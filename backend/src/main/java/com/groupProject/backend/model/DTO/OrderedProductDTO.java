package com.groupProject.backend.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * \brief Клас, що представляє об'єкт продукту в замовленні (DTO). Використовується для передачі даних про продукт, який входить до замовлення, між клієнтом і сервером.
 */
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderedProductDTO {
    /**
     * Унікальний ідентифікатор продукту, який входить до замовлення.
     */
    @NotNull(message = "product Id is required")
    private Long productId;

    /**
     * Кількість одиниць цього продукту в замовленні.
     */
    @NotNull(message = "quantity is required")
    private int quantity;

    /**
     * Ціна одиниці продукту в замовленні.
     */
    @NotNull(message = "price is required")
    private int price;
}
