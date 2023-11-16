package com.groupProject.backend.repository;

import com.groupProject.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * \brief Репозиторій для доступу до даних користувачів (User) у базі даних.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    @Query("select user from User user where user.auth0Id = ?1")
    Optional<User> findByAuth0Id(String auth0Id);
}