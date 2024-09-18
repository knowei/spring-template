package com.liwen.project.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String uuid;
    private String code;
}
