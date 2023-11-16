package com.groupProject.backend.service;

import com.groupProject.backend.Converter.OrderConverter;
import com.groupProject.backend.Converter.ShoppingCartEntityConverter;

import com.groupProject.backend.model.DTO.OrderDTO;
import com.groupProject.backend.model.DTO.ShoppingCartEntityDTO;
import com.groupProject.backend.model.User;

import com.groupProject.backend.repository.UserRepository;
import com.groupProject.backend.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * \brief Сервіс, який надає функціонал для операцій над об'єктами типу ShoppingCartEntity та оформлення замовлень.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;

    private final ShoppingCartEntityConverter shoppingCartEntityConverter;
    private final OrderConverter orderConverter;
    /**
     * Додати товар до корзини покупця.
     *
     * @param shoppingCartEntity Об'єкт ShoppingCartEntityDTO, що представляє товар, який додається до корзини.
     * @return Об'єкт типу Optional, що може містити ShoppingCartEntityDTO, якщо додавання товару успішне.
     */
    public Optional<ShoppingCartEntityDTO> addToCart(ShoppingCartEntityDTO shoppingCartEntity) {
        return Optional.ofNullable(shoppingCartEntityConverter.convertToDTO(shoppingCartRepository.save(shoppingCartEntityConverter.convertToEntity(shoppingCartEntity))));
    }
    /**
     * Отримати список товарів у корзині покупця за його ідентифікатором.
     *
     * @param customerId Ідентифікатор покупця.
     * @return Список товарів у форматі ShoppingCartEntityDTO, що містяться в корзині покупця.
     */
    public List<ShoppingCartEntityDTO> getCartItemsForCustomer(Long customerId) {
        Optional<User> customer = userRepository.findById(customerId);
        return  shoppingCartEntityConverter.convertToListDto(shoppingCartRepository.findAllByUser(customer.get()));
    }
    /**
     * Оформити замовлення на основі вмісту корзини покупця.
     *
     * @param customerId Ідентифікатор покупця, для якого оформляється замовлення.
     * @return Об'єкт типу Optional, що може містити OrderDTO, якщо оформлення замовлення успішне.
     */
    public Optional<OrderDTO> checkout(Long customerId) {
        Optional<User> customer = userRepository.findById(customerId);
        OrderDTO newOrder = null;
        return Optional.ofNullable(newOrder);
    }


    /**
     * Видалити товар із корзини покупця.
     *
     * @param shoppingCardItem Об'єкт ShoppingCartEntityDTO, що містить товар, який видаляється із корзини.
     * @return Логічне значення, що вказує на успішність видалення товару із корзини.
     */
    public boolean deleteShoppingCartItem(ShoppingCartEntityDTO shoppingCardItem) {
        shoppingCartRepository.deleteById(shoppingCardItem.getId());
        return true;
    }
    /**
     * Оновити кількість товару у корзині покупця.
     *
     * @param shoppingCardItem Об'єкт ShoppingCartEntityDTO, що містить дані для оновлення кількості товару у корзині.
     * @return Об'єкт типу ShoppingCartEntityDTO, що містить оновлену інформацію про товар у корзині.
     */
    public ShoppingCartEntityDTO updateShoppingCardItem(ShoppingCartEntityDTO shoppingCardItem) {
        return  shoppingCartEntityConverter.convertToDTO(shoppingCartRepository.save(shoppingCartEntityConverter.convertToEntity(shoppingCardItem)));
    }

     /*
    // Метод для просмотра содержимого корзины для конкретного клиента
    public List<ShoppingCartEntity> getCartItemsForCustomer(Customer customer) {
        return shoppingCartRepository.findByCustomer(customer);
    }

    // Метод для оформления заказа на основе содержимого корзины
   public Order checkout(Customer customer) {

        return createdOrder;
    }
   */
}
