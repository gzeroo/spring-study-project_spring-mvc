package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

// 클라 - > 서버 입력한 http의 html body 읽기 @RequestBody 사용 유용

// 클라 - > 서버 전달 방식
// 1. 창 입력(url창) ex. ?username=김지영 // 자바스크립트로 쏘는거
// 2. 쿼리파라미터(패치 -> 자바스크립트에서 API 파싱한거 제이슨) 주소창 입력
// 3. html bodt 입력 - 폼(form)

// 클라 -> 서버 전달 방식 최종
// 1. 쿼리 파라미터 : @RequestParam / @ModelAttribute
// 2. 바디 :  @ResponseBody

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream(); // 클라 입력받은걸(리퀘스트) 인풋스트림으로 읽는다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // 인풋스트림 읽은걸, 한국어로 읽는다.

        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-string-v2") // Writer 서버 -> 클라 응답(클라 모니터 출력)
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        // 위에는 리퀘스트 이용,여기는 인풋스트림으로 바로 클라 입력 확인

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // 바로 스트림으로 읽기 가능
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    @PostMapping("/request-body-string-v3") // HttpEntity 이용
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {

        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-string-v4") // @RequestBody 어노테이션만 씀 // 헤더 읽고싶으면 @RequestHeader도 같이 써주면 됨
    public String requestBodyStringV4(@RequestBody String messageBody) { //통으로 객체로 줘서 읽음
        log.info("messageBody={}", messageBody);
        return "ok";
    }
}
