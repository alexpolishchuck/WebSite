package com.groupProject.backend.controller;

import com.groupProject.backend.model.DTO.OrderDTO;

import com.groupProject.backend.model.DTO.ProductDTO;
import com.groupProject.backend.model.DTO.ShoppingCartEntityDTO;
import com.groupProject.backend.service.ProductService;
import com.groupProject.backend.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
/**
 * \brief Клас-контролер для управління корзиною покупок.
 */
@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;

    /**
     * Конструктор контролера.
     *
     * @param shoppingCartService Сервіс для роботи з корзиною покупок.
     * @param productService       Сервіс для роботи з товарами.
     */
    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }
    /**
     * Додає товар до корзини покупок.
     *
     * @param shoppingCartEntityDTO Дані товару, який слід додати до корзини.
     * @return Відповідь, що містить інформацію про корзину покупок після додавання товару або код помилки, якщо додавання не вдалося.
     */

    @PostMapping("/add")
    public ResponseEntity<ShoppingCartEntityDTO> addToCart(@Valid @RequestBody ShoppingCartEntityDTO shoppingCartEntityDTO) {
        logger.info("Received a request to add shopping Cart Entity{}", shoppingCartEntityDTO);
        Optional<ShoppingCartEntityDTO> updatedCart = shoppingCartService.addToCart(shoppingCartEntityDTO);
        if (!updatedCart.isPresent())
        {
            logger.error("Failed request to add shopping Cart Entity{}", shoppingCartEntityDTO);
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(updatedCart.get(), HttpStatus.CREATED);
    }
    /**
     * Отримує список товарів у корзині покупок для покупця за ідентифікатором.
     *
     * @param customerId Ідентифікатор покупця.
     * @return Відповідь, що містить список товарів у корзині покупок або код помилки, якщо корзина пуста або покупця не знайдено.
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ShoppingCartEntityDTO>> getCartItemsForCustomer(@PathVariable Long customerId) {
        logger.info("Received a request to get shopping Cart Itmes for Customer Id{}", customerId);
        List<ShoppingCartEntityDTO> cartItems = shoppingCartService.getCartItemsForCustomer(customerId);
        if (cartItems.isEmpty())
        {
            logger.error("Failed request to get shopping Cart Itmes for Customer Id{}", customerId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    /**
     * Оформлює замовлення на основі вмісту корзини покупок покупця.
     *
     * @param customerId Ідентифікатор покупця.
     * @return Відповідь, що містить інформацію про створене замовлення або код помилки, якщо оформлення не вдалося.
     */
    @PostMapping("/checkout/{customerId}")
    public ResponseEntity<OrderDTO> checkout(@PathVariable Long customerId) {
        logger.info("Received a request to checkout shopping Cart Itmes for Customer Id{}", customerId);
        Optional<OrderDTO> createdOrder = shoppingCartService.checkout(customerId);
        if (!createdOrder.isPresent())
        {
            logger.error("Failed request to create order for Cart with customer Id{}", customerId);
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(createdOrder.get(), HttpStatus.CREATED);
    }

    /**
     * Видаляє товар із корзини покупок.
     *
     * @param shoppingCartEntityDTO Дані товару, який слід видалити із корзини.
     * @return Відповідь, що підтверджує успішне видалення або код помилки, якщо видалення не вдалося.
     */
    @DeleteMapping
    public ResponseEntity<String> deleteShoppingCartItem(@Valid @RequestBody  ShoppingCartEntityDTO shoppingCartEntityDTO) {
        logger.info("Received a request to delete shopping Cart Item{}", shoppingCartEntityDTO);
        boolean deleted = shoppingCartService.deleteShoppingCartItem(shoppingCartEntityDTO);
        if (deleted) {
            return ResponseEntity.ok("Cart Item deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * Оновлює кількість товару в корзині покупок.
     *
     * @param quantity              Нова кількість товару в корзині.
     * @param shoppingCartEntityDTO Дані товару, для якого слід оновити кількість.
     * @return Відповідь, що містить оновлені дані товару в корзині покупок або код помилки, якщо оновлення не вдалося.
     */
    @PutMapping("/{quantity}")
    public ResponseEntity<ShoppingCartEntityDTO> updateShoppingCartItemQuantity(@PathVariable int quantity, @Valid @RequestBody ShoppingCartEntityDTO shoppingCartEntityDTO) {
        logger.info("Received a request to update order for Cart{} with quantity{}",shoppingCartEntityDTO, quantity);

        // від'ємний quantity
        if (quantity == 0 || shoppingCartEntityDTO.getQuantity() + quantity <= 0)
        {
            logger.error("Failed request to update order for Cart{} with quantity{}",shoppingCartEntityDTO, quantity);
            shoppingCartService.deleteShoppingCartItem(shoppingCartEntityDTO);
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        // перевірка quantity на складі
        if (quantity > 0)
        {
            Optional<ProductDTO> product = productService.getProductById(shoppingCartEntityDTO.getProductId());
            if (product.isPresent())
            {
                if (product.get().getStockQuantity() < quantity)
                {
                    logger.error("Failed request to update order for Cart{} with quantity{}",shoppingCartEntityDTO, quantity);
                    return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                }
            }

        }
        shoppingCartEntityDTO.setQuantity(quantity);
        ShoppingCartEntityDTO shoppingCartEntity = shoppingCartService.updateShoppingCardItem(shoppingCartEntityDTO);
        return new ResponseEntity<>(shoppingCartEntity, HttpStatus.OK);
    }

}

