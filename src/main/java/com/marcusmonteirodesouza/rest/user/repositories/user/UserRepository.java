package com.marcusmonteirodesouza.rest.user.repositories.user;

import com.marcusmonteirodesouza.rest.common.exception.AlreadyExistsException;
import com.marcusmonteirodesouza.rest.user.models.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCrypt;

@ApplicationScoped
public class UserRepository {
    @PersistenceContext(name = "jpa-unit")
    private EntityManager em;

    public User createUser(String username, String email, String password)
            throws AlreadyExistsException {
        validateUsername(username);

        validateEmail(email);

        var passwordHash = hashPassword(password);

        var user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);

        em.persist(user);

        return user;
    }

    private void validateUsername(String username) throws AlreadyExistsException {
        if (doesUserExist("username", username)) {
            throw new AlreadyExistsException("Username " + username + " is taken");
        }
    }

    private void validateEmail(String email) throws AlreadyExistsException {
        if (doesUserExist("email", email)) {
            throw new AlreadyExistsException("Email " + email + " is taken");
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Email " + email + " is invalid");
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean doesUserExist(String fieldName, Object fieldValue) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var root = cq.from(User.class);
        cq.select(cb.count(root)).where(cb.equal(root.get(fieldName), fieldValue));
        var count = em.createQuery(cq).getSingleResult();
        return count > 0;
    }
}
