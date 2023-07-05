package org.zerock.boardex.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
// User 객체는 스프링 프레임워크에서 지원하는 사용자 객체임
// username,password,authorities 등을 가짐
public class MemberSecurityDTO extends User implements OAuth2User { // 다중상속 : User:클래스, OAuth2User:인터페이스
    private String mid;
    private String mpw;
    private String email;
    private boolean del;
    private boolean social;
    //소셜 로그인 정보
    private Map<String, Object> props;


    // 생성자(멤버변수 초기화)
    public MemberSecurityDTO(String username, String password,
    String email, boolean del, boolean social,
    // Collection <> : 객체의 집합, 순서가 있는 배열공간(List), 집합체 저장공간(Set), 키와 값으로 데이터 구성하는 공간(Map)이 속함
    // GrantedAuthority : 아이디, 비밀번호 기반 인증에서 UserDetailsService 를 통해 조회됨
    Collection<? extends GrantedAuthority> authorities) {
        // super : 부모객체(User)의 메서드를 상속받음
        super(username, password, authorities);
        this.mid=username;
        this.mpw=password;
        this.email=email;
        this.del=del;
        this.social=social;
    }
    // 소셜 로그인 정보를 반환하는 메서드
    @Override
    public Map<String, Object> getAttributes() {
        return this.getProps();
    }
    // 아이디를 반환하는 메서드
    @Override
    public String getName() {
        return this.mid;
    }
}
