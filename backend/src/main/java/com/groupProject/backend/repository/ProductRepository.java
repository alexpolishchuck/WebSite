package com.groupProject.backend.repository;

import com.groupProject.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * \brief Репозиторій для доступу до даних продуктів (Product) у базі даних.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Пошук продукту за назвою.
     *
     * @param name Назва продукту, за якою ведеться пошук.
     * @return Знайдений продукт, якщо він існує.
     */
    Optional<Product> findByName(String name);
}
