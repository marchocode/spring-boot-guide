package xyz.chaobei;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.chaobei.interceptor.IndexInterceptor;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Configuration
@ComponentScan(basePackages = "xyz.chaobei")
@EnableWebMvc
@Slf4j
public class AppConfig implements WebMvcConfigurer {

    /**
     * 时间戳转换
     */
    private static class DateFormatter implements Formatter<Date> {
        @Override
        public Date parse(String text, Locale locale) throws ParseException {
            log.info("DateFormatter text={}", text);
            return new Date(Long.valueOf(text));
        }

        @Override
        public String print(Date object, Locale locale) {
            log.info("DateFormatter object={}", object);
            return object.getTime() + "";
        }
    }

    /**
     * 时间戳转换为
     */
    private static class StringToLocalDateTime implements Converter<String, LocalDateTime> {
        @Override
        public LocalDateTime convert(String source) {
            log.info("StringToLocalDateTime source={}", source);
            Date date = new Date(Long.valueOf(source));
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        }
    }

    /**
     * 添加格式专函注册器,用于将 servlet query params 基于string 类型的参数转换为指定类型
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {

        log.info("AppConfig addFormatters");
        registry.addFormatterForFieldType(Date.class, new DateFormatter());
        // registry.addConverter(new StringToLocalDateTime());

        DateTimeFormatterRegistrar dateTimeFormatterRegistrar = new DateTimeFormatterRegistrar();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINESE);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.CHINESE);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);

        dateTimeFormatterRegistrar.setDateFormatter(dateFormatter);
        dateTimeFormatterRegistrar.setTimeFormatter(timeFormatter);
        dateTimeFormatterRegistrar.setDateTimeFormatter(dateTimeFormatter);

        /**
         * 		registry.addFormatterForFieldType(Instant.class, new InstantFormatter());
         * 		registry.addFormatterForFieldType(Period.class, new PeriodFormatter());
         * 		registry.addFormatterForFieldType(Duration.class, new DurationFormatter());
         * 		registry.addFormatterForFieldType(Year.class, new YearFormatter());
         * 		registry.addFormatterForFieldType(Month.class, new MonthFormatter());
         * 		registry.addFormatterForFieldType(YearMonth.class, new YearMonthFormatter());
         * 		registry.addFormatterForFieldType(MonthDay.class, new MonthDayFormatter());
         */

        // register many dateTime
        dateTimeFormatterRegistrar.registerFormatters(registry);
    }

    /**
     * 指定验证对象
     *
     * @return
     */
    @Override
    public Validator getValidator() {
        return new LocalValidatorFactoryBean();
    }

    /**
     * 指定拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IndexInterceptor()).addPathPatterns("/**");
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> httpMessageConverter : converters) {
            log.info("AppConfig configureMessageConverters item ={}", httpMessageConverter.getClass());
        }
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        /**
         * [RMI TCP Connection(3)-127.0.0.1] INFO xyz.chaobei.AppConfig - AppConfig extendMessageConverters item =class org.springframework.http.converter.ByteArrayHttpMessageConverter
         * [RMI TCP Connection(3)-127.0.0.1] INFO xyz.chaobei.AppConfig - AppConfig extendMessageConverters item =class org.springframework.http.converter.StringHttpMessageConverter
         * [RMI TCP Connection(3)-127.0.0.1] INFO xyz.chaobei.AppConfig - AppConfig extendMessageConverters item =class org.springframework.http.converter.ResourceHttpMessageConverter
         * [RMI TCP Connection(3)-127.0.0.1] INFO xyz.chaobei.AppConfig - AppConfig extendMessageConverters item =class org.springframework.http.converter.ResourceRegionHttpMessageConverter
         * [RMI TCP Connection(3)-127.0.0.1] INFO xyz.chaobei.AppConfig - AppConfig extendMessageConverters item =class org.springframework.http.converter.xml.SourceHttpMessageConverter
         * [RMI TCP Connection(3)-127.0.0.1] INFO xyz.chaobei.AppConfig - AppConfig extendMessageConverters item =class org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter
         * [RMI TCP Connection(3)-127.0.0.1] INFO xyz.chaobei.AppConfig - AppConfig extendMessageConverters item =class org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter
         * [RMI TCP Connection(3)-127.0.0.1] INFO xyz.chaobei.AppConfig - AppConfig extendMessageConverters item =class org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
         */

        for (HttpMessageConverter<?> httpMessageConverter : converters) {
            log.info("AppConfig extendMessageConverters item ={}", httpMessageConverter.getClass());
        }
    }
}
