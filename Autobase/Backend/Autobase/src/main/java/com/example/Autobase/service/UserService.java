package com.example.Autobase.service;

import com.example.Autobase.dao.RoleDao;
import com.example.Autobase.dao.UserDao;
import com.example.Autobase.dto.request.SetUserEnabledDto;
import com.example.Autobase.dto.request.UserLoginDto;
import com.example.Autobase.dto.request.UserRegistrationDto;
import com.example.Autobase.dto.request.UserUpdateDto;
import com.example.Autobase.dto.response.UserResponseDto;
import com.example.Autobase.exception.DuplicateLoginException;
import com.example.Autobase.exception.RoleNotFoundException;
import com.example.Autobase.exception.UserNotFoundException;
import com.example.Autobase.model.entities.Role;
import com.example.Autobase.model.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserResponseDto registerUser(UserRegistrationDto userRegistrationDto) {
        if (userDao.getUserByLogin(userRegistrationDto.getLogin()).isPresent()) {
            throw new DuplicateLoginException("Пользователь с логином: " + userRegistrationDto + " уже зарегистрирован ");

        }
        String encodedPassword = bCryptPasswordEncoder.encode(userRegistrationDto.getPassword());
        User user = new User(userRegistrationDto.getLogin(), encodedPassword);
        userDao.addUser(user);
        if (userRegistrationDto.getRoles() != null && !userRegistrationDto.getRoles().isEmpty()) {
            for (String roleName : userRegistrationDto.getRoles()) {
                Role role = roleDao.getRoleByName(roleName).orElseThrow(() -> new RoleNotFoundException("роль с именем:  " + roleName + " не нашлось в бд "));

                userDao.addUserRole(user.getId(), role.getId());
            }
        }
        user = userDao.getUserById(user.getId()).orElseThrow(() -> new UserNotFoundException("не нашелся пользователь по id"));

        return userToResponseDto(user);
    }

    public UserResponseDto getUserByLogin(String login) {
        User user = userDao.getUserByLogin(login).orElseThrow(() -> new UserNotFoundException("не нашелся пользователь по логину"));


        return userToResponseDto(user);
    }

    public UserResponseDto getUserById(Long userId) {
        User user = userDao.getUserById(userId).orElseThrow(() -> new UserNotFoundException("не нашелся пользователь по id"));
        return userToResponseDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> userList = userDao.getAllUsers();
        List<UserResponseDto> userResponseDtoArrayList = new ArrayList<>();
        for (User user : userList) {
            userResponseDtoArrayList.add(userToResponseDto(user));
        }
        return userResponseDtoArrayList;
    }

    public UserResponseDto setUserEnabled(Long id, SetUserEnabledDto setUserEnabledDto) {
        User user = userDao.getUserById(id).orElseThrow(() -> new UserNotFoundException("не нашелся пользователь по id"));
        user.setEnabled(Boolean.valueOf(setUserEnabledDto.getEnabled()));
        userDao.updateUser(user);
        return userToResponseDto(user);
    }

    public void deleteUser(Long userId) {

        userDao.getUserById(userId).orElseThrow(() -> new UserNotFoundException("не нашелся пользователь по id"));
        userDao.deleteUserRoles(userId);
        userDao.deleteUser(userId);
    }


    public UserResponseDto login(UserLoginDto userLoginDto) {
        User user = userDao.getUserByLogin(userLoginDto.getLogin()).orElseThrow(() -> new UserNotFoundException("не нашелся пользователь по login"));
        if (bCryptPasswordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            return userToResponseDto(user);
        } else {
            throw new UserNotFoundException("неверный пароль");
        }
    }

    public UserResponseDto updateUser(Long id, UserUpdateDto updateDto) {
        User user = userDao.getUserById(id).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        if (updateDto.getLogin() != null && !updateDto.getLogin().equals(user.getLogin())) {
            userDao.getUserByLogin(updateDto.getLogin()).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new DuplicateLoginException("Логин уже используется");
                }
            });
            user.setLogin(updateDto.getLogin());
        }

        if (updateDto.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(updateDto.getPassword()));
        }
        if (updateDto.getEnabled() != null) {
            user.setEnabled(updateDto.getEnabled());
        }
        userDao.updateUser(user);

        if (updateDto.getRoles() != null) {
            userDao.deleteUserRoles(user.getId());

            for (String roleName : updateDto.getRoles()) {
                Role role = roleDao.getRoleByName(roleName).orElseThrow(() -> new RoleNotFoundException("Роль не найдена: " + roleName));
                userDao.addUserRole(user.getId(), role.getId());
            }
        }

        User updatedUser = userDao.getUserById(id).orElseThrow(() -> new UserNotFoundException("Пользователь не найден после обновления"));
        return userToResponseDto(updatedUser);
    }

    /*
    public void addRoleToUser(Long userId, Long roleId) {
        userDao.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        roleDao.getRoleById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Роль не найдена"));
        userDao.addUserRole(userId, roleId);
    }
     */

    private UserResponseDto userToResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setLogin(user.getLogin());
        userResponseDto.setEnabled(user.getEnabled());
        userResponseDto.setCreateAt(user.getCreatedAt());
        userResponseDto.setUpdateAt(user.getUpdatedAt());
        Set<Role> roleSet = user.getRoles();
        Set<String> roleString = new HashSet<>();
        for (Role role : roleSet) {
            roleString.add(role.getName());
        }
        userResponseDto.setRoles(roleString);
        return userResponseDto;
    }


}
