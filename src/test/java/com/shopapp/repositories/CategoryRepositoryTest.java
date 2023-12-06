package com.shopapp.repositories;

import com.shopapp.models.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest");

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @Sql("/multiple-categories.sql")
    void whenFindAllPageCategory_thenReturnContentPage() {

        Page<Category> categoryPage = categoryRepository.findAll(PageRequest.of(0, 10));

        assertThat(categoryPage.getContent().size()).isEqualTo(2);
    }

}