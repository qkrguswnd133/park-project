package com.spring.project.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * User Entity = User 엔티티는 UserDetails를 상속받아서 구현, UserDetails에서 필수로 구현해야 하는 메소드는 아래와 같다.
 *
 * getAuthorities() = 사용자의 권한을 컬렉션 형태로 반환 (클래스 자료형은 GrantedAuthority를 구현해야함)
 *                  컬렉션(Collection) = 데이터의 집합, 그룹을 의미
 *                  GrantedAuthority = 현재 사용자가 가지고 있는 권한을 의미, 특정 자원에 대한 권한이 있는지를 검사하여 접근 허용 여부를 결정
 * getUsername() = 사용자의 id를 반환 (id는 unique한 값이여야함 )
 *                  UNIQUE = 제약조건의 하나로 해당 컬럼에 동일한 값이 들어가지 않도록 하는 제약조건
 * getPassword() = 사용자의 password를 반환
 * isAccountNonExpired() = 계정 만료 여부 반환 (true = 만료되지 않음을 의미)
 * isAccountNonLocked() = 계정 잠금 여부 반환 (true = 잠금되지 않음을 의미)
 * isCredentialsNonExpired() = 패스워드 만료 여부 반환 (true = 만료되지 않음을 의미)
 * isEabled() = 계정 사용 가능 여부 반환 (true = 사용 가능을 의미)
 */

// @AllArgsConstructor = 모든 필드 값을 파라미터로 받는 생성자를 만들어 줌
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무런 매개변수가 없는 생성자
                                                    // 매개변수가 없는 생성자의 접근 레벨이 public 또는 protected로 해야 한다.
                                                    // 인스턴스 변수는 직접 접근이 아닌 내부 메소드로 접근해야 한다.
                                                    // Entity 클래스 인스턴스 변수는 직접 접근이 아닌 내부 메소드로 접근해야 한다. (Getter 사용)
@Entity // 데이터베이스의 테이블과 일대일로 매칭되는 객체 단위이며 Entity 객체의 인스턴스 하나가 테이블에서 하나의 레코드 값을 의미
@Getter // 접근자를 자동으로 생성
public class UserInfo implements UserDetails {

    @Id // 데이터베이스의 Primary key를 지정하는 어노테이션
    // Primary Key = 유일한 키값을 기준으로 질의한 데이터를 추출해 결과셋으로 반환
    @Column(name = "uid") // 데이터베이스의 컬럼을 지정하는 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임 즉, id 값을 null로 하면 DB가 알아서 AUTO_INCREMENT 해준다.
    private Long uid;

    @Column(name = "email", unique = true) // UNIQUE = 제약조건의 하나로 해당 컬럼에 동일한 값이 들어가지 않도록 하는 제약조건 즉, email 중복을 방지
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "auth")
    private String auth;

    // Builder 패턴 = 빌더 패턴은 생성 패턴 중 하나이다. 또, 생성 패턴은 인스턴스를 만드는 절차를 추상화하는 패턴이다.
    // 1. 인스턴스를 생성할 때 인자를 선택적으로 가독성 좋게 넘길 수 있음.
    // 2. 인자의 순서가 상관이 없다.
    // 3. 불필요한 생성자를 만들지 않는다.
    @Builder
    public UserInfo(String email, String password, String name, String auth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.auth = auth;
    }

    // 사용자의 권한을 컬렉션 형태로 변환
    // 단, 클래스 자료형은 GrantedAuthority를 구현해야함
    /**
     * getAuthorities() = 사용자의 권한을 컬렉션 형태로 반환 (클래스 자료형은 GrantedAuthority를 구현해야함)
     *                  컬렉션(Collection) = 데이터의 집합, 그룹을 의미
     *                  GrantedAuthority = 현재 사용자가 가지고 있는 권한을 의미, 특정 자원에 대한 권한이 있는지를 검사하여 접근 허용 여부를 결정
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // GrantedAuthority 클래스를 상속받아 Collection 선언
        Set<GrantedAuthority> roles = new HashSet<>();
        // Set<GrantedAuthority> = 같은 요소의 중복 저장을 허용하지 않는다. 즉, 권한을 중복시키지 않는다.
        // HashSet<> = 해시 알고리즘을 사용하여 검색 속도가 매우 빠름, HashSet 클래스는 Set 인터페이스를 구현하므로, 요소를 순서에 상관없이 저장하고 중복된 값은 저장하지 않음.
        for (String role : auth.split(",")){ // 향상 for문 으로써, auth(ROLE_ADMIN,ROLE_USER)에서 차례대로 객체를 꺼내서 role에 넣겠다.
            roles.add(new SimpleGrantedAuthority(role)); // role(ROLE_ADMIN,ROLE_USER)을 roles에 추가
            // SimpleGrantedAuthority = GrantedAuthority를 상속받은 클래스
        }
        return roles;
    }

    // 사용자의 id를 반환 (unique한 값)
//    getUsername() = 사용자의 id를 반환 (id는 unique한 값이여야함)
//    UNIQUE = 제약조건의 하나로 해당 컬럼에 동일한 값이 들어가지 않도록 하는 제약조건
    @Override
    public String getUsername() {
        return email;
    }

    // getPassword() = 사용자의 password를 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
}
