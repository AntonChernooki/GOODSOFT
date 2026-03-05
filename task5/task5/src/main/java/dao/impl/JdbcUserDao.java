package dao.impl;

import config.DatabaseConfig;
import dao.UserDao;
import model.Role;
import model.User;

import java.sql.*;
import java.util.*;

public class JdbcUserDao implements UserDao {

    private static final JdbcUserDao INSTANCE = new JdbcUserDao();

    private JdbcUserDao() {
    }

    public static JdbcUserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<User> getAllUsers() throws SQLException {
        String sql = "SELECT users.login, users.password, users.email, users.surname, users.name, users.patronymic, users.birthday, roles.name AS role_name " +
                "FROM users " +
                "LEFT JOIN user_roles ON users.id = user_roles.user_id " +
                "LEFT JOIN roles ON user_roles.role_id = roles.id " +
                "ORDER BY users.login";

        Map<String, User> userMap = new LinkedHashMap<>();

        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String login = resultSet.getString("login");

                userMap.putIfAbsent(login, mapUser(resultSet));

                String roleName = resultSet.getString("role_name");
                if (roleName != null && !roleName.isEmpty()) {
                    User user = userMap.get(login);
                    try {
                        Role role = Role.valueOf(roleName.toUpperCase());
                        user.getRoles().add(role);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Неизвестная роль в БД: " + roleName);
                    }
                }
            }
        }

        return userMap.values();
    }

    private Set<Role> getRolesForUser(Connection connection, String login) throws SQLException {
        String sql = "SELECT roles.name " +
                "FROM roles " +
                "JOIN user_roles ON roles.id = user_roles.role_id " +
                "JOIN users ON users.id = user_roles.user_id " +
                "WHERE users.login = ?";

        Set<Role> roles = new HashSet<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String roleName = resultSet.getString("name");
                    try {
                        Role role = Role.valueOf(roleName.toUpperCase());
                        roles.add(role);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Неизвестная роль: " + roleName);
                    }
                }
            }
        }
        return roles;
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("email"),
                resultSet.getString("surname"),
                resultSet.getString("name"),
                resultSet.getString("patronymic"),
                resultSet.getString("birthday"),
                new HashSet<>()
        );
    }

    @Override
    public User getUserByLogin(String login) throws SQLException {
        String sql = "SELECT login, password, email, surname, name, patronymic, birthday FROM users WHERE login = ?";

        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = mapUser(resultSet);
                    Set<Role> roles = getRolesForUser(connection, login);
                    user.setRoles(roles);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean addUser(User user) {
        String insertUser = "INSERT INTO users (login, password, email, surname, name, patronymic, birthday) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertRole = "INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE login = ?), (SELECT id FROM roles WHERE name = ?))";

        try (Connection connection = DatabaseConfig.getDataSource().getConnection()) {
            connection.setAutoCommit(false);

            try {
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertUser)) {
                    preparedStatement.setString(1, user.getLogin());
                    preparedStatement.setString(2, user.getPassword());
                    preparedStatement.setString(3, user.getEmail());
                    preparedStatement.setString(4, user.getSurname());
                    preparedStatement.setString(5, user.getName());
                    preparedStatement.setString(6, user.getPatronymic());
                    if (user.getBirthday() != null && !user.getBirthday().isEmpty()) {
                        preparedStatement.setDate(7, java.sql.Date.valueOf(user.getBirthday()));
                    } else {
                        preparedStatement.setNull(7, Types.DATE);
                    }
                    preparedStatement.executeUpdate();
                }

                if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertRole)) {
                        for (Role role : user.getRoles()) {
                            preparedStatement.setString(1, user.getLogin());
                            preparedStatement.setString(2, role.name());
                            preparedStatement.addBatch();
                        }
                        preparedStatement.executeBatch();
                    }
                }

                connection.commit();
                return true;

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(String login) {
        String deleteRoles = "DELETE FROM user_roles WHERE user_id = (SELECT id FROM users WHERE login = ?)";
        String deleteUser = "DELETE FROM users WHERE login = ?";

        try (Connection connection = DatabaseConfig.getDataSource().getConnection()) {
            connection.setAutoCommit(false);

            try {
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteRoles)) {
                    preparedStatement.setString(1, login);
                    preparedStatement.executeUpdate();
                }

                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteUser)) {
                    preparedStatement.setString(1, login);
                    int rows = preparedStatement.executeUpdate();

                    connection.commit();
                    return rows > 0;
                }

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(String oldLogin, User newUser) {
        String updateUser = "UPDATE users SET login = ?, password = ?, email = ?, surname = ?, name = ?, patronymic = ?, birthday = ? WHERE login = ?";
        String deleteRoles = "DELETE FROM user_roles WHERE user_id = (SELECT id FROM users WHERE login = ?)";
        String insertRole = "INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE login = ?), (SELECT id FROM roles WHERE name = ?))";

        try (Connection connection = DatabaseConfig.getDataSource().getConnection()) {
            connection.setAutoCommit(false);

            try {

                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteRoles)) {
                    preparedStatement.setString(1, oldLogin);
                    preparedStatement.executeUpdate();
                }

                try (PreparedStatement preparedStatement = connection.prepareStatement(updateUser)) {
                    preparedStatement.setString(1, newUser.getLogin());
                    preparedStatement.setString(2, newUser.getPassword());
                    preparedStatement.setString(3, newUser.getEmail());
                    preparedStatement.setString(4, newUser.getSurname());
                    preparedStatement.setString(5, newUser.getName());
                    preparedStatement.setString(6, newUser.getPatronymic());
                    if (newUser.getBirthday() != null && !newUser.getBirthday().isEmpty()) {
                        preparedStatement.setDate(7, java.sql.Date.valueOf(newUser.getBirthday()));
                    } else {
                        preparedStatement.setNull(7, Types.DATE);
                    }
                    preparedStatement.setString(8, oldLogin);

                    if (preparedStatement.executeUpdate() == 0) {
                        connection.rollback();
                        return false;
                    }
                }


                if (newUser.getRoles() != null && !newUser.getRoles().isEmpty()) {
                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertRole)) {
                        for (Role role : newUser.getRoles()) {
                            preparedStatement.setString(1, newUser.getLogin());
                            preparedStatement.setString(2, role.name());
                            preparedStatement.addBatch();
                        }
                        preparedStatement.executeBatch();
                    }
                }

                connection.commit();
                return true;

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}