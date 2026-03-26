package com.example.demoShop.entity;

public enum OrderStatus {
    PENDING,    // Нове, очікує підтвердження
    CONFIRMED,  // Підтверджено
    DELIVERING, // В доставці
    DONE,       // Виконано
    CANCELLED   // Скасовано
}