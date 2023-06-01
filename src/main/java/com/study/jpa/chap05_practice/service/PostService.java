package com.study.jpa.chap05_practice.service;

import com.study.jpa.chap05_practice.repository.HashTagRepository;
import com.study.jpa.chap05_practice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional // JPA 레파지토리를 사용하는 빈은 반드시 트랜잭션 아노테이션 필수
public class PostService {

            private final PostRepository postRepository;
            private final HashTagRepository hashTagRepository;
}
