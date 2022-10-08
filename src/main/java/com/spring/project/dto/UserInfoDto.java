package com.spring.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto { // 폼으로 받을 회원정보를 매핑 시켜줄 객체를 만드는 작업
    private String email;
    private String password;
    private String name;
    private String auth;
}
