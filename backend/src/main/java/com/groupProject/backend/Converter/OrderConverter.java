package com.groupProject.backend.Converter;

import com.groupProject.backend.model.DTO.OrderDTO;
import com.groupProject.backend.model.DTO.OrderedProductDTO;
import com.groupProject.backend.model.DTO.ProductDTO;
import com.groupProject.backend.model.Order;
import com.groupProject.backend.model.OrderedProduct;
import com.groupProject.backend.model.Product;
import com.groupProject.backend.model.User;
import com.groupProject.backend.service.ProductService;
import com.groupProject.backend.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * \brief Клас-конвертер для ковертації класу Order і класу OrderDTO.
 */
@Component
public class OrderConverter {

    private final UserService userService;
    private final ProductService productService;
    /**
     * Конструктор контролера.
     *
     * @param userService Сервіс для роботи з користувачами.
     * @param productService Сервіс для роботи з продуктами.
     */
    public OrderConverter(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }
    /**
     * Конвертує об'єкт OrderDTO у відповідний об'єкт Order.
     *
     * @param orderDTO Об'єкт OrderDTO для конвертації.
     * @return Об'єкт Order, який є результатом конвертації.
     */
    public Order convertToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        Optional<User> customer = userService.getUserEntityById(orderDTO.getCustomerId());
        order.setCustomer(customer.get());
        Optional<User> manager = userService.getUserEntityById(orderDTO.getManagerId());
        order.setManager(manager.get());
        order.setId(orderDTO.getId());
        order.setOrderDateTime(orderDTO.getOrderDateTime());
        order.setStatus(Order.OrderStatus.valueOf(orderDTO.getStatus()));
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setAddress(orderDTO.getAddress());

        if (orderDTO.getOrderedProducts() != null) {
            List<OrderedProduct> orderedProducts = orderDTO.getOrderedProducts().stream()
                    .map(this::convertToOrderedProductEntity)
                    .collect(Collectors.toList());
            order.setOrderedProducts(orderedProducts);
        }

        return order;
    }
    /**
     * Конвертує об'єкт OrderedProductDTO у відповідний об'єкт OrderedProduct.
     *
     * @param orderedProductDTO Об'єкт OrderedProductDTO для конвертації.
     * @return Об'єкт OrderedProduct, який є результатом конвертації.
     */
    public OrderedProduct convertToOrderedProductEntity(OrderedProductDTO orderedProductDTO) {
        OrderedProduct orderedProduct = new OrderedProduct();
        Optional<Product> product = productService.getProductEntityById(orderedProductDTO.getProductId());
        orderedProduct.setProduct(product.get());
        orderedProduct.setQuantity(orderedProductDTO.getQuantity());
        orderedProduct.setPrice(orderedProductDTO.getPrice());
        return orderedProduct;
    }
    /**
     * Конвертує об'єкт Order у відповідний об'єкт OrderDTO.
     *
     * @param order Об'єкт Order для конвертації.
     * @return Об'єкт OrderDTO, який є результатом конвертації.
     */
    public OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerId(order.getCustomer().getId());
        orderDTO.setManagerId(order.getManager().getId());
        orderDTO.setOrderDateTime(order.getOrderDateTime());
        orderDTO.setStatus(order.getStatus().getDisplayName());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setAddress(order.getAddress());
        orderDTO.setId(order.getId());
        if (order.getOrderedProducts() != null) {
            List<OrderedProductDTO> orderedProductDTOs = order.getOrderedProducts().stream()
                    .map(this::convertToOrderedProductDTO)
                    .collect(Collectors.toList());
            orderDTO.setOrderedProducts(orderedProductDTOs);
        }

        return orderDTO;
    }
    /**
     * Конвертує об'єкт OrderedProduct у відповідний об'єкт OrderedProductDTO.
     *
     * @param orderedProduct Об'єкт OrderedProduct для конвертації.
     * @return Об'єкт OrderedProductDTO, який є результатом конвертації.
     */
    public OrderedProductDTO convertToOrderedProductDTO(OrderedProduct orderedProduct) {
        OrderedProductDTO orderedProductDTO = new OrderedProductDTO();
        orderedProductDTO.setProductId(orderedProduct.getProduct().getId());
        orderedProductDTO.setQuantity(orderedProduct.getQuantity());
        orderedProductDTO.setPrice(orderedProduct.getPrice());
        return orderedProductDTO;
    }
    /**
     * Конвертує список об'єктів Order у список відповідних об'єктів OrderDTO.
     *
     * @param orders Список об'єктів Order для конвертації.
     * @return Список об'єктів OrderDTO, який є результатом конвертації.
     */
    public List<OrderDTO> convertToListDto(List<Order> orders){
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
}
