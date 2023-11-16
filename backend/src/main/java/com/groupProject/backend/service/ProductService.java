package com.groupProject.backend.service;

import com.groupProject.backend.Converter.ProductConverter;
import com.groupProject.backend.model.DTO.ProductDTO;
import com.groupProject.backend.model.Product;
import com.groupProject.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
/**
 * \brief Сервіс, який надає функціонал для операцій над об'єктами типу Product.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    /**
     * Отримати список усіх продуктів у системі.
     *
     * @return Список усіх продуктів у форматі ProductDTO.
     */
    public List<ProductDTO> getAllProducts() {
        return productConverter.convertToListDto(productRepository.findAll());
    }
    /**
     * Отримати продукт за його ідентифікатором.
     *
     * @param productId Ідентифікатор продукту.
     * @return Об'єкт типу Optional, що може містити ProductDTO, якщо продукт знайдено.
     */
    public Optional<ProductDTO> getProductById(Long productId) {
        return Optional.ofNullable(productConverter.convertToDTO(productRepository.findById(productId).get()));
    }
    /**
     * Отримати продукт за його ідентифікатором у вигляді об'єкта Product.
     *
     * @param productId Ідентифікатор продукту.
     * @return Об'єкт типу Optional, що може містити Product, якщо продукт знайдено.
     */
    public Optional<Product> getProductEntityById(Long productId) {
        return productRepository.findById(productId);
    }

    /**
     * Створити новий продукт на основі об'єкта ProductDTO.
     *
     * @param product Об'єкт ProductDTO, що представляє продукт.
     * @return Об'єкт типу Optional, що може містити ProductDTO, якщо створення успішне.
     */
    public Optional<ProductDTO> createProduct(ProductDTO product) {
        return Optional.ofNullable(productConverter.convertToDTO(productRepository.save(productConverter.convertToEntity(product))));
    }
    /**
     * Видалити продукт за його ідентифікатором.
     *
     * @param productId Ідентифікатор продукту.
     */
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    /**
     * Оновити інформацію про продукт на основі об'єкта ProductDTO.
     *
     * @param product Об'єкт ProductDTO, що містить нові дані для продукту.
     * @return Об'єкт типу Optional, що може містити оновлене ProductDTO, якщо оновлення успішне.
     */
    public Optional<ProductDTO> updateProduct(ProductDTO product) {
        return Optional.ofNullable(productConverter.convertToDTO(productRepository.save(productConverter.convertToEntity(product))));
    }
    /**
     * Отримати продукт за його іменем.
     *
     * @param name Ім'я продукту.
     * @return Об'єкт типу Optional, що може містити ProductDTO, якщо продукт знайдено за іменем.
     */
    public Optional<ProductDTO> getProductByName(String name) {
        return Optional.ofNullable(productConverter.convertToDTO(productRepository.findByName(name).get()));
    }
}

