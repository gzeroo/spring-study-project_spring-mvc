package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController // Controller Web을 봄
// key=value 형식으로 전달된 파라미터를 매핑하는 어노테이션입니다.
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/hello-basic") // RequestMapping : 클라 입력 정보 > ex. ://localhost:8080/
    public String helloBasic() {
        log.info("helloBasic");
        return "김지영";
    }

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET) // method = RequestMethod.GET : post, get 등 요청 제한 가능
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }

    /**
     * 편리한 축약 애노테이션 (코드보기)
     * @GetMapping
     * @PostMapping
     * @PutMapping
     * @DeleteMapping
     * @PatchMapping(경로 변수) // 파라미터를 URL에 포함시켜 전달하는 방법입니다.
     * 클라이언트가 기존 리소스를 완전히 교체해야 하는 경우 PUT을 사용할 수 있습니다.
     * 부분 업데이트를 수행할 때 HTTP PATCH를 사용할 수 있습니다.
     * 멱등성: 연산을 여러 번 적용하더라도 결과가 달라지지 않는 성질을 뜻합니다
     * PUT : 리소스의 전체를 변경할 때 사용 (partial modifications), 변경하는 데이터만 요청하면 된다. (멱등성o)
     * PATCH(경로 변수) : 리소스의 부분을 변경할 때 사용, 변경하지 않는 데이터도 함께 전송해야 한다. (멱등성x)
     * // PATCH(경로 변수) : 경로변수를 읽어들일 때 사용
     */

    // @PostMapping(value = "/mapping-get-v2") //  2개 쓰고 싶을때 2개 선언하면 됨 위아래
    @GetMapping(value = "/mapping-get-v2") // == @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }

    /**
     * PathVariable 사용
     * 변수명이 같으면 생략 가능
     *
     * @PathVariable("userId") String userId -> @PathVariable userId
     * /mapping/userA
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        log.info("mappingPath userId={}", data);
        return "ok";
    }

    /**
     * PathVariable 사용 다중
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /**
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug") // value = "" 와 params = "" 의 값이 클라 입력 정보와 매칭(==)되어야함.
    public String mappingParam() { // 뒤에 params 파라미터도 클라 입력 같아야 ok 출력
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug") // 클라에서 오는 Http의 헤더 필터 가능
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * // consumes 소비하는 입장 // 서버 입장임
     * // 클라 -> 서버
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    // Content-Type 클라가 준 HTTP를 서버가 읽을 때 'consumes = MediaType.APPLICATION_JSON_VALUE'
    public String mappingConsumes() { // consumes 소비하는 입장 // 서버 입장임
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    // MediaType. ~ 을 이용해서 프로듀스 타입 줄 수 있음.
    // 클라가 서버에게 어떤 타입으로 읽는다고 지정 가능.(HTML, XML, JSON 등)
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
