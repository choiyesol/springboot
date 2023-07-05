package org.zerock.boardex.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;

// REST api 방식 : 눈에 보이지 않게 에러를 처리하므로 에러 찾기가 힘듦
// 그래서 에러를 눈에 보이도록 하기 위해 작성하는 것.
@RestControllerAdvice
@Log4j2
public class CustomRestAdvice {
    // @ExceptionHandler : Controller 계층에서 발생하는 에러를 메서드로 처리
    //  ㄴ Service, Repository 에서 발생하는 에러는 제외
    @ExceptionHandler(BindException.class)
    // @ResponseStatus : 상태 코드를 변경함
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>>
    // 에러 처리 시, 매개변수 이름은 e 로 지정
    handleBindException(BindException e) {
        log.error(e);
        // 키와 값을 가진 Map 배열로 errorMap 이라는 객체 생성
        Map<String, String> errorMap=new HashMap<>();
        if (e.hasErrors()) {
            // 에러 객체를 bindingResult에 저장
            BindingResult bindingResult = e.getBindingResult();
            // bindingResult.getFieldErrors().forEach : 발생한 에러 개수만큼 반복
            bindingResult.getFieldErrors().forEach(fieldError -> {
                // getField() : 오류 필드명
                // getCode() : 오류 코드
                errorMap.put(fieldError.getField(), fieldError.getCode());
            });
        }
            return ResponseEntity.badRequest().body(errorMap);
    }
    // 데이터에 문제가 있다고 사용자에게 예외 메세지를 전송하기
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public  ResponseEntity<Map<String, String>> handleFKException(Exception e) {
        log.error(e);
        Map<String, String> errorMap=new HashMap<>();
        // System.currentTimeMillis() : 1970.01.01 부터 경과한 시간을 long 값으로 리턴 (밀리초 단위:1/1000)
        errorMap.put("time : ",""+System.currentTimeMillis());
        errorMap.put("msg : ","constraint fails");
        return ResponseEntity.badRequest().body(errorMap);
    }

}
