package com.taskagile.app.domain.application;

import com.taskagile.app.domain.application.commands.RegistrationCommand;
import com.taskagile.app.domain.model.user.RegistrationException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * Register a new user with username, email address, and password.
     *
     * @param command instance of <code>RegistrationCommand</code>
     * @throws RegistrationException when registration failed. Possible reasons are:
     *                               1) Username already exists
     *                               2) Email address already exists.
     */
    void register(RegistrationCommand command) throws RegistrationException;
}
