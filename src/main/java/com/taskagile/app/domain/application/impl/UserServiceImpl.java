package com.taskagile.app.domain.application.impl;

import com.taskagile.app.domain.application.UserService;
import com.taskagile.app.domain.application.commands.RegistrationCommand;
import com.taskagile.app.domain.common.event.DomainEventPublisher;
import com.taskagile.app.domain.common.mail.MailManager;
import com.taskagile.app.domain.common.mail.MessageVariable;
import com.taskagile.app.domain.model.user.*;
import com.taskagile.app.domain.model.user.events.UserRegisteredEvent;
import com.taskagile.app.web.apis.authenticate.AuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private RegistrationManagement registrationManagement;
    private DomainEventPublisher domainEventPublisher;
    private MailManager mailManager;
    private UserRepository userRepository;

    public UserServiceImpl(RegistrationManagement registrationManagement,
                           DomainEventPublisher domainEventPublisher,
                           MailManager mailManager,
                           UserRepository userRepository) {
        this.registrationManagement = registrationManagement;
        this.domainEventPublisher = domainEventPublisher;
        this.mailManager = mailManager;
        this.userRepository = userRepository;
    }

    @Override
    public void register(RegistrationCommand command) throws RegistrationException {
        Assert.notNull(command, "Parameter `command` must not be null");
        User newUser = registrationManagement.register(command.getUsername(), command.getEmailAddress(), command.getPassword());
        sendWelcomeMessage(newUser);
        domainEventPublisher.publish(new UserRegisteredEvent(newUser));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("No user found");
        }

        User user;

        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        if (username.contains("@")){
            user = userRepository.findByEmailAddress(username);
        } else {
            user = userRepository.findByUsername(username);
        }

        if(user == null){
            throw new UsernameNotFoundException("No user found by `" + username + "`");
        }

        return new SimpleUser(user);
    }

    private void sendWelcomeMessage(User user) {
        mailManager.send(
                user.getEmailAddress(),
                "Welcome to TaskAgile",
                "weclome.ftl",
                MessageVariable.from("user", user)
        );
    }
}
