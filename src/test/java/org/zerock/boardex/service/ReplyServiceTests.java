package org.zerock.boardex.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.boardex.dto.ReplyDTO;

// 댓글 등록 테스트
@SpringBootTest
@Log4j2
public class ReplyServiceTests {
    @Autowired
    private ReplyService replyService;
    @Test
    public void testRegister() {
        ReplyDTO replyDTO=ReplyDTO.builder()
                .replyText("어려워도 계속 하다보면 잘 하게 될거야 화이팅")
                .replyer("사오정")
                .bno(402L)
                .build();
        log.info(replyService.register(replyDTO));
    }

}
