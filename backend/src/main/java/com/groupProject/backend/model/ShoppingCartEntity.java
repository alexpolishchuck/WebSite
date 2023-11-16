package com.groupProject.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

/**
 * \brief Клас, що представляє сутність корзини покупок в магазині.
 */
@Entity
@Table(name = "shopping_carts")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShoppingCartEntity {

    /**
     * Унікальний ідентифікатор сутності корзини покупок.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Користувач, який додав продукт до корзини.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Продукт, який додано до корзини.
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * Кількість одиниць продукту в корзині.
     */
    private int quantity;
}

