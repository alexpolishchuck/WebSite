package com.groupProject.backend.Converter;

import com.groupProject.backend.model.DTO.ProductDTO;
import com.groupProject.backend.model.DTO.UserDTO;
import com.groupProject.backend.model.Product;
import com.groupProject.backend.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    /**
     * Конвертує об'єкт UserDTO у відповідний об'єкт User.
     *
     * @param userDTO Об'єкт UserDTO для конвертації.
     * @return Об'єкт User, який є результатом конвертації.
     */
    public User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setLogin(userDTO.getLogin());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRole(User.Role.valueOf(userDTO.getRole()));
        user.setId(userDTO.getId());
        user.setAuth0Id(userDTO.getAuth0Id());
        return user;
    }
    /**
     * Конвертує об'єкт User у відповідний об'єкт UserDTO.
     *
     * @param user Об'єкт User для конвертації.
     * @return Об'єкт UserDTO, який є результатом конвертації.
     */
    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setLogin(user.getLogin());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(String.valueOf(user.getRole()));
        userDTO.setId(user.getId());
        userDTO.setAuth0Id(user.getAuth0Id());
        return userDTO;
    }
    /**
     * Конвертує список об'єктів User у список відповідних об'єктів UserDTO.
     *
     * @param users Список об'єктів User для конвертації.
     * @return Список об'єктів UserDTO, який є результатом конвертації.
     */
    public List<UserDTO> convertToListDto(List<User> users){
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
