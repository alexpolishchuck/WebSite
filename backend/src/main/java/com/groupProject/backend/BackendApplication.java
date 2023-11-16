package com.groupProject.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * \brief Головний клас застосунку. Запускає веб-застосунок.
 */
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        try
        {
            SpringApplication.run(BackendApplication.class, args);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
