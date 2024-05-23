package com.marcusmonteirodesouza.rest.user.resources.users;

import com.marcusmonteirodesouza.rest.auth.services.AuthService;
import com.marcusmonteirodesouza.rest.common.exception.AlreadyExistsException;
import com.marcusmonteirodesouza.rest.user.repositories.user.UserRepository;
import com.marcusmonteirodesouza.rest.user.resources.users.dto.RegisterUserRequest;
import com.marcusmonteirodesouza.rest.user.resources.users.dto.UserResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/")
public class UserResource {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject private UserRepository userRepository;

    @Inject private AuthService authService;

    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public UserResponse registerUser(RegisterUserRequest request) throws AlreadyExistsException {
        logger.info(
                "Registering User. Username: "
                        + request.user.username
                        + ", Email: "
                        + request.user.email);

        var user =
                this.userRepository.createUser(
                        request.user.username, request.user.email, request.user.password);

        var token = authService.createJWT(user);

        return new UserResponse(
                user.getEmail(), user.getUsername(), token, user.getBio(), user.getImage());
    }
}
