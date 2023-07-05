package org.zerock.boardex.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.boardex.dto.MemberSecurityDTO;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
// AuthenticationSuccessHandler : 로그인 성공 후 특정 동작을 제어하기 위한 인터페이스
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {
    // PasswordEncoder : 비밀번호를 암호화 하는 객체
    private final PasswordEncoder passwordEncoder;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse
     response, Authentication authentication) throws IOException, ServletException {
        log.info("==========================");
        log.info("카카오 로그인 핸들러");
        // getPrincipal() : 로그인한 객체의 사용자 정보를 가져옴
        log.info(authentication.getPrincipal());
        MemberSecurityDTO memberSecurityDTO=(MemberSecurityDTO) authentication.getPrincipal();
        String encodePw=memberSecurityDTO.getMpw();
        // 소셜로그인(카카오 로그인)이고 회원의 비밀번호는 1111
        // social 자료형이 boolean > true or false 값을 가짐
        if (memberSecurityDTO.isSocial() && memberSecurityDTO.getMpw().equals("1111")
         // 가져온 아이디의 암호화한 비밀번호가 1111과 일치하냐,
         || passwordEncoder.matches("1111",memberSecurityDTO.getMid())) {
            log.info("비밀번호를 변경해주십시오");
            log.info("회원 정보를 수정하여 다시 접속하십시오");
            response.sendRedirect("/member/modify");
            return;
        }else {
            response.sendRedirect("/board/list");
        }


    }
}
