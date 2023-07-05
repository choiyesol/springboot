package org.zerock.boardex.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {
    private String uuid;
    private String fileName;
    private boolean img;
    public String getLink() {
        // 만약 파일이 이미지라면
        if(img) {
            // s_+uuid+_+파일명 > 이렇게 썸네일명 반환해주세요
            return "s_"+uuid+"_"+fileName;
        // 만약 이미지 파일이 아니라면    
        }else {
            // uuid+_+파일명 > 이렇게 반환해주세요
            return uuid+"_"+fileName;
        }
    }
}
