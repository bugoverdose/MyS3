package bugoverdose.mys3.config;

import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Configuration
public class CacheConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        final var interceptor = new WebContentInterceptor();
        interceptor.addCacheMapping(defaultCacheControl(), "/images/**");
        registry.addInterceptor(interceptor);
    }

    private CacheControl defaultCacheControl() {
        return CacheControl.maxAge(600, TimeUnit.SECONDS)
                .cachePublic();
    }
}
