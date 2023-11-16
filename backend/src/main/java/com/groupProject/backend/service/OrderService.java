package com.groupProject.backend.service;
import com.groupProject.backend.Converter.OrderConverter;
import com.groupProject.backend.model.DTO.OrderDTO;
import com.groupProject.backend.model.Order;
import com.groupProject.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
/**
 * \brief Сервіс, який надає функціонал для операцій над об'єктами типу Order.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    /**
     * Отримати список усіх замовлень у системі.
     *
     * @return Список усіх замовлень у форматі OrderDTO.
     */
    public List<OrderDTO> getAllOrders() {
        return orderConverter.convertToListDto(orderRepository.findAll());
    }
    /**
     * Отримати замовлення за його ідентифікатором.
     *
     * @param orderId Ідентифікатор замовлення.
     * @return Об'єкт типу Optional, що може містити OrderDTO, якщо замовлення знайдено.
     */
    public Optional<OrderDTO> getOrderById(Long orderId) {
        return Optional.ofNullable(orderConverter.convertToDTO(orderRepository.findById(orderId).get()));
    }
    /**
     * Отримати замовлення за його ідентифікатором у вигляді об'єкта Order.
     *
     * @param orderId Ідентифікатор замовлення.
     * @return Об'єкт типу Optional, що може містити Order, якщо замовлення знайдено.
     */
    public Optional<Order> getOrderEntityById(Long orderId) {
        return orderRepository.findById(orderId);
    }
    /**
     * Отримати список замовлень для конкретного користувача за його ідентифікатором.
     *
     * @param customerId Ідентифікатор користувача.
     * @return Список замовлень у форматі OrderDTO.
     */
    public List<OrderDTO> getOrderByCustomerId(Long customerId) {
        return  orderConverter.convertToListDto(orderRepository.findAllByCustomerId(customerId));
    }
    /**
     * Отримати список замовлень для конкретного менеджера за його ідентифікатором.
     *
     * @param orderId Ідентифікатор менеджера.
     * @return Список замовлень у форматі OrderDTO.
     */
    public List<OrderDTO> getOrderByManagerId(Long orderId) {
        return orderConverter.convertToListDto(orderRepository.findAllByManagerId(orderId));
    }
    /**
     * Створити нове замовлення на основі об'єкта OrderDTO.
     *
     * @param order Об'єкт OrderDTO, що представляє замовлення.
     * @return Об'єкт типу Optional, що може містити OrderDTO, якщо створення успішне.
     */
    public Optional<OrderDTO> createOrder(OrderDTO order) {
        return Optional.ofNullable(orderConverter.convertToDTO(orderRepository.save(orderConverter.convertToEntity(order))));
    }

    /**
     * Видалити замовлення за його ідентифікатором.
     *
     * @param orderId Ідентифікатор замовлення.
     */
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
    /**
     * Оновити інформацію про замовлення на основі об'єкта OrderDTO.
     *
     * @param order Об'єкт OrderDTO, що містить нові дані для замовлення.
     * @return Об'єкт типу Optional, що може містити оновлене OrderDTO, якщо оновлення успішне.
     */
    public Optional<OrderDTO> updateOrder(OrderDTO order) {
        return Optional.ofNullable(orderConverter.convertToDTO(orderRepository.save(orderConverter.convertToEntity(order))));
    }
}
