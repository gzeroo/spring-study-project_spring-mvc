package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Slf4j // @Slf4j > 로그 라이브러리

// 스프링에게 '빈(@RestController 밑에 클래스)' 선언하는 어노테이션 @RestController
@RestController // API 방식으로 클라 HTTP 받는 방식의 어노테이션 // @WepServlet이랑 유사
public class LogTestController {

    private final Logger log = LoggerFactory.getLogger(getClass()); // @Slf4j 랑 같음

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);

        log.trace(" trace my log="+ name);

        log.trace("trace log={}", name);

        log.debug("debug log={}", name);
        log.info(" info log={}", name);
        log.warn(" warn log={}", name);
        log.error("error log={}", name);

        return "ok";
    }
}
