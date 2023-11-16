package com.groupProject.backend.Converter;

import com.groupProject.backend.model.DTO.ShoppingCartEntityDTO;
import com.groupProject.backend.model.Product;
import com.groupProject.backend.model.ShoppingCartEntity;
import com.groupProject.backend.model.User;
import com.groupProject.backend.service.ProductService;
import com.groupProject.backend.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * \brief Клас-конвертер для ковертації класу ShoppingCartEntity і класу ShoppingCartEntityDTO.
 */
@Component
public class ShoppingCartEntityConverter {

    private final UserService userService;
    private final ProductService productService;
    /**
     * Конструктор контролера.
     *
     * @param userService Сервіс для роботи з користувачами.
     * @param productService Сервіс для роботи з продуктами.
     */
    public ShoppingCartEntityConverter(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }
    /**
     * Конвертує об'єкт ShoppingCartEntityDTO у відповідний об'єкт ShoppingCartEntity.
     *
     * @param shoppingCartEntityDTO Об'єкт ShoppingCartEntityDTO для конвертації.
     * @return Об'єкт ShoppingCartEntity, який є результатом конвертації.
     */
    public ShoppingCartEntity convertToEntity(ShoppingCartEntityDTO shoppingCartEntityDTO) {
        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        Optional<Product> product = productService.getProductEntityById(shoppingCartEntityDTO.getProductId());
        Optional<User> user = userService.getUserEntityById(shoppingCartEntityDTO.getUserId());
        shoppingCartEntity.setUser(user.get());
        shoppingCartEntity.setProduct(product.get());
        shoppingCartEntity.setQuantity(shoppingCartEntityDTO.getQuantity());
        shoppingCartEntity.setId(shoppingCartEntityDTO.getId());
        return shoppingCartEntity;
    }

    /**
     * Конвертує об'єкт ShoppingCartEntity у відповідний об'єкт ShoppingCartEntityDTO.
     *
     * @param shoppingCartEntity Об'єкт ShoppingCartEntity для конвертації.
     * @return Об'єкт ShoppingCartEntityDTO, який є результатом конвертації.
     */
    public ShoppingCartEntityDTO convertToDTO(ShoppingCartEntity shoppingCartEntity) {
        ShoppingCartEntityDTO shoppingCartEntityDTO = new ShoppingCartEntityDTO();
        shoppingCartEntityDTO.setUserId(shoppingCartEntity.getUser().getId());
        shoppingCartEntityDTO.setProductId(shoppingCartEntity.getProduct().getId());
        shoppingCartEntityDTO.setQuantity(shoppingCartEntity.getQuantity());
        shoppingCartEntityDTO.setId(shoppingCartEntity.getId());
        return shoppingCartEntityDTO;
    }
    /**
     * Конвертує список об'єктів ShoppingCartEntity у список відповідних об'єктів ShoppingCartEntityDTO.
     *
     * @param shoppingCartEntities Список об'єктів ShoppingCartEntity для конвертації.
     * @return Список об'єктів ShoppingCartEntityDTO, який є результатом конвертації.
     */
    public List<ShoppingCartEntityDTO> convertToListDto(List<ShoppingCartEntity> shoppingCartEntities){
        return shoppingCartEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
