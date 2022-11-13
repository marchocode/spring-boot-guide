package xyz.chaobei;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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
        registry.addInterceptor(new IndexInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**");
    }

    /**
     * 配置默认的消息转换器，如果这里不进行配置的话，spring 会增加默认的消息处理器
     *
     * @param converters initially an empty list of converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        /**
         * if you don't add anything converters
         * the following HttpMessageConverter will be confined.
         *
         * AppConfig extendMessageConverters item =class org.springframework.http.converter.ByteArrayHttpMessageConverter
         * AppConfig extendMessageConverters item =class org.springframework.http.converter.StringHttpMessageConverter
         * AppConfig extendMessageConverters item =class org.springframework.http.converter.ResourceHttpMessageConverter
         * AppConfig extendMessageConverters item =class org.springframework.http.converter.ResourceRegionHttpMessageConverter
         * AppConfig extendMessageConverters item =class org.springframework.http.converter.xml.SourceHttpMessageConverter
         * AppConfig extendMessageConverters item =class org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter
         * AppConfig extendMessageConverters item =class org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter
         * AppConfig extendMessageConverters item =class org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
         */

        for (HttpMessageConverter<?> httpMessageConverter : converters) {
            log.info("AppConfig configureMessageConverters item ={}", httpMessageConverter.getClass());
        }

        Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder();

        jackson2ObjectMapperBuilder.indentOutput(true);
        jackson2ObjectMapperBuilder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);

        /**
         * 配置jdk8的 localDateTime 序列化与反序列化方式
         */
        jackson2ObjectMapperBuilder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        jackson2ObjectMapperBuilder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));

        converters.add(new MappingJackson2HttpMessageConverter(jackson2ObjectMapperBuilder.build()));
    }

    /**
     * 添加扩展的消息转换器，如果需要默认的消息处理器，则无需配置 configureMessageConverters 直接修改如下
     *
     * @param converters the list of configured converters to be extended
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        for (HttpMessageConverter<?> httpMessageConverter : converters) {
            log.info("AppConfig extendMessageConverters item ={}", httpMessageConverter.getClass());
        }
    }
    /**
     * 映射一个classpath 下的目录作为静态资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
