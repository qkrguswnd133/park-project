package com.spring.project.repository;

import com.spring.project.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * interface = 다른 클래스를 작성할 때 기본이 되는 틀을 제공하면서, 다른 클래스 사이의 중간 매개 역할까지 담당하는 일종의 추상 클래스
 *              인터페이스는 오로지 추상 메소드와 상수만을 포함할 수 있다.
 * JpaRepository<> = jpa는 미리 검색 메소드를 정의 해 두는 것으로, 메소드를 호출하는 것만으로 스마트한 데이터 검색을 할 수 있게 되는 것
 *                  Entity에 있는 데이터를 조회하거나 저장과 변경 그리고 삭제를 할 때, Spring JPA에서 제공하는 Repository라는 인터페이스를
 *                  정의 해 해당 Entity의 데이터를 사용 할 수 있다.
 *                  즉, JpaRepository를 사용하면 SQL의 insert, select, delete, update와 같은 DML 데이터 조작 언어를 사용함
 *                  extends JpaRepository<엔터티 클래스이름, ID 필드 타입>
 */
public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByEmail(String email); // Optional<T> = NullPointerException을 방지할 수 있도록 하는 클래스
                                                    // 즉, Optional 클래스에서 제공하는 함수로 NPE를 방지할 수 있다.
                                                    // findBy**는 Entity의 속성을 검색해서, Optional를 리턴 타입으로 반환하는 메서드
}

/**
 * JpaRepository의 메소드 명명 규칙 (findBy**)
 *      1. findBy**
 *          - "findBy" 이후에 엔티티의 속성 이름을 붙인다.
 *              ex. findByEmail, findByName..
 *      2. Like / NotLike
 *          - Like를 붙이면, 인수에 지정된 텍스트를 포함하는 엔티티를 검색한다. 또한 NotLike를 쓰면 인수의 텍스트를 포함하지 않는 것을 검색한다.
 *              ex. "findByNameLike"의 경우라면, Name에서 인수의 텍스트를 검색한다.
 *      3. StartingWith / EndingWith
 *          - 텍스트 값에서 인수에 지정된 텍스트로 시작하거나 끝나는 것을 검색하기 위한 것이다.
 *              ex. "findByNameStartingWith("A")"라면, Name의 값이 "A"로 시작하는 항목을 검색한다.
 *      4. IsNull / IsNotNull
 *          - 값이 null 이거나, 혹은 null이 아닌 것을 검색한다. 인수는 필요 없다.
 *              ex. "findByNameNull()"이라면, Name의 값이 null인 것만 검색한다.
 *      5. True / False
 *          - 부울 값으로  true인 것, 혹은 false인 것을 검색한다. 인수는 필요 없다.
 *              ex. "findByCheckTrue()"라면 check라는 항목이 true인 것만을 검색한다.
 *      6. Before / After
 *          - 시간 값으로 사용한다. 인수에 지정한 값보다 이전의 것, 혹은 이후 것은 검색한다.
 *              ex. "findByCreateBefore(new Date())"라고 하면, create라는 항목의 값이 현재보다 이전의 것만을 찾는다.(create가 Date인 경우)
 *      7. LessThan / GreaterThan
 *          - 숫자 값으로 사용한다. 그 항목의 값이 인수보다 작거나 큰 것을 검색한다.
 *              ex. "findByAgeLessThan(20)"이라면, age의 값이 20보다 작은 것을 찾는다.
 *      8. Between
 *          - 두 값을 인수로 가지고 그 두값 사이의 것을 검색한다.
 *              ex. "findByAgeBetween(10,20)"라고 하면, age의 값이 10이상 20이하인 것을 검색한다. 수치뿐만 아니라 시간의 항목 등에도 사용 할 수 있다.
 */
