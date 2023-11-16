package com.groupProject.backend.repository;

import com.groupProject.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



/**
 * \brief Репозиторій для доступу до даних замовлень (Order) у базі даних.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Пошук всіх замовлень, які належать конкретному користувачу за його унікальним ідентифікатором.
     *
     * @param customerId Унікальний ідентифікатор користувача (клієнта).
     * @return Список замовлень, які належать користувачеві.
     */
    List<Order> findAllByCustomerId(Long customerId);

    /**
     * Пошук всіх замовлень, які обслуговуються конкретним менеджером за його унікальним ідентифікатором.
     *
     * @param managerId Унікальний ідентифікатор менеджера.
     * @return Список замовлень, які обслуговуються менеджером.
     */
    List<Order> findAllByManagerId(Long managerId);
}

