package com.taskagile.app.domain.model.user;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void save_nullUsernameUser_shouldFail(){
        assertThrows(DataIntegrityViolationException.class, () -> {
            User inValidUser = User.create(null, "sunny@taskagile.com", "myPassword");
            repository.save(inValidUser);
        });
    }

}