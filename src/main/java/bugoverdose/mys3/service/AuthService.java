package bugoverdose.mys3.service;

import bugoverdose.mys3.exception.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final String authorizationCode;

    public AuthService(@Value("${security.authorization-key}") String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public void validate(String authorizationCode) {
        if (authorizationCode == null) {
            throw UnauthenticatedException.ofMissingAuthCode();
        }
        if (!this.authorizationCode.equals(authorizationCode)) {
            throw UnauthenticatedException.ofInvalidAuthCode();
        }
    }
}
