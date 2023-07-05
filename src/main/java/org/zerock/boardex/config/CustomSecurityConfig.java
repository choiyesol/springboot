package org.zerock.boardex.config;

import groovy.util.logging.Log4j2;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.zerock.boardex.security.CustomUserDetailsService;
import org.zerock.boardex.security.handler.Custom403Handler;
import org.zerock.boardex.security.handler.CustomSocialLoginSuccessHandler;

import javax.sql.DataSource;

import java.nio.file.AccessDeniedException;

import static org.hibernate.query.sqm.tree.SqmNode.log;

// 로그인 화면
@Log4j2 // log.info 명령어를 수행할 수 있는 기능을 가진 어노테이션
@Configuration // 환경설정 파일을 만듬, @Bean 어노테이션과 같이 사용
@RequiredArgsConstructor // final 이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동생성해주는 lombok annotation

// 권한 설정에 필요한 어노테이션
// 권한 체크
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {
    private final DataSource dataSource;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomSocialLoginSuccessHandler(passwordEncoder());
    }

    @Bean // 수동으로 Bean 등록
    // SecurityFilterChain : 스프링부트에서 지원하는 기본 로그인 화면으로 접속하지 못하게 함
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        log.info("******** configure **********");
        // 사용자 인증처리
        http.formLogin().loginPage("/member/login");
        // csrf 토큰 비활성화 (아이디와 비밀번호 사용하여 로그인)
        http.csrf().disable();
        // rememberMe() : 자동 로그인
        http.rememberMe()
                // key() : 인증받은 사용자의 정보로 token 생성, 임의로 설정함
                // token 이란? 서버가 각각의 사용자를 구별하도록 교유한 정보를 담은 암호화 데이터
                .key("12345678")
                // tokenRepository() : rememberMe의 토큰 저장소
                .tokenRepository(persistentTokenRepository())
                // userDetailsService() : 유저의 정보를 가져오는 메서드
                .userDetailsService(customUserDetailsService)
                // tokenValiditySeconds() : 생성된 token의 만료시간(30일)
                .tokenValiditySeconds(60*60*24*30);
        // exceptionHandling() : Error발생시 후처리 설정
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        // 카카오 로그인하기
        http.oauth2Login().loginPage("/member/login")
                .successHandler(authenticationSuccessHandler());

        return http.build();
    }

    @Bean
    // WebSecurityCustomizer : 적용하지 않을 리소스룰 설정
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("****** web configure ******");
        return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }

    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    // 자동로그인을 위한 환경설정
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        // 쿠키 정보를 테이블로 저장하도록 설정
        JdbcTokenRepositoryImpl repo=new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

    // 정식 사용자가 아닌 경우 게시글 수정하려고 접근했을 시 서버에서 접근 거부하면 예외처리함
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new Custom403Handler();
    }

}
