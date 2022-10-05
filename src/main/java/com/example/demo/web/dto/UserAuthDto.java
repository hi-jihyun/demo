package com.example.demo.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class UserAuthDto {

    private Integer userNo;
    private String userId;
    private String userPw;
    private String userType;
    private String userNm;

    public UserAuthDto(Integer userNo, String userId, String userPw, String userType, String userNm){
        this.userNo = userNo;
        this.userId = userId;
        this.userPw = userPw;
        this.userType = userType;
        this.userNm = userNm;

    }

 }