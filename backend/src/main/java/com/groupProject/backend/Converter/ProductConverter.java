package com.groupProject.backend.Converter;

import com.groupProject.backend.model.DTO.ProductDTO;
import com.groupProject.backend.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
/**
 * \brief Клас-конвертер для ковертації класу Product і класу ProductDTO.
 */
@Component
public class ProductConverter {
    /**
     * Конвертує об'єкт ProductDTO у відповідний об'єкт Product.
     *
     * @param productDTO Об'єкт ProductDTO для конвертації.
     * @return Об'єкт Product, який є результатом конвертації.
     */
    public Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setCategory(productDTO.getCategory());
        product.setImageURL(productDTO.getImageURL());
        product.setId(productDTO.getId());

        return product;
    }
    /**
     * Конвертує об'єкт Product у відповідний об'єкт ProductDTO.
     *
     * @param product Об'єкт Product для конвертації.
     * @return Об'єкт ProductDTO, який є результатом конвертації.
     */
    public ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStockQuantity(product.getStockQuantity());
        productDTO.setCategory(product.getCategory());
        productDTO.setImageURL(product.getImageURL());
        productDTO.setId(product.getId());
        return productDTO;
    }
    /**
     * Конвертує список об'єктів Product у список відповідних об'єктів ProductDTO.
     *
     * @param products Список об'єктів Product для конвертації.
     * @return Список об'єктів ProductDTO, який є результатом конвертації.
     */
    public List<ProductDTO> convertToListDto(List<Product> products){
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
