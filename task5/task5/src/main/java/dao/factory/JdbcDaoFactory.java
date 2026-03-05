package dao.factory;

import dao.UserDao;

import dao.impl.JdbcUserDao;

public class JdbcDaoFactory {
    private static final UserDao userDao = JdbcUserDao.getInstance();

    private static final JdbcDaoFactory INSTANCE = new JdbcDaoFactory();

    public static JdbcDaoFactory getInstance() {
        return INSTANCE;
    }

    private JdbcDaoFactory() {
    }


    public UserDao getUserDao() {
        return userDao;
    }

}
