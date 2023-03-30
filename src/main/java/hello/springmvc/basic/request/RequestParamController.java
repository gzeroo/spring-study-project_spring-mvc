package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


// 클라 - > 서버 전달 방식
// 1. 창 입력(url창) ex. ?username=김지영 // 자바스크립트로 쏘는거
// 2. 쿼리파라미터(패치 -> 자바스크립트에서 API 파싱한거 제이슨) 주소창 입력
// 3. html 폼(form)

@Slf4j
@Controller // ViewName으로 불러옴
public class RequestParamController {

    @RequestMapping("/request-param-v1") // 잘못입력하면 서버 잘못 뜸 500 에러
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));


        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2") // 잘못입력하면 클라 에러 뜸 400 에러
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) { // RequestParam: 키 ("age") / 밸류 int memberAge 읽는다.

        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    @ResponseBody // 리턴값을 서버 -> 클라의 http의 바디(HTML) 에 담는다.
    @RequestMapping("/request-param-v3") // 보내는 값이 변수명이랑 같으면
    public String requestParamV3( // http://localhost:8080/request-param-v3?username=김지영&age=20
            @RequestParam String username, // 보내는 값이 변수명이랑 같으면 변수 생략 가능 / ex. ?username=김지영&age=20
            @RequestParam int age) {

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    // String, int 등과 같이 단순 타입일 때 @RequestParam 생략 가능
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) { // 단수 타입일때만!! @RequestParam 을 안써도 입력 가능
        log.info("username={}, age={}", username, age); // RequestParam은 객체 입력 가능
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired( // 필수 정보면 true - 작동됨! 필수인거 안넣으면 false 됨
            @RequestParam(required = true) String username, // true 필수
            @RequestParam(required = false) Integer age) { // false 필수 아님

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault( // 입력 안하면 기본으로 내가 임의 값(디폴트밸류) 넣어줄거야
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map") // @RequestParam : json 으로 보낼 때 읽기 가능 / 키 : 밸류 / 객체 담을 수 있음
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) { // <오브젝트> 넣어서 내가 입력한 밸류값을 키로 부를 수 있다.
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    @ResponseBody // 파라미터 log.info 이름 매칭 필수
    @RequestMapping("/model-attribute-v1") // 멤버 빌더 // @ModelAttribute 객체 읽을 때 사용
    public String modelAttributeV1(@ModelAttribute HelloData helloData) { // HelloData 클래스임. modelAttributeV1 메소드는 객체를 받겠다 선언중
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge()); // HelloData 객체 생성한걸로 유저네임이랑 나이 불러옴
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2") // @ModelAttribute는 HTTP Body 내용과 HTTP 파라미터의 값들을 Getter, Setter, 생성자를 통해 주입하기 위해 사용한다.
    public String modelAttributeV2(HelloData helloData) { // @ModelAttribute 어노테이션 안써도 클라->서버 입력 받기 가능
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
