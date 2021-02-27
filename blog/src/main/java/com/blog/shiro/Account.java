package com.blog.shiro;

import lombok.Data;

import java.io.Serializable;

@Data
public class Account implements Serializable {
    private Long id;
    private String username;
    private String avatar;
}
