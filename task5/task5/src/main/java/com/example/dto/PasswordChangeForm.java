package com.example.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PasswordChangeForm {

    @NotBlank(message = "{password.old.blank}")
    private String oldPassword;

    @NotBlank(message = "{password.new.blank}")
    @Size(min = 4,message = "{password.new.size}")
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
