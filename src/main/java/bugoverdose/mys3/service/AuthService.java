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
            throw new UnauthenticatedException("인증 코드 정보가 누락되었습니다.");
        }
        if (!this.authorizationCode.equals(authorizationCode)) {
            throw new UnauthenticatedException("잘못된 인증 정보입니다.");
        }
    }
}
