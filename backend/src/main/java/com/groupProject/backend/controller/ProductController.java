package com.groupProject.backend.controller;

import com.groupProject.backend.model.DTO.ProductDTO;
import com.groupProject.backend.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

/**
 * \brief Клас-контролер для управління товарами.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;
    /**
     * Конструктор контролера.
     *
     * @param productService Сервіс для роботи з товарами.
     */
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Створює новий товар.
     *
     * @param product Дані нового товару.
     * @return Відповідь, що містить інформацію про створений товар.
     */
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO product) {
        logger.info("Received a request to create an product: {}", product);
        Optional<ProductDTO> createdProduct = productService.createProduct(product);
        if (!createdProduct.isPresent())
        {
            logger.error("Failed request to create an product: {}", product);
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(createdProduct.get(), HttpStatus.CREATED);
    }
    /**
     * Отримує список всіх товарів.
     *
     * @return Відповідь, що містить список усіх існуючих товарів.
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        logger.info("Received a request to get all products");

        List<ProductDTO> products = productService.getAllProducts();
        if (products.isEmpty())
        {
            logger.error("Failed request to get all products");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    /**
     * Отримує товар за ідентифікатором.
     *
     * @param productId Ідентифікатор товару.
     * @return Відповідь, що містить інформацію про товар або код помилки, якщо товар не знайдено.
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
        logger.info("Received request to get product by id{}", productId);
        Optional<ProductDTO> product = productService.getProductById(productId);
        if (!product.isPresent())
        {
            logger.error("Failed request to get product by id{}", productId);
        }
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * Отримує товар за назвою.
     *
     * @param name Назва товару.
     * @return Відповідь, що містить інформацію про товар або код помилки, якщо товар не знайдено.
     */
    @GetMapping("/{name}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String name) {
        logger.info("Received request to get product by name{}", name);
        Optional<ProductDTO> product = productService.getProductByName(name);
        if (!product.isPresent())
        {
            logger.error("Failed request to get product by name{}", name);
        }
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * Видаляє товар за ідентифікатором.
     *
     * @param productId Ідентифікатор товару, який слід видалити.
     * @return Відповідь з кодом 204 (NO_CONTENT) у разі успішного видалення або кодом помилки, якщо товар не знайдено.
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        logger.info("Received request to delete product by id{}", productId);
        if (!productService.getProductById(productId).isPresent()) {
            logger.error("Failed request to delete product by id{}", productId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /**
     * Встановлює кількість товару на складі.
     *
     * @param productDTO     Дані товару, кількість якого слід встановити.
     * @param stockQuantity  Нова кількість товару на складі.
     * @return Відповідь, що містить оновлені дані товару або код помилки, якщо оновлення не вдалося.
     */
    @PostMapping("/stockQuantity/{stockQuantity}")
    public ResponseEntity<ProductDTO> setProductQuantity(@Valid  @RequestBody ProductDTO productDTO, @PathVariable int stockQuantity) {
        logger.info("Received request to set product{} Quantity{}", productDTO, stockQuantity);
        productDTO.setStockQuantity(stockQuantity);
        Optional<ProductDTO> updatedProductDTO = productService.updateProduct(productDTO);
        if (!updatedProductDTO.isPresent())
        {
            logger.error("Failed request to set product{} Quantity{}", productDTO, stockQuantity);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    // TODO: search by Categories / keywords
    // TODO: search by product title Containing Ignore Case
}
