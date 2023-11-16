package com.groupProject.backend.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.groupProject.backend.model.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * \brief Клас, що представляє об'єкт продукту (DTO). Використовується для передачі даних про продукт між клієнтом і сервером.
 */
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    /**
     * Унікальний ідентифікатор продукту.
     */
    @NotNull(message = "Id is required")
    private Long id;

    /**
     * Назва продукту.
     */
    @NotNull(message = "name is required")
    private String name;

    /**
     * Опис продукту.
     */
    @NotNull(message = "description is required")
    private String description;

    /**
     * Ціна продукту.
     */
    @NotNull(message = "price is required")
    private double price;

    /**
     * Кількість одиниць продукту на складі.
     */
    @NotNull(message = "stockQuantity is required")
    private int stockQuantity;

    /**
     * Категорія, до якої належить продукт.
     */
    @NotNull(message = "category is required")
    private String category;

    /**
     * URL-посилання на зображення продукту.
     */
    private String imageURL;
}

