package com.futuremap.erp.config;

import com.futuremap.erp.common.auth.resource.*;
import com.futuremap.erp.common.security.IgnoreUrlsConfig;
import com.futuremap.erp.common.security.RestAccessDeniedHandler;
import com.futuremap.erp.common.security.JwtAuthorizationTokenFilter;
import com.futuremap.erp.common.security.RestAuthenticationEntryPoint;
import com.futuremap.erp.common.security.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtAuthorizationTokenFilter jwtAuthorizationTokenFilter;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

	@Autowired(required = false)
	private UrlDynamicSecurityServiceImpl dynamicSecurityService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

	@Bean
	public IgnoreUrlsConfig ignoreUrlsConfig() {
		return new IgnoreUrlsConfig();
	}
	@ConditionalOnBean(name = "urlDynamicSecurityServiceImpl")
	@Bean
	public UrlAccessDecisionManager urlAccessDecisionManager() {
		return new UrlAccessDecisionManager();
	}


	@ConditionalOnBean(name = "urlDynamicSecurityServiceImpl")
	@Bean
	public UrlDynamicSecurityFilter urlDynamicSecurityFilter() {
		return new UrlDynamicSecurityFilter();
	}

	@ConditionalOnBean(name = "urlDynamicSecurityServiceImpl")
	@Bean
	public UrlSecurityMetadataSource urlSecurityMetadataSource() {
		return new UrlSecurityMetadataSource();
	}

	@Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
					ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity
							.authorizeRequests();
					//??????????????????????????????????????????
					for (String url : ignoreUrlsConfig().getUrls()) {
						registry.antMatchers(url).permitAll();
					}
					// ????????????????????????????????????session
					registry.and()
					        .csrf()
					        .disable()
					        .sessionManagement()
					        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
							// ??????????????????????????????
							.and()
					        .exceptionHandling()
					        .authenticationEntryPoint(restAuthenticationEntryPoint).accessDeniedHandler(restAccessDeniedHandler)
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
							//?????????????????????OPTIONS??????
					        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					        .anyRequest().authenticated();

					// Custom JWT based authentication
		           registry.and().addFilterBefore(jwtAuthorizationTokenFilter, UsernamePasswordAuthenticationFilter.class);
					//?????????????????????????????????????????????????????????
					if(dynamicSecurityService!=null){
						registry.and().addFilterBefore(urlDynamicSecurityFilter(), FilterSecurityInterceptor.class);
					}

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
