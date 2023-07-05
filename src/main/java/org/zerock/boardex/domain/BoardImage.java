package org.zerock.boardex.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
// Comparable () 객체 : 비교해야하는 객체에 적용함
// 주로 정렬을 위해 사용 (파일명 기준 오름차순 정렬)
public class BoardImage implements Comparable<BoardImage>{
    @Id // 주키 생성
    private String uuid; // 랜덤으로 이미지 파일의 코드가 만들어짐
    private String fileName;
    private int ord;
    // 게시글 하나에 여러개의 첨부파일 입력
    @ManyToOne
    private Board board;
    @Override

    public int compareTo(BoardImage other) {
        return this.ord- other.ord;
    }
    public void changeBoard(Board board) {
        this.board=board;
    }
}
