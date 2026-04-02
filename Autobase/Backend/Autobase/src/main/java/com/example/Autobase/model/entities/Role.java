package com.example.Autobase.model.entities;

import lombok.Data;


@Data
public class Role {
    private Long id;
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role() {

    }


}
