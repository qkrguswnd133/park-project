package com.spring.project.service;

import com.spring.project.dto.UserInfoDto;
import com.spring.project.entity.UserInfo;
import com.spring.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자를 자동 생성해주는 lombok 어노테이션
@Service
public class UserService implements UserDetailsService { // UserDetailsService 인터페이스는 DB에서 유저 정보를 가져오는 역할을 한다.
    private final UserRepository userRepository;


    /**
     * Spring Security 필수 메소드 구현
     *
     * @param email 이메일
     * @return UserDetails
     * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
     */

    // UserDetailsService를 상속 받으면 loadUserByUsername(String) 메소드를 오버라이딩 해야함.
    // 이 메소드에서 DB로부터 회원정보를 가져와 있는 회원인지 아닌지 체크여부를 하기 때문에 필수로 구현해야 함.
    @Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅 됨)
    public UserInfo loadUserByUsername(String email) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현 (로그인)
        return userRepository.findByEmail(email) // DB로부터 회원 email을 가져온다.
                .orElseThrow(() -> new UsernameNotFoundException((email))); // email이 존재하지 않으면 UsernameNotFoundException 일으킴
    } // .orElseThrow() = 이 함수를 쓰지 않으면 if ~ else 조건문을 사용하여 회원 email 정보를 체크 해야함 ex> "".equal(eamil)

    /**
     * 회원정보 저장
     * @param infoDto 회원정보가 들어있는 DTO
     * @return 저장되는 회원의 PK
     */
    public Long save(UserInfoDto infoDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword())); // 입력받은 패스워드를 BCrypt로 암호화

        return userRepository.save(UserInfo.builder()
                .email(infoDto.getEmail())
                .auth(infoDto.getAuth())
                .name(infoDto.getName())
                .password(infoDto.getPassword()).build()).getUid(); // .getUid() = 저장되는 회원의 Private Key(code)를 리턴
    }
}
// Builder 패턴 = 빌더 패턴은 생성 패턴 중 하나이다. 또, 생성 패턴은 인스턴스를 만드는 절차를 추상화하는 패턴이다.
// 1. 인스턴스를 생성할 때 인자를 선택적으로 가독성 좋게 넘길 수 있음.
// 2. 인자의 순서가 상관이 없다.
// 3. 불필요한 생성자를 만들지 않는다.