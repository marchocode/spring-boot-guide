package xyz.chaobei;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "xyz.chaobei")
@EnableWebMvc
@Slf4j
public class AppConfig implements WebMvcConfigurer {

    /**
     * 注册一个MultipartResolver,可以使 ServletRequest 转转为 MultipartServletRequest
     *
     * @return
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        // org.springframework.format.support.DefaultFormattingConversionService
        log.info("addFormatters FormatterRegistry class={}", registry.getClass());
    }
}
