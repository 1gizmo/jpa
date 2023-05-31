package com.study.jpa.chap02_querymethod.repository;

import com.study.jpa.chap02_querymethod.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    void insertData() {
        //given
        Student s1 = Student.builder()
                .name("춘식이")
                .city("서울시")
                .major("수학과")
                .build();
        Student s2 = Student.builder()
                .name("춘신춘왕")
                .city("부산시")
                .major("수학교육과")
                .build();
        Student s3 = Student.builder()
                .name("대길이")
                .city("한양도성")
                .major("체육교육과")
                .build();
        //when

        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);
    }

    @Test
    @DisplayName("이름이 춘식이인 학생의 정보를 조회해야 한다")
    void testFindByName() {
        //given
        String name = "춘식이";
        //when
        List<Student> students = studentRepository.findByName(name);
        //then
        assertEquals(1, students.size());

        System.out.println("students.get(0) = " + students.get(0));
    }

    @Test
    @DisplayName("testFindByCityAndMajor")
    void testFindByCityAndMajor() {
        //given
        String city = "부산시";
        String major = "수학교육과";
        //when
        List<Student> students = studentRepository.findByCityAndMajor(city, major);
        //then
        assertEquals(1, students.size());
        assertEquals("춘신춘왕", students.get(0).getName());

        System.out.println("students.get(0) = " + students.get(0));
    }

    @Test
    @DisplayName("testFindByMajorContaining")
    void testFindByMajorContaining() {
        //given
        String major = "수학";
        //when
        List<Student> students = studentRepository.findByMajorContaining(major);
        //then
        assertEquals(2, students.size());

        System.out.println("\n\n\n");
        students.forEach(System.out::println);
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("testNativeSQL")
    void testNativeSQL() {
        //given
        String name = "춘신춘왕";
        //when
        Student student = studentRepository.findNameWithSQL(name);
        //then
        assertEquals("부산시", student.getCity());

        System.out.println("\n\n\n");
        System.out.println("student = " + student);
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("testFindCityWithJPQL")
    void testFindCityWithJPQL() {
        //given
        String city = "한양도성";
        //when
        Student student = studentRepository.getByCityWithJPQL(city);
        //then
        assertEquals("대길이", student.getName());

        System.out.println("\n\n\n");
        System.out.println("student = " + student);
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("testSearchNameJPQL")
    void testSearchNameJPQL() {
        //given
        String name = "춘";
        //when
        List<Student> students = studentRepository.searchByNamesWithJPQL(name);
        //then
        assertEquals(2, students.size());

        System.out.println("\n\n\n");
        students.forEach(System.out::println);
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("JPQL로 삭제하기")
    void testDeleteWithJPQL() {
        //given
        String name = "대길이";
        //when
        studentRepository.deleteByNameWithJPQL(name);
        //then
        List<Student> students = studentRepository.findByName(name);

        assertEquals(0, students.size());
    }

}