package com.example.login_test.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

// ** Lombok = 생성된 모든 필드의 getter 메서드를 생성하여 사용할 수 있게함.
@Getter

// ** @NoArgsConstructor : Lombok 라이브러리에서 사용됨.
/*  'public'보다는 'protected' 접근 수준으로 설정해야함.
이유는, 현재 클래스를 외부에서 임의로 인스턴스화하는 것을 방지해야하기 때문.
Entity 클래스의 인스턴스는 JPA나 해당 클래스의 정적 팩토리 메서드 등을 통해서만
생성되어야 한다는 의도를 명확하게 나타낸 것이라고 봐야함.
*/
@NoArgsConstructor(access = AccessLevel.PROTECTED)

//** 이 클래스가 엔터티 클래스임을 선언.
/* 제약사항
 * @Entity가 붙은 클래스는 반드시 기본 생성자를 가지고 있어야 한다.
 * 'public' 또는 'protected' 접근 수준을 가져야 한다.
 * Entity 클래스의 모든 필드는 JPA가 알아서 매핑하기 위해 직렬화가 가능(Serializable)해야 한다.
 * @Entity가 붙은 클래스는 상속을 받거나 확장될 수 있고, 다른 Entity 클래스를 상속받을 수 있다.
 * Entity 클래스의 필드는 객체-관계 매핑(ORM)을 위해 다른 어노테이션들로 추가적으로 설정할 수 있다.
 * (예: @Column, @Id, @OneToMany 등)
 * */
@Entity

// ** JPA(Java Persistence API)에서 사용됨.
// ** Entity 클래스가 매핑될 데이터베이스의 테이블을 지정
// ** name="user_tb" = 테이블의 이름 설정
@Table(name="user_tb")
public class User{

    // ** 해당 필드를 PK로 지정
    @Id

    /* @Id 어노테이션과 함께 사용
     * GenerationType.IDENTITY 전략은 데이터베이스에 레코드를 삽입할 때,
     * 데이터베이스가 자동으로 아이디를 생성하도록 함.
     * */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    /* @Column 어노테이션은 JPA(Java Persistence API)에서 사용됨
     * Entity 클래스의 필드가 매핑될 데이터베이스 컬럼의 세부 사항을 지정한다.
     * length = 100은 데이터베이스에서 이 컬럼의 최대 길이를 100으로 설정.
     * nullable = false: 이 컬럼은 NULL 값을 허용하지 않음.
     * unique = true: 이 컬럼의 값은 유일해야 함. (중복된 값이 들어갈 수 없음.)
     **/
    @Column(length = 100, nullable = false, unique = true)
    private String email;


    @Column(length = 256, nullable = false)
    private String password;

    @Column(length = 45, nullable = false)
    private String username;

    @Column(length = 11, nullable = false)
    private String phoneNumber;

    @Column(length = 30)
    @Convert(converter = StringArrayConverter.class)
    private List<String> roles = new ArrayList<>();
    // ** 사용자 권한을 저장한다.
    // ** ROLE_ADMIN
    // ** ROLE_MANAGER
    // ** ROLE_USER 등등...


    /* @Builder 어노테이션은 Lombok 라이브러리에서 제공.
     * 빌더 패턴을 쉽게 구현할 수 있게 도와준다.
     * 주로 생성자의 인자가 많거나, 인자를 선택적으로 지정해야하는 경우에 사용.
     * */
    @Builder
    public User(int id, String email, String password, String username, String phoneNumber, List<String> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }

    public void output(){
        System.out.println(id);
        System.out.println(email);
        System.out.println(password);
        System.out.println(username);
        System.out.println(roles);
    }
}
