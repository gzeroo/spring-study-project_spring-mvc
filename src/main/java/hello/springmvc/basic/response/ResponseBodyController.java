package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController // 바디에 넣는거 // Controller 는 ViewName 나옴
// @RestController : @ResponseBody + @Controller 합성어
/*
 @ResponseBody
 * HTTP 메시지 바디에 직접 내용을 입력
 * "HttpMessageConverter"가 동작
   (<-> (반대)viewResolver 가 동작, hello를 return 하면 "/response/hello.html"로 변환해줌)
    - 문자로 응답해야한다면? "StringHttpMessageConverter"
    - 객체로 응답해야한다면? "MappingJackson2HttpMessageConverter"
 */
public class ResponseBodyController {

    // 서버 -> 클라 응답할 때 클래스
    // resources >  basic > static 폴더 : 정적인 파일

    // 서버 - > 클라 전달 방식
    // 1. 정적 리소스: 문서(명세_이미지,동영상 등)_웹서버
    // 2. 동적 리소스_was_템플릿 엔진 사용(JSP 호출, 타임리프 등)
    // 3. API : 데이터 전달(JSON)

    @GetMapping("/response-body-string-v1") // HttpMessageConverter : 클라 <-> 서버 받는 언어(json,text 등)을 파악해서 보내줌
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() { // HttpEntity 을 상속받음 / 자식임/ HttpEntity 는 매개변수 body만 가능
        // ResponseEntity 는 bodt + HttpStatus(응답 코드) 가능)
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

//    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() { //객체 타입 ex. <HelloData> // 객체 주고 받을 때는 json 사용
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return new ResponseEntity<>(helloData, HttpStatus.OK);
        // "HTTP 메시지 컨버터"가 JSON 형식으로 변환되어 객체를 반환
    }

    @ResponseStatus(HttpStatus.OK) // 응답코드 보내기 // 밑에 응답 코드를 보내는게 없으니까
//    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() { // 클라가 서버에게 받는 언어는 Accept 로 설정(json 으로 받겠다 등)
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return helloData;
    }
}
