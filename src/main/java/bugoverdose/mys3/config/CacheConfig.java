package bugoverdose.mys3.config;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Configuration
public class CacheConfig implements WebMvcConfigurer {

    private final int maxAge;

    public CacheConfig(@Value("${cache.max-age}") int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        final var interceptor = new WebContentInterceptor();
        interceptor.addCacheMapping(cacheControl(), "/images/**");
        registry.addInterceptor(interceptor);
    }

    private CacheControl cacheControl() {
        return CacheControl.maxAge(maxAge, TimeUnit.SECONDS).cachePublic();
    }
}
