package com.taskagile.app.domain.application.impl;

import com.taskagile.app.domain.common.event.DomainEventPublisher;
import com.taskagile.app.domain.common.mail.MailManager;
import com.taskagile.app.domain.model.user.RegistrationException;
import com.taskagile.app.domain.model.user.RegistrationManagement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class UserServiceImplTest {

    private RegistrationManagement registrationManagementMock;
    private DomainEventPublisher eventPublisherMock;
    private MailManager mailManagerMock;
    private UserServiceImpl instance;

    @BeforeEach
    public void setUp() {
        registrationManagementMock = mock(RegistrationManagement.class);
        eventPublisherMock = mock(DomainEventPublisher.class);
        mailManagerMock = mock(MailManager.class);
        instance = new UserServiceImpl(registrationManagementMock, eventPublisherMock, mailManagerMock);
    }

    @Test
    public void register_nullCommand_shouldFail() throws RegistrationException {

        Exception exception = assertThrows(IllegalArgumentException.class, ()->{
            instance.register(null);
        });
        System.out.println("exception = " + exception);
        
    }
}