package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

// http 리퀘스트(클라) 헤더
@Slf4j
@RestController
public class RequestHeaderController {

    @RequestMapping("/headers") // RequestMapping: 기본적으로 get, post 다 받을 수 있음 // 따로 조건 지정해야함.
    public String headers(HttpServletRequest request,
                          HttpServletRequest response,
                          HttpMethod httpMethod,
                          Locale locale,
                          // 실제로 사용은 아래와 같이 어노테이션 이용함
                          @RequestHeader MultiValueMap<String, String> headerMap,
                          @RequestHeader("host") String host, // 헤더 중에서 host 값을 갖고 있는 놈의 밸류(값)를 나타내 / 키 값에 맞는 밸류
                          @CookieValue(value = "myCookie", required = false) String cookie
                          ) {

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);
        return "ok";
    }
}
