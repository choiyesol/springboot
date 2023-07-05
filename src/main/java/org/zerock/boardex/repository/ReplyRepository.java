package org.zerock.boardex.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.boardex.domain.Reply;


public interface ReplyRepository extends JpaRepository<Reply,Long> {  //교재 p99 참고(JPA)
    // 특정 게시글의 댓글도 페이징 처리
    // Reply를 r이라고 하겠다 > r 테이블의 정보를 가져와서 게시판 번호에 따른 데이터를 조회해주세요
    @Query("select r from Reply r where r.board.bno=:bno")
    // 객체<객체타입> : 제네릭 문법(Generic)
    // Reply 클래스를 통채로 품은 Page 객체를 가지고 글 번호 기준으로 페이징을 해주세요
    Page<Reply> listOfBoard(@Param("bno") Long bno, Pageable pageable);

    //댓글이나 첨부파일 삭제
    void deleteByBoard_Bno(Long bno);

}
