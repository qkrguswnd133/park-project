package com.spring.project.controller;

import com.spring.project.dto.UserInfoDto;
import com.spring.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자를 자동 생성해주는 lombok 어노테이션
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public String signup(UserInfoDto infoDto) { // 회원 추가
        userService.save(infoDto);
        return "redirect:/login";
    }

    /**
     * HttpServletRequest = 서블릿 컨테이너는 웹 서버로부터 들어온 클라이언트의 요청을 넘겨받아, HttpServletRequest 인터페이스의 객체를 생성한다.
     *                      이 객체에 클라이언트의 요청사항들을 담아 서블릿 서비스 메서드에 전당하므로, 클라이언트의 요청에 관한 많은 정보를 내장시키고 있다.
     * HttpServletResponse = 요청을 보낸 클라이언트에게 응답을 보내기 위해 WAS에서 생성되어 서블릿에게 전달됨
     *                      서블릿은 이 객체를 이용하여 content type, 응답코드, 응답 메시지등을 전송
     * SecurityContextLogoutHandler = 로그아웃에 대한 처리를 담당하는 함수로 사용자가 로그아웃 요청을 했을 경우에만 적용됨
     *                                세션 무효화, 인증 토큰 삭제, SecurityContext에서 해당 토큰 삭제, 쿠키 삭제 등을 처리.
     */
    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login"; // 로그아웃 처리 후 로그인 페이지로 리다이렉트
    }
}
