package org.zerock.boardex.domain;

import jakarta.persistence.*;
import lombok.*;

// ReplyDTO와 매핑
@Entity
// 쿼리의 조건으로 자주 사용되는 컬럼에는 인덱스 설정함
@Table(name = "Reply", indexes = {@Index(name = "idx_reply_bno", columnList = "board_bno")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
// BaseEntity : 작성일, 수정일 상속 받음
public class Reply extends BaseEntity {
    // Primary key 설정, 필수요소, 자동으로 번호 생성(auto_increment)
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long rno;    //댓글 번호_주키(primary key)

    // 다대일 관계 > 관계 설정하는 어노테이션 (게시글 1 여러개의 댓글  : 다대일 관계)
    // @ManyToOne 사용 시, 반드시 FetchType.LAZY 요로콩 설정해야 함
    @ManyToOne(fetch=FetchType.LAZY)
    private Board board;        //게시판 객체 _외래키(foreign key)
    private String replyText;   //댓글
    private String replyer;     //댓글 작성자

    // 댓글내용만 수정가능
    // 외부에서 text내용을 전달받아서 replyText에 저장
    public void changeText(String text) {
        this.replyText=text;
    }
}
