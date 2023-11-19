package com.groupProject.backend.controller;

import com.groupProject.backend.model.DTO.OrderDTO;
import com.groupProject.backend.model.DTO.OrderedProductDTO;
import com.groupProject.backend.model.DTO.ProductDTO;
import com.groupProject.backend.model.Order;

import com.groupProject.backend.service.OrderService;
import com.groupProject.backend.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
/**
 * \brief Клас-контролер для управління замовленнями.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final ProductService productService;
    /**
     * Конструктор контролера.
     *
     * @param orderService Сервіс для роботи з замовленнями.
     * @param productService Сервіс для роботи з продуктами.
     */
    @Autowired
    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }
    /**
     * Створює нове замовлення.
     *
     * @param orderDTO Дані нового замовлення.
     * @return Відповідь, що містить інформацію про створене замовлення.
     */
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        logger.info("Received a request to create an order: {}", orderDTO);
        Optional<OrderDTO> createdOrder = orderService.createOrder(orderDTO);
        if (!createdOrder.isPresent())
        {
            logger.error("Failed request to create an order: {}", orderDTO);
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        deleteOrderedProducts(orderDTO);
        return new ResponseEntity<>(createdOrder.get(), HttpStatus.CREATED);
    }
    /**
     * Надає всі існуючі замовлення.
     *
     * @return Відповідь, що містить інформацію про всі замовлення.
     */
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        logger.info("Received a request to get all orders: ");
        List<OrderDTO> orders = orderService.getAllOrders();
        if (orders.isEmpty())
        {
            logger.error("Failed request to get all orders");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Надає існуючє замовлення по id.
     *
     * @param orderId Дані існуючого замовлення.
     * @return Відповідь, що містить інформацію про створене замовлення по ID.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        logger.info("Received a request to get an Order by id{}", orderId);
        Optional<OrderDTO> orderDTO = orderService.getOrderById(orderId);
        return orderDTO.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Видаляє  замовлення по id.
     *
     * @param orderId Дані існуючого замовлення.
     * @return Boolean, на основі чи вдалося видалити замовленя по id.
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        logger.info("Received a request to delete an Order by id{}", orderId);
        if (!orderService.getOrderById(orderId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Надає існуючє замовлення по customerId.
     *
     * @param customerId Дані існуючого замовлення.
     * @return Відповідь, що містить інформацію про створене замовлення по CustomerID.
     */
    @GetMapping("/cstmrId/{customerId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@PathVariable Long customerId) {
        logger.info("Received a request to get all Orders by customer id{}", customerId);
        List<OrderDTO> orders = orderService.getOrderByCustomerId(customerId);
        if(orders.isEmpty())
        {
            logger.error("Failed request to get all Orders by customer id{}", customerId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Надає існуючє замовлення по managerId.
     *
     * @param managerId Дані існуючого замовлення.
     * @return Відповідь, що містить інформацію про створене замовлення по Manager ID.
     */
    @GetMapping("/mngrId/{managerId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByManagerId(@PathVariable Long managerId) {
        logger.info("Received a request to get all Orders by manager id{}", managerId);
        List<OrderDTO> orders = orderService.getOrderByManagerId(managerId);
        if(orders.isEmpty())
        {
            logger.error("Failed request to get all Orders by manager id{}", managerId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Змінює статус замовлення на Cancel.
     *
     * @return Відповідь, що містить інформацію про зміну статуса замовлення на CANCELED.
     */
    @PostMapping("/cancellation")
    public ResponseEntity<OrderDTO> cancelOrder(@Valid @RequestBody OrderDTO orderDTO) {
        logger.info("Received a request to get cancel order {}", orderDTO);

        if (!Objects.equals(orderDTO.getStatus(), "SUBMITTED"))
        {
            logger.error("Tried to cancel an order without SUBMITTED status");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        orderDTO.setStatus(String.valueOf(Order.OrderStatus.CANCELED));
        Optional<OrderDTO> updatedOrder = orderService.updateOrder(orderDTO);

        if(!updatedOrder.isPresent())
        {
            logger.error("Failed request to cancel an order: {}", orderDTO);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedOrder.get(), HttpStatus.OK);
    }

    /**
     * Змінює статус замовлення .
     *
     * @param status новий статус.
     * @return Відповідь, що містить інформацію про зміну статуса замовлення на status.
     */
    @PostMapping("/stts/{status}")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable String status, @Valid @RequestBody OrderDTO orderDTO) {
        logger.info("Received a request to update order{} with status{}", orderDTO, status);
        orderDTO.setStatus(String.valueOf(Order.OrderStatus.valueOf(status)));
        Optional<OrderDTO> updatedOrder = orderService.updateOrder(orderDTO);
        if(!updatedOrder.isPresent())
        {
            logger.error("Failed request to update an order{}, status{}", orderDTO, status);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedOrder.get(), HttpStatus.OK);
    }

    /**
     * Видаляє замовлені продукти .
     *
     * @param orderDTO Дані існуючого замовлення.
     */
    private void deleteOrderedProducts(OrderDTO orderDTO)
    {
        for(OrderedProductDTO orderedProduct : orderDTO.getOrderedProducts())
        {
            Optional<ProductDTO> productDTO = productService.getProductById(orderedProduct.getProductId());

            if(!productDTO.isPresent())
            {
                logger.error("Failed request to get product by id{}", orderedProduct.getProductId());
            }

            int actualQuantity = productDTO.get().getStockQuantity() - orderedProduct.getQuantity();
            productDTO.get().setStockQuantity(actualQuantity);
            productService.updateProduct(productDTO.get());
        }
    }
}

