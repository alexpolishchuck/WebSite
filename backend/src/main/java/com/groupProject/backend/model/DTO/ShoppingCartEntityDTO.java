package com.groupProject.backend.model.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
/**
 * \brief Клас, що представляє об'єкт сутності корзини покупця (DTO). Використовується для передачі даних про корзину покупця між клієнтом і сервером.
 */
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingCartEntityDTO {
    /**
     * Унікальний ідентифікатор сутності корзини.
     */
    @NotNull(message = "Id is required")
    private Long id;

    /**
     * Унікальний ідентифікатор користувача (покупця).
     */
    @NotNull(message = "user Id is required")
    private Long userId;

    /**
     * Унікальний ідентифікатор продукту в корзині.
     */
    @NotNull(message = "product Id is required")
    private Long productId;

    /**
     * Кількість одиниць продукту в корзині.
     */
    @NotNull(message = "quantity is required")
    private int quantity;
}
