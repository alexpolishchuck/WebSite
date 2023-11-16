package com.groupProject.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.List;

/**
 * \brief Клас, що представляє продукт в магазині.
 */
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {

    /**
     * Унікальний ідентифікатор продукту.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Назва продукту.
     */
    private String name;

    /**
     * Опис продукту.
     */
    @Column(length = 1000)
    private String description;

    /**
     * Ціна продукту.
     */
    private double price;

    /**
     * Кількість одиниць цього продукту на складі.
     */
    private int stockQuantity;

    private String category;
    // TODO: make class Enum categories
    // Поки що поле категорії визначено як рядок. Можна розглядати використання перерахування (Enum) для категорій продуктів.

    /**
     * URL-зображення для продукту.
     */
    private String imageURL;
}

