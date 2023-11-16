package com.groupProject.backend.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.groupProject.backend.model.Order;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * \brief Клас, що представляє об'єкт замовлення (DTO). Використовується для передачі даних про замовлення між клієнтом і сервером.
 */
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    /**
     * Унікальний ідентифікатор замовлення.
     */
    @NotNull(message = "Id is required")
    private Long id;

    /**
     * Унікальний ідентифікатор клієнта, який зробив замовлення.
     */
    @NotNull(message = "Customet Id is required")
    private Long customerId;

    /**
     * Унікальний ідентифікатор менеджера, який обробляє замовлення.
     */
    @NotNull(message = "Manager Id is required")
    private Long managerId;

    /**
     * Дата та час оформлення замовлення.
     */
    @NotNull(message = "Order DateTime is required")
    private Date orderDateTime;

    /**
     * Статус замовлення (наприклад, "SUBMITTED", "CANCELED" і т. д.).
     */
    @NotNull(message = "Status is required")
    private String status;

    /**
     * Загальна сума замовлення.
     */
    @NotNull(message = "Total Amount is required")
    private double totalAmount;

    /**
     * Адреса, за якою необхідно доставити замовлення.
     */
    @NotNull(message = "Address is required")
    private String address;

    /**
     * Список продуктів, які входять до замовлення.
     */
    @NotNull(message = "Ordered Products is required")
    private List<OrderedProductDTO> orderedProducts;
}
