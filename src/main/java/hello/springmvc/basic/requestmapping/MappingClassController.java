package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

// @Controller : 밑에 참고  return "get users"; 일 경우 ViewName 으로 인식하기 때문에 접근 불가
@RestController // 컨트롤러 + 리스폰스 바디 합쳐진거 // 리턴값이 바디에 출력한다
@RequestMapping("/mapping/users") // /mapping/users URL로 들어온 모든 클라 입력을 MappingClassController 가 받는다.
//@RequestMapping : 모든 메소드 조건 받음(put, get, post 등)
public class MappingClassController {

    @GetMapping
    public String user() {
        return "get users";
    } // 출력 ("return "get users";")이 http 바디에 들어감

    @PostMapping // 입력
    // @ResponseBody : 바디에만 적용하고 싶으면 리스폰스바디 어노테이션 이용
    public String addUser() {
        return "post user";
    }

    @GetMapping("/{userId}") // { } : 경로 변수 / 클라가 입력한 값이 들어감
    public String findUser(@PathVariable String userId) {
        return "get userId=" + userId;
    }

    @PatchMapping("/{userId}") // 수정 (put / Patch)
    public String updateUser(@PathVariable String userId) {
        return "update userId=" + userId;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return "delete userId=" + userId;
    }

}
