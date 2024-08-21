package com.yanren.websocketexample.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 所有請求皆通行，
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("chat-auth.html").authenticated()
//                            .requestMatchers(PathRequest.toH2Console()).permitAll()
                            .anyRequest().permitAll()
//                            .anyRequest().authenticated()
                    ;
                })
                .formLogin(Customizer.withDefaults())
                // 以下為h2-console開啟1.允許iframe 2.無須csrf_token
                .headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin))
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(PathRequest.toH2Console())
                        // csrf 保護預設開啟，若不忽略則POST請求頭需加上X-CSRF-TOKEN欄(可以看signup.html 的js)
                        .ignoringRequestMatchers("/user/signup")
                )
        ;

        return http.build();
    }

    @Bean
    UserDetailsManager userDetailsService(DataSource dataSource) {
        UserDetails user1 = User.builder()
                .username("yanren")
                .password("{noop}1234")
                .roles("USER")
                .build();
        UserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        if (!userDetailsManager.userExists(user1.getUsername())) {
            userDetailsManager.createUser(user1);
        }
        return userDetailsManager;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
