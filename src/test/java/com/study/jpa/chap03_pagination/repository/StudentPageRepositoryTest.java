package com.study.jpa.chap03_pagination.repository;

import com.study.jpa.chap02_querymethod.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// 만약에 서비스클래스를 사용한다면 해당 클래스에 붙일 것!
@Transactional // JPA는 I, U, D시에 반드시 트랜잭션 처리가 필수
@Rollback(false)
class StudentPageRepositoryTest {

    @Autowired
    StudentPageRepository studentPageRepository;

    @BeforeEach
    void bulkInsert() {
        // 학생을 147명 저장
        for (int i = 1; i <= 147; i++) {
            Student s = Student.builder()
                    .name("김파파" + i)
                    .city("도시시" + i)
                    .major("전공공" + i)
                    .build();
            studentPageRepository.save(s);
        }
    }

    @Test
    @DisplayName("기본 페이징 테스트")
    void testBasicPagination() {
        //given
        int pageNo = 1;
        int amount = 10;

        // 페이지 정보 생성
        // 페이지번호가 zero-based
        Pageable pageInfo
                = PageRequest.of(pageNo - 1,
                amount,
                Sort.by((
                        Sort.Order.desc("name")),
                        Sort.Order.asc("city")
                // 정렬 기준 필드명
                ));

        //when
        Page<Student> students
                = studentPageRepository.findAll(pageInfo);

        // 페이징 완료된 데이터셋
        List<Student> studentList = students.getContent();

        // 총 페이지 수
        int totalPages = students.getTotalPages();

        // 총 학생 수
        long totalElements = students.getTotalElements();

        Pageable prev = students.getPageable().previousOrFirst();
        Pageable next = students.getPageable().next();

        //then
        System.out.println("\n\n\n");
        System.out.println("totalPages = " + totalPages);
        System.out.println("totalElements = " + totalElements);
        System.out.println("prev = " + prev);
        System.out.println("next = " + next);
        System.out.println("\n\n\n");
        studentList.forEach(System.out::println);
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("이름검색 + 페이징")
    void testSearchAndPagination() {
        //given
        int pageNo = 1 ;
        int size = 10 ;
        Pageable pageInfo = PageRequest.of(pageNo -1 , size);
        //when
        Page<Student> students = studentPageRepository.findByNameContaining("3", pageInfo);

        //then
        System.out.println("\n\n\n");
        students.getContent().forEach(System.out::println);
        System.out.println("\n\n\n");
    }

}