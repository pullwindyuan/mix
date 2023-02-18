package com.futuremap.erp.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(logInterceptor());
//    }
//
//    @Bean
//    public MyDataScopeInterceptor logInterceptor() {
//        return new MyDataScopeInterceptor();
//    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("*");
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(context -> context.getSource() != null);
        modelMapper.getConfiguration().setAmbiguityIgnored(false);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // LocalDateTime系列序列化模块，继承自jsr310，我们在这里修改了日期格式
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(
                LocalDateTime.class,
                new JsonSerializer<LocalDateTime>() {
                    @Override
                    public void serialize(
                            LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
                            throws IOException {
                        String format =
                                value.atZone(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                        gen.writeString(format);
                    }
                });

        javaTimeModule.addSerializer(
                LocalDate.class,
                new JsonSerializer<LocalDate>() {
                    @Override
                    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers)
                            throws IOException {
                        String format = value.format(DateTimeFormatter.ISO_OFFSET_DATE);
                        gen.writeString(format);
                    }
                });

        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }
    /** String --> LocalDate */
    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(@NotNull String source) {
                if (StringUtils.hasText(source)) {
                    return LocalDate.parse(source, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                }
                return null;
            }
        };
    }

    /** String --> LocalDatetime */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(@NotNull String source) {
                if (StringUtils.hasText(source)) {
                    return LocalDateTime.parse(source, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                }
                return null;
            }
        };
    }

    /** String --> LocalTime */
    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(@NotNull String source) {
                if (StringUtils.hasText(source)) {
                    return LocalTime.parse(source, DateTimeFormatter.ISO_OFFSET_TIME);
                }
                return null;
            }
        };
    }
  
}
