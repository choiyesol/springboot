package org.zerock.boardex.service;

import org.zerock.boardex.domain.Board;
import org.zerock.boardex.dto.*;

import java.util.List;
import java.util.stream.Collectors;

//인터페이스라 메서드 선언만 함
public interface BoardService {
    //데이터 등록 작업
    Long register(BoardDTO boardDTO);
    //데이터 조회 작업
    BoardDTO readOne(Long bno);
    //데이터 수정 작업
    void modify(BoardDTO boardDTO);
    //데이터 삭제 작업
    void remove(Long bno);
    //목록, 검색 기능
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
    // 댓글의 숫자처리
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);
    // 게시글의 이미지와 댓글의 숫자 처리
    PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);

    // 원래 인터페이스에서는 메서드를 정의만 함 > 내용을 구현하는 것은 자식객체(Impl)에서 했었음
    // 인터페이스에서 선언한 메서드는 자식객체(Impl)에서 반드시 구현해야 함
    // 디폴트 메서드(default method)를 구현하여 그대로 자식객체로 상속 가능
    default Board dtoToEntity(BoardDTO boardDTO) {
        Board board=Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();
        // 첨부파일 이미지의 파일명이 null이 아니라면 = 첨부파일이 있다면
        if(boardDTO.getFileNames() != null){
            // 첨부파일의 개수만큼 반복해주세요
            boardDTO.getFileNames().forEach(fileName->{
                // 첨부파일명을 _로 나누어서 arr 배열에 저장
                // ex1) arr[0]=965bcf8e-bf37-4350-9858-596ed557e4c8_heart
                //      arr[1]=cat.jpg
                String[] arr=fileName.split("_",2);
                // 게시판에 이미지 배열을 추가해주세요
                board.addImage(arr[0],arr[1]);
            });
        }
        return board;
    }
    // Board 엔티티 객체를 BoardDTO 타입으로 변환하는 default 메서드
    default BoardDTO entityToDTO(Board board) {
        // boardDTO 객체의 내용을 데이터베이스에서 가져와서 build 시킴
        BoardDTO boardDTO=BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();
        // 첨부파일의 Uuid+"_"+파일명 합쳐진 문자열을 fileNames에 배열로 저장
        List<String> filenames=board.getImageSet().stream().sorted().map(boardImage ->
                boardImage.getUuid()+"_"+boardImage.getFileName()).collect(Collectors.toList());
        // Uuid+"_"+파일명으로 만들어진 fileNames의 값을 boardDTO 객체의 fileName에 저장
        boardDTO.setFileNames(filenames);
        return boardDTO;
    }
}
