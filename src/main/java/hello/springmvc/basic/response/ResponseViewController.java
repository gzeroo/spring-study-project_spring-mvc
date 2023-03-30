package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

// response > hello.html 가져옴
@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1") //@ResponseBody 가 없으면 모델뷰 반환
    public ModelAndView responseViewV1() { // 모델뷰 반환
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!"); // 키 : 밸류 // attributeName 이름이 html에 있는 {키 값과 동일해야함}
        return mav;
    }

    //주로 v2 사용!
    @RequestMapping("/response-view-v2") // @RestController : api 데이터 보낼때 매핑용
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");
        return "response/hello";
    }

    // 단점: 경로를 바로 입력하는거라 다른 사람이 보기에 어떤 경로를 이용했는지 확인이 어려워 가독성이 떨어짐
    @RequestMapping("/response/hello") // html 경로임 html 바로 보여주기!
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
    }
}
