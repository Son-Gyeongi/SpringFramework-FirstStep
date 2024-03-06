package com.example.demo.chapter03.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
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
     *
     * @param joinPoint
     */
    /*@Before("execution(* com.example.demo.chapter03.used.*Greet.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        // 시작 부분 표시
        System.out.println("===== Before Advice =====");
        // 날짜 출력
        System.out.println(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        // 메서드 이름 출력
        System.out.println(String.format("메서드:%s", joinPoint.getSignature().getName()));
    }*/

    /**
     * 메서드 실행 후 호출하는 After Advice
     *
     * @param joinPoint
     */
    /*@After("execution(* com.example.demo.chapter03.used.*Greet.*(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        // 시작 부분 표시
        System.out.println("===== After Advice =====");
        // 날짜 출력
        System.out.println(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        // 메서드 이름 출력
        System.out.println(String.format("메서드:%s", joinPoint.getSignature().getName()));
    }*/

    /**
     * 메서드 전 후에 호출할 수 있는 Aruond Advice
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.example.demo.chapter03.used.*Greet.*(..))")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        // 시작 부분 표시
        System.out.println("===== Around Advice =====");
        System.out.println("▼▼▼ 처리전 ▼▼▼");
        // 지정한 클래스의 메서드 실행
        Object result = joinPoint.proceed();
        System.out.println("▲▲▲ 처리후 ▲▲▲");
        // 반환값을 돌려줄 필요가 있는 경우에는 Object 타입의 반환값을 돌려줍니다.
        return result;
    }
}
