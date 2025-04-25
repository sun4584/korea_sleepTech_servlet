package com.study.korea_sleeptech_servlet.order_app.entity;

import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
public class Order {
    private int id;
    private final int userId;
    private final String productName;
    private final int amount;
}