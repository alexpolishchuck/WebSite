package com.groupProject.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

/**
 * \brief Клас, що представляє користувача системи.
 */
public class User {

    /**
     * Унікальний ідентифікатор користувача.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    /**
     * Унікальний ідентифікатор користувача в базі даних Auth0.
     */
    @Column(name="auth0_id")
    private String auth0Id;

    /**
     * Ім'я користувача.
     */
    @Column(name="first_name")
    private String firstName;

    /**
     * Прізвище користувача.
     */
    @Column(name="last_name")
    private String lastName;

    /**
     * Логін користувача.
     */
    @Column(name="login")
    private String login;

    /**
     * Адреса користувача.
     */
    @Column(name="address")
    private String address;

    /**
     * Номер телефону користувача.
     */
    @Column(name="phone_number")
    private String phoneNumber;

    /**
     * Роль користувача, яка визначає його привілеї в системі.
     */
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role;

    /**
     * Перелік ролей користувача.
     */
    public enum Role {
        ROLE_ADMIN("Admin"),
        ROLE_MANAGER("Manager"),
        ROLE_USER("Customer");

        private final String displayName;

        Role(String displayName) {
            this.displayName = displayName;
        }

        /**
         * Отримати відображувану назву ролі.
         *
         * @return Відображувана назва ролі.
         */
        public String getDisplayName() {
            return displayName;
        }
    }
}


