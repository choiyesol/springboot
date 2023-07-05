package org.zerock.boardex.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.boardex.domain.Board;
import org.zerock.boardex.dto.BoardListAllDTO;
import org.zerock.boardex.dto.BoardListReplyCountDTO;

// 인터페이스에서는 메서드를 정의만 할 수 있음
public interface BoardSearch {
    Page<Board> search1(Pageable pageable);  //search1:검색해주세요

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    // 댓글
    Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable);

    // 게시판의 게시글에 포함된 데이터 모두 검색
    Page<BoardListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable);
}
