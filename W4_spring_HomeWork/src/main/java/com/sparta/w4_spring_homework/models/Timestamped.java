package com.sparta.w4_spring_homework.models;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@MappedSuperclass // 멤버 변수가 컬럼이 되도록 합니다.
@EntityListeners(AuditingEntityListener.class) // 변경되었을 때 자동으로 기록합니다.
@Getter // get 함수를 자동 생성합니다.
public abstract class Timestamped {

    @CreatedDate // 최초 생성 시점
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // 마지막 변경 시점
    @Column
    private LocalDateTime modifiedAt;
}