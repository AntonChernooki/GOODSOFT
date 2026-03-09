package com.example.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PasswordChangeForm {

    @NotBlank(message = "старый пароль не может быть пустым")
    private String oldPassword;

    @NotBlank(message = "новый пароль не может быть пустым")
    @Size(min = 4,message = "новый пароль должен содержать минимум 4 символа")
    private String newPassword;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
