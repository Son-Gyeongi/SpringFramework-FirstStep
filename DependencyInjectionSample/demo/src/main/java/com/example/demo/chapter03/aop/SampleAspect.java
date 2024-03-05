package com.example.demo.chapter03.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect // 어드바이스를 기술하는 클래스
@Component // 인스턴스를 생성하기 위해
public class SampleAspect {
    /**
     * 메서드 실행 전에 호출하는 Before Advice
     * @param joinPoint
     */
    @Before("execution(* com.example.demo.chapter03.used.*Greet.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        // 시작 부분 표시
        System.out.println("===== Before Advice =====");
        // 날짜 출력
        System.out.println(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        // 메서드 이름 출력
        System.out.println(String.format("메서드:%s", joinPoint.getSignature().getName()));
    }
}
