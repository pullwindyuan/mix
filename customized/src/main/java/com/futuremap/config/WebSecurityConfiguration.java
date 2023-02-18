package com.futuremap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
					ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity
							.authorizeRequests();
					// 关闭跨站请求防护及不使用session
					registry.and()
					        .csrf()
					        .disable()
					        .sessionManagement()
					        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
							// 自定义权限拒绝处理类
							.and()
					        .exceptionHandling()
							.and()
					        .authorizeRequests()
						    .antMatchers("/auth/**",
											"/csrf",
											"/v2/api-docs",
											"/definitions/**",
											"/configuration/ui",
											"/swagger-resources/**",
											"/configuration/security",
											"/swagger-ui.html",
											"/*.html",
											"*.html",
											"/webjars/**",
											"/swagger-resources/configuration/ui",
											"swagger-ui.html",
											"/sys/register",
											"/sys/login"
									        ,"/**"
						    		        ).permitAll()
							//允许跨域请求的OPTIONS请求
					        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					        .anyRequest().authenticated();

}

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
						"/*.css",
						"/*.png",
						"/*.woff",
						"/*.ttf",
						"/*.js",
                        "/**/*.css",
						"/**/*.png",
						"/**/*.woff",
						"/**/*.ttf",
                        "/*.js"

                )
				.antMatchers(
						HttpMethod.POST,
						"/**/**",
						"/saleorder/**"
				);
    }


}
