package com.taskagile.app.domain.common.mail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import freemarker.template.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class DefaultMailManagerTests {

    @Autowired
    private Configuration configuration;
    private Mailer mailerMock;
    private DefaultMailManager instance;

    @BeforeEach
    public void setUp() {
        mailerMock = mock(Mailer.class);
        instance = new DefaultMailManager("noreply@taskagile.com", mailerMock, configuration);
    }

    @Test
    public void send_nullEmailAddress_shouldFail() {
        assertThrows(IllegalArgumentException.class, () -> {
            instance.send(null, "Test subject", "test.ftl");
        });
    }

    @Test
    public void send_validParameters_shouldSucceed() {
        String to = "user@example.com";
        String subject = "Test subject";
        String templateName = "test.ftl";

        instance.send(to, subject, templateName, MessageVariable.from("name", "test"));
        ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);
        verify(mailerMock).send(messageArgumentCaptor.capture());

        Message messageSent = messageArgumentCaptor.getValue();
        System.out.println("messageSent = " + messageSent);

        assertEquals(to, messageSent.getTo());
        assertEquals(subject, messageSent.getSubject());
        assertEquals("noreply@taskagile.com", messageSent.getFrom());
        assertEquals("Hello, test", messageSent.getBody());
    }
}
