package com.study.korea_sleeptech_servlet.order_app.entity;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor // 초기화 되지 않은 final 필드, @NonNull 필드를 매개변수로 생성자 생성
@Getter
@Setter
public class User {
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String email;

//    public User(String name, String email) {
//        this.name = name;
//        this.email = email;
//    }
}