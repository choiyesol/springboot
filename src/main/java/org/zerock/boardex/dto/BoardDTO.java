package org.zerock.boardex.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

// Board와 매핑
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long bno;
    @NotEmpty /* 제목은 비어있으면 안됨 */
    @Size(min = 3, max = 100) /* 최소 3글자 ~ 최대 100글자까지 제목 입력 가능 */
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String writer;
    // 자동으로 데이터 생성되기 때문에 @NotEmpty 입력 X
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    //첨부파일의 이름들
    private List<String> fileNames;
}
