package com.spring.project.config;

import com.spring.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자를 자동 생성해주는 lombok 어노테이션
@EnableWebSecurity // Spring Security 활성화
@Configuration // 가시적으로 설정파일이야 ~, Bean 등록할거야 ~
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurityConfigurerAdapter는
                                        // Spring Security의 설정 파일로서의 역할을 하기 위해 상속해야하는 클래스

    private final UserService userService; // 유저 정보를 가져올 클래스

    @Override
    public void configure(WebSecurity web) {
        // WebSecurityConfigurerAdapter를 상속받으면 오버라이드할 수 있고, 인증을 무시할 경로들을 설정해놓을 수 있다.
        // static 하위 폴더(css, js, images, fonts, vendor, assets)는 무조건 접근이 가능해야 하기 때문에 인증을 무시
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/vendor/**", "/assets/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // WebSecurityConfigurerAdapter를 상속받으면 오버라이드할 수 있다.
        // http 관련 인증 설정이 가능
        http
                .authorizeRequests() // 접근에 대한 인증 설정을 의미
                .antMatchers("/login", "/signup", "/user").permitAll() // 누구나 접근 허용
                .antMatchers("/", "/board").hasRole("USER") // USER, ADMIN만 접근 가능
                .antMatchers("/admin").hasRole("ADMIN") // ADMIN만 접근 가능
                .anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
                .and() // 특정 설정에 대한 구성을 완료한 후 작성
                .formLogin() // 로그인에 관한 설정을 의미
                .loginPage("/login") // 로그인 페이지 링크 설정
                .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 주소
                .and()
                .logout() // 로그아웃에 관한 설정을 의미
                .logoutSuccessUrl("/login") // 로그아웃 성공시 리다이렉트 주소
                .invalidateHttpSession(true); // 로그아웃 이후 세션 전체 삭제 여부
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception { // 로그인할 때 필요한 정보를 가져오는 곳
        auth.userDetailsService(userService) // 유저 정보를 가져오는 서비스를 userService로 지정
                // 해당 서비스(userService)에서는 UserDetailService를 implements해서
                // loadUserByUsername() 구현해야함
                .passwordEncoder(new BCryptPasswordEncoder()); // .passwordEncoder를 이용하면 평문의 비밀번호를 암호화하여 DB에 주입(별도로 DB에서 암호화를 할 필요 X)
                // BCrypt라는 해시 함수를 이용하여 패스워드를 암호화
    }
}
