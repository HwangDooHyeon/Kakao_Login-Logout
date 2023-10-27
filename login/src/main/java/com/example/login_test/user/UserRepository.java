package com.example.login_test.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// ** JpaRepository<User, Integer> : 기본적인 CRUD 연산 메서드(save, findById, findAll, delete 등)를 사용할 수 있음.
public interface UserRepository extends JpaRepository<User, Integer> {

    // ** JPA는 메서드의 이름을 분석하는 방식으로 쿼리문을 작성할 수 있는 능력자
    Optional<User> findByEmail(String email);
}













