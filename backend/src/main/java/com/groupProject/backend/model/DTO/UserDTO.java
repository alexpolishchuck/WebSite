package com.groupProject.backend.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.groupProject.backend.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 * \brief Клас, що представляє об'єкт користувача (DTO). Використовується для передачі даних про користувача між клієнтом і сервером.
 */
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    /**
     * Унікальний ідентифікатор користувача.
     */
    @NotNull(message = "Id is required")
    private Long id;

    /**
     * Унікальний ідентифікатор користувача в базі даних Auth0.
     */
    @NotNull(message = "auth0 id is required")
    private String auth0Id;

    /**
     * Ім'я користувача.
     */
    @NotNull(message = "firstName is required")
    private String firstName;

    /**
     * Прізвище користувача.
     */
    @NotNull(message = "lastName is required")
    private String lastName;

    /**
     * Логін користувача.
     */
    @NotNull(message = "login is required")
    private String login;

    /**
     * Адреса користувача.
     */
    @NotNull(message = "address is required")
    private String address;

    /**
     * Номер телефону користувача.
     */
    @NotNull(message = "phoneNumber is required")
    private String phoneNumber;

    /**
     * Роль користувача.
     */
    @NotNull(message = "role is required")
    private String role;
}

