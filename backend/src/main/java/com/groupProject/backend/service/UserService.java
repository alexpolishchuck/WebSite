package com.groupProject.backend.service;
import com.groupProject.backend.Converter.UserConverter;

import com.groupProject.backend.model.DTO.UserDTO;
import com.groupProject.backend.model.User;
import com.groupProject.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * \brief Сервіс, який надає функціонал для операцій над користувачами (клієнтами) системи.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserConverter userConverter;
    /**
     * Зареєструвати користувача в системі.
     *
     * @param user Об'єкт UserDTO, що містить дані про користувача для реєстрації.
     * @return Об'єкт типу Optional, що може містити UserDTO, якщо реєстрація успішна.
     */
    public Optional<UserDTO> registerUser(UserDTO user) {
        return Optional.ofNullable(userConverter.convertToDTO(userRepository.save(userConverter.convertToEntity(user))));
    }
    /**
     * Отримати список всіх користувачів системи.
     *
     * @return Список користувачів у форматі UserDTO.
     */
    public List<UserDTO> getAllUsers() {
        return  userConverter.convertToListDto(userRepository.findAll());
    }
    /**
     * Отримати інформацію про користувача за його ідентифікатором.
     *
     * @param customerId Ідентифікатор користувача.
     * @return Об'єкт типу Optional, що може містити UserDTO, якщо користувача знайдено за вказаним ідентифікатором.
     */
    public Optional<UserDTO> findUserById(Long customerId) {
        return Optional.ofNullable(userConverter.convertToDTO(userRepository.findById(customerId).get()));
    }
    /**
     * Оновити інформацію про користувача.
     *
     * @param user Об'єкт UserDTO, що містить оновлені дані про користувача.
     * @return Об'єкт типу Optional, що може містити оновлену інформацію про користувача, якщо оновлення успішне.
     */
    public Optional<UserDTO> updateUser(UserDTO user) {
        return Optional.ofNullable(userConverter.convertToDTO(userRepository.save(userConverter.convertToEntity(user))));
    }
    /**
     * Видалити користувача із системи за його ідентифікатором.
     *
     * @param customerId Ідентифікатор користувача, якого слід видалити.
     */
    public void deleteUser(Long customerId) {
        userRepository.deleteById(customerId);
    }

    /**
     * Отримати інформацію про користувача за його ідентифікатором.
     *
     * @param userId Ідентифікатор користувача.
     * @return Об'єкт типу Optional, що може містити UserDTO, якщо користувача знайдено за вказаним ідентифікатором.
     */
    public Optional<User> getUserEntityById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Отримати інформацію про користувача за його Auth0 ідентифікатором.
     *
     * @param auth0Id Auth0 ідентифікатор користувача.
     * @return Об'єкт типу Optional, що може містити UserDTO, якщо користувача знайдено за вказаним ідентифікатором.
     */
    public Optional<User> getUserEnityByAuth0Id(String auth0Id)
    {
        return userRepository.findByAuth0Id(auth0Id);
    };
}

