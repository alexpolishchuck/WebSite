package com.groupProject.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * \brief Клас, що представляє вбудований об'єкт "Замовлений продукт" у замовленні. Використовується для визначення продуктів та їх кількості в замовленні.
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderedProduct {
    /**
     * Продукт, який був замовлений.
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * Кількість одиниць цього продукту в замовленні.
     */
    private int quantity;

    /**
     * Ціна за одну одиницю цього продукту.
     */
    private int price;
}

