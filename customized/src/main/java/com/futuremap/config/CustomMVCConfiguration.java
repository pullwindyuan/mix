 package com.futuremap.config;

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.serializer.SerializerFeature;
 import com.alibaba.fastjson.support.config.FastJsonConfig;
 import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
 import com.fasterxml.jackson.annotation.JsonInclude;
 import com.fasterxml.jackson.databind.DeserializationFeature;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import com.futuremap.custom.interceptor.LogInterceptor;
 import com.google.common.collect.Lists;
 import org.modelmapper.ModelMapper;
 import org.modelmapper.convention.MatchingStrategies;
 import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.http.MediaType;
 import org.springframework.http.converter.HttpMessageConverter;
 import org.springframework.http.converter.StringHttpMessageConverter;
 import org.springframework.web.client.RestTemplate;
 import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
 import org.springframework.web.servlet.config.annotation.*;

 import java.nio.charset.Charset;
 import java.util.ArrayList;
 import java.util.List;

 @Configuration
 public class CustomMVCConfiguration<T>
   extends WebMvcConfigurationSupport
 {
   @Bean
   public HttpMessageConverters fastJsonHttpMessageConverters() {
     FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();


     List<MediaType> supportedMediaTypes = Lists.newArrayList();

     supportedMediaTypes.add(MediaType.APPLICATION_JSON);
     supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
     fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);


     FastJsonConfig fastJsonConfig = new FastJsonConfig();


     fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

     StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.defaultCharset());

     fastJsonConfig.setSerializerFeatures(new SerializerFeature[] { SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue });

     return new HttpMessageConverters(new HttpMessageConverter[] { (HttpMessageConverter)fastJsonHttpMessageConverter, (HttpMessageConverter)stringHttpMessageConverter });
   }

   @Bean
   public FastJsonHttpMessageConverter responseBodyConverter() {
     FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
     return converter;
   }

   @Override
   public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/**")
               .allowedOriginPatterns("*")
               .allowCredentials(true)
               .allowedMethods("*");
   }

   @Override
   public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
     FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
     List<MediaType> supportedMediaTypes = new ArrayList<>();
     supportedMediaTypes.add(MediaType.APPLICATION_JSON);
     supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
     supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
     supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
     supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
     supportedMediaTypes.add(MediaType.APPLICATION_PDF);
     supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
     supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
     supportedMediaTypes.add(MediaType.APPLICATION_XML);
     supportedMediaTypes.add(MediaType.IMAGE_GIF);
     supportedMediaTypes.add(MediaType.IMAGE_JPEG);
     supportedMediaTypes.add(MediaType.IMAGE_PNG);
     supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
     supportedMediaTypes.add(MediaType.TEXT_HTML);
     supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
     supportedMediaTypes.add(MediaType.TEXT_PLAIN);
     supportedMediaTypes.add(MediaType.TEXT_XML);
     converter.setSupportedMediaTypes(supportedMediaTypes);
     converter.setDefaultCharset(Charset.forName("UTF-8"));
     FastJsonConfig config = new FastJsonConfig();
     JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
     config.setSerializerFeatures(new SerializerFeature[] { SerializerFeature.WriteDateUseDateFormat });
     converter.setFastJsonConfig(config);
     converters.add(converter);
   }



   @Override
   public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
     configurer.favorPathExtension(false);
   }


   @Override
   protected void addResourceHandlers(ResourceHandlerRegistry registry) {
     registry.addResourceHandler(new String[] { "/**"
         }).addResourceLocations(new String[] { "classpath:/static/" });
     registry.addResourceHandler(new String[] { "swagger-ui.html"
         }).addResourceLocations(new String[] { "classpath:/META-INF/resources/" });
     registry.addResourceHandler(new String[] { "doc.html"
         }).addResourceLocations(new String[] { "classpath:/META-INF/resources/" });
     registry.addResourceHandler(new String[] { "/webjars/**"
         }).addResourceLocations(new String[] { "classpath:/META-INF/resources/webjars/" });
     super.addResourceHandlers(registry);
   }


     @Override
     public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
         handlers.add(new CustomResponseReturnValueHandler());
     }



     @Override
   public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
     configurer.enable();
   }

   @Bean
   public RestTemplate initRestTemplate() {
     return new RestTemplate();
   }

     @Override
     public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(logInterceptor());
     }

     @Bean
     public LogInterceptor logInterceptor() {
         return new LogInterceptor();
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
         return objectMapper;
     }
 }





