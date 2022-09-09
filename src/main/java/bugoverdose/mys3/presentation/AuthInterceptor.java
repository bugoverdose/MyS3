package bugoverdose.mys3.presentation;

import bugoverdose.mys3.exception.UnauthenticatedException;
import bugoverdose.mys3.service.AuthService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String PLAIN_TEXT_CONTENT_TYPE = "text/plain";
    private static final String UTF_8_CHARACTER_ENCODING = "UTF-8";

    private final AuthService authService;

    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String method = request.getMethod();
        if (method.equals(HttpMethod.GET.name()) || method.equals(HttpMethod.OPTIONS.name())) {
            return true;
        }
        return validateAuthentication(request.getHeader(AUTHORIZATION), response);
    }

    private boolean validateAuthentication(String authorizationCode, HttpServletResponse response) {
        try {
            authService.validate(authorizationCode);
            return true;
        } catch (UnauthenticatedException e) {
            handleUnauthenticated(response, e.getMessage());
            return false;
        }
    }

    private void handleUnauthenticated(HttpServletResponse response, String errorMessage) {
        try {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(PLAIN_TEXT_CONTENT_TYPE);
            response.setCharacterEncoding(UTF_8_CHARACTER_ENCODING);
            final var writer = response.getWriter();
            writer.write(errorMessage);
        } catch (IOException ignored) {
        }
    }
}
