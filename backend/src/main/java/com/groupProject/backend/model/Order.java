package com.groupProject.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * \brief Клас, що представляє об'єкт замовлення. Використовується для взаємодії з базою даних та представлення замовлень в системі.
 */
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {
    /**
     * Унікальний ідентифікатор замовлення.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Замовник (клієнт), який зробив замовлення.
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    /**
     * Менеджер, який обробляє замовлення.
     */
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    /**
     * Дата та час замовлення.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDateTime;

    /**
     * Статус замовлення, який визначає його поточний стан.
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    /**
     * Загальна сума замовлення.
     */
    private double totalAmount;

    /**
     * Адреса доставки замовлення.
     */
    private String address;

    /**
     * Список продуктів, які були замовлені.
     */
    @ElementCollection
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderedProduct> orderedProducts;

    /**
     * Перелік можливих статусів замовлення.
     */
    public enum OrderStatus {
        IN_PROCESS("In Process"),
        SHIPPED("Shipped"),
        DELIVERED("Delivered"),
        CANCELED("Canceled"),
        SUBMITTED("Submitted"),
        RETURNED("Returned");

        private final String displayName;

        OrderStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}


