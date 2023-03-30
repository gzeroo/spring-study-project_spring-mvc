package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */

// 클라 -> 서버 json으로 보낼 때 설명 클래스!
@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody); // 클라에게 입력받은 내용의 직렬화된 스트링 타입
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class); // objectMapper json 파싱
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge()); // 직렬화된거 위에서 파싱 후 나온 값(클라의 입력 값)
        // HelloData.class 에서 age가 int임 결과값 username=윤지수, age=40

        //한글로 보내고 싶으면 인코딩 utf-8 써줘야함
        response.getWriter().write("ok"); // == @ResponseBody
    }

    // 내부적으로 HttpMessageConverter 가 돌고 있음! 그래서 @RequestBody 돌아감
    // HttpMessageConverter : Http 메시지를 읽어서 변환해주는 친구 / 클라가 보낸 메시지를 변환해준다.(서버입장)
    // StringHttpMessageConverter 적용
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    // 내부에서 HttpMessageConverter -> MappingJackson2HttpMessageConverter 가 돌아감
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData data) { // 객체(ex. HelloData data = 객체)로도 받을 수 있음! 반드시 매개변수에 @RequestBody 붙여줘야함
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }

    // ModelAttribute 은 안됨! / body 에는 들어가나 쿼리파라미터(키:밸류) 된 타입만 읽을 수 있음!
    /*
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@ModelAttribute HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }
     */

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {  // HttpEntity<제네릭>객체타입도 읽을 수 있다!
        HelloData data = httpEntity.getBody();
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return data; // 매개변수 리턴이 data가 나옴! // 클라가 입력한걸 서버가 그대로 반환해줌!
    }
}
