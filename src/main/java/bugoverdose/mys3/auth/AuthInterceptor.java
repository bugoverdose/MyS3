package bugoverdose.mys3.auth;

import bugoverdose.mys3.exception.UnauthenticatedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    private final AuthService authService;

    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        String method = request.getMethod();
        if (method.equals(HttpMethod.GET.name()) || method.equals(HttpMethod.OPTIONS.name())) {
            return true;
        }
        try {
            authService.validate(request.getHeader(AUTHORIZATION));
            return true;
        } catch (UnauthenticatedException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }
}
