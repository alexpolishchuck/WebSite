package com.groupProject.backend.repository;

import com.groupProject.backend.model.ShoppingCartEntity;
import com.groupProject.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \brief Репозиторій для доступу до даних елементів корзини (ShoppingCartEntity) у базі даних.
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {

    /**
     * Пошук всіх елементів корзини для користувача.
     *
     * @param user Користувач, для якого ведеться пошук елементів корзини.
     * @return Список елементів корзини, які відповідають користувачу.
     */
    List<ShoppingCartEntity> findAllByUser(User user);
}
