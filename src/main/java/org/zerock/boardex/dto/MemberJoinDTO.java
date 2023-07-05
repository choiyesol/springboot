package org.zerock.boardex.dto;

import lombok.Data;

//회원가입 변수 선언
@Data
public class MemberJoinDTO {
    private String mid;
    private String mpw;
    private String email;
    private boolean del;
    private boolean social;
}
