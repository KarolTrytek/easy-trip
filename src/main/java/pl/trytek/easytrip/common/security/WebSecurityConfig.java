package pl.trytek.easytrip.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@CrossOrigin
public class WebSecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

       // http.cors().configurationSource(corsConfigurationSource());

        http.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "http://localhost:4200"));
        http.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT"));
        http.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Content-Type, Authorization"));
        http.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true"));

        http.authorizeRequests(
                        (autz) -> autz
                                .antMatchers("/dawwa").authenticated()
                                //.antMatchers("/api/user").authenticated()
                                //.antMatchers("/api/favourite/*").authenticated()
                                //.antMatchers("/api/user/list").hasAuthority("admin") //gdy odkomentowane to nie przechodzi cors przez 320 na options
                                )
                .formLogin().permitAll().and()
                //.cors().and()
                //.cors().configurationSource(corsConfigurationSource()).and()
                .csrf().disable();


        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilter(new CustomAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))));
        //http.addFilter(new WebCorsConfig());
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        //http.cors().configurationSource(corsConfigurationSource());
       // http.cors().configurationSource(corsConfigurationSource());

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Requestor-Type"));
        configuration.setExposedHeaders(List.of("X-Get-Header"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Bean
//    public WebMvcConfigurer corsMappingConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//               registry.addMapping("/easy-trip/login").allowedOrigins("http://localhost:4200");
//            }
//        };
//    }
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                WebConfigProperties.Cors cors = webConfigProperties.getCors();
//                registry.addMapping("/**")
//                        .allowedOrigins(cors.getAllowedOrigins())
//                        .allowedMethods(cors.getAllowedMethods())
//                        .maxAge(cors.getMaxAge())
//                        .allowedHeaders(cors.getAllowedHeaders())
//                        .exposedHeaders(cors.getExposedHeaders());
//            }
//        };
//    }
}
