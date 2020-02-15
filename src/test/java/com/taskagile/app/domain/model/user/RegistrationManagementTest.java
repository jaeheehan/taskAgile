package com.taskagile.app.domain.model.user;

import com.taskagile.app.domain.common.security.PasswordEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegistrationManagementTest {

    private UserRepository repositoryMock;
    private PasswordEncryptor passwordEncryptorMock;
    private RegistrationManagement instance;

    @BeforeEach
    public void setUp() {
        repositoryMock = mock(UserRepository.class);
        passwordEncryptorMock = mock(PasswordEncryptor.class);
        instance = new RegistrationManagement(repositoryMock, passwordEncryptorMock);
    }

    @Test
    public void register_existedUsername_shouldFail() throws RegistrationException {

        String username = "existUsername";
        String emailAddress = "sunny@taskagile.com";
        String password = "MyPassword!";

        when(repositoryMock.findByUsername(username)).thenReturn(new User());

        assertThrows(UsernameExistsException.class, () -> {
            instance.register(username, emailAddress, password);
        });
    }

    @Test
    public void register_uppercaseEmailAddress_shouldSucceedAndBecomeLowercase() throws RegistrationException{

        String username = "existUsername";
        String emailAddress = "sunny@taskagile.com";
        String password = "MyPassword!";

        instance.register(username, emailAddress, password);
        User userToSave = User.create(username, emailAddress.toLowerCase(), password);
        verify(repositoryMock).save(userToSave);
    }

    /*
    @Test
    public void register_newUser_shouldSucceed() throws RegistrationException {

        String username = "existUsername";
        String emailAddress = "sunny@taskagile.com";
        String password = "MyPassword!";
        String encryptedPassword = "EncryptedPassword";
        User newUser = User.create(username, emailAddress, encryptedPassword);

        when(repositoryMock.findByUsername(username)).thenReturn(null);
        when(repositoryMock.findByEmailAddress(emailAddress)).thenReturn(null);
        doNothing().when(repositoryMock).save(newUser);
        when(passwordEncryptorMock.encrypt(password)).thenReturn("EncryptedPassword");

        User savedUser = instance.register(username, emailAddress, password);
        InOrder inOrder = inOrder(repositoryMock);
        inOrder.verify(repositoryMock).findByUsername(username);
        inOrder.verify(repositoryMock).findByEmailAddress(emailAddress);
        inOrder.verify(repositoryMock).save(newUser);
        verify(passwordEncryptorMock).encrypt(password);
        assertEquals(encryptedPassword, savedUser.getPassword(), "Saved User's password should be encrypted");
    }
    */
}