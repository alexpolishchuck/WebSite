package com.groupProject.backend.controller;

import com.groupProject.backend.model.DTO.UserDTO;
import com.groupProject.backend.security.Auth0Management;
import com.groupProject.backend.service.UserService;
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
 * \brief Клас-контролер для управління користувачами.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);
    private final UserService userService;
    private final Auth0Management auth0Management;
    /**
     * Конструктор контролера.
     *
     * @param userService     Сервіс для роботи з користувачами.
     * @param auth0Management Сервіс для взаємодії з даними користувачів Auth0.
     */
    @Autowired
    public UserController(UserService userService, Auth0Management auth0Management) {
        this.userService = userService;
        this.auth0Management = auth0Management;
    }
    /**
     * Реєструє нового користувача в системі.
     *
     * @param user Дані користувача для реєстрації.
     * @return Відповідь, яка містить зареєстрованого користувача або код помилки, якщо реєстрація не вдалася.
     */
    @PostMapping
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO user) {
        logger.info("Received a request to register User{}", user);
        Optional<UserDTO> registeredUser = userService.registerUser(user);
        if(!registeredUser.isPresent())
        {
            logger.error("Failed  request to register User{}", user);
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(registeredUser.get(), HttpStatus.CREATED);
    }
    /**
     * Отримує список всіх користувачів (доступно тільки для користувачів з роллю "Менеджер").
     *
     * @return Відповідь, яка містить список всіх користувачів або код помилки, якщо користувачі не знайдені.
     */
    @GetMapping
    @PreAuthorize("hasRole('Manager')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("Received a request to get All Users");
        List<UserDTO> users = userService.getAllUsers();
        if(users.isEmpty())
        {
            logger.error("Failed request to get All Users");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    /**
     * Отримує користувача за ідентифікатором.
     *
     * @param userId Ідентифікатор користувача.
     * @return Відповідь, яка містить дані користувача або код помилки, якщо користувач не знайдений.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        logger.info("Received a request to get User by id{}",userId);
        Optional<UserDTO> customer = userService.findUserById(userId);
        if(!customer.isPresent())
        {
            logger.error("Failed request to get User by id{}",userId);
        }
        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * Оновлює дані користувача за ідентифікатором.
     *
     * @param userId Ідентифікатор користувача, якого потрібно оновити.
     * @param user   Оновлені дані користувача.
     * @return Відповідь, яка містить оновлені дані користувача або код помилки, якщо оновлення не вдалося.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDTO user) {
        logger.info("Received a request to update User{} by id{}",user,userId);

        Optional<UserDTO> oldUser = userService.findUserById(userId);
        if (!oldUser.isPresent())
        {
            logger.error("Failed request to update User{} by id{}", user, userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String auth0Id = oldUser.get().getAuth0Id();

        if(!auth0Management.updateUserEmail(auth0Id, user.getLogin()))
        {
            logger.error("Failed request to update User{} on Auth0 id{}", user, auth0Id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setId(userId);
        user.setAuth0Id(auth0Id);
        Optional<UserDTO> updatedUser = userService.updateUser(user);

        if(!updatedUser.isPresent())
        {
            logger.error("Failed request to update User{}",user);
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(updatedUser.get(), HttpStatus.OK);
    }
    /**
     * Видаляє користувача за ідентифікатором.
     *
     * @param userId Ідентифікатор користувача, якого потрібно видалити.
     * @return Відповідь, яка підтверджує успішне видалення або код помилки, якщо видалення не вдалося.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        logger.info("Received a request to delete User by id{}",userId);

        Optional<UserDTO> user = userService.findUserById(userId);

        if (!user.isPresent())
        {
            logger.error("Failed request to delete User by id{}",userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String auth0Id = user.get().getAuth0Id();

        if(!auth0Management.deleteUser(auth0Id))
        {
            logger.error("Failed request to delete User on Auth0 by id{}",auth0Id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
