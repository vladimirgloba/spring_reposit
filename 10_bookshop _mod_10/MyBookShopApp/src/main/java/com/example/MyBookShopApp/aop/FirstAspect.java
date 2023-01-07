package com.example.MyBookShopApp.aop;

import com.example.MyBookShopApp.security.BookstoreUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

@Aspect
@Component
public class FirstAspect {

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }

    @Pointcut(value = "@annotation(com.example.MyBookShopApp.aop.ForAllMethodsAspect)")
    public void p8() {
    }

    @Pointcut(value = "@annotation(com.example.MyBookShopApp.aop.AboutRequestInformation)")
    public void p9() {

    }


    @AfterReturning(pointcut = "execution(* com.example.MyBookShopApp.security.BookstoreUserRegister.registerNewUser(..))", returning = "retVal")
    public void logAfterReturningGetEmployee(Object retVal) throws Throwable {
        System.out.println("ПОПЫТКА СОХРАНИТЬ ПОЛЬЗОВАТЕЛЯ ИЗ КЛАССА BookstoreUserRegister");
        Integer id = 0;
        try {
            id = ((BookstoreUser) retVal).getId();
            BookstoreUser user = (BookstoreUser) retVal;
            System.out.println("\t ИНФОРМАЦИЯ О ПОЛЬЗОВАТЕЛЕ:");
            System.out.println("\t user.getName() = " + user.getName());
            System.out.println("\t user.getPassword() = " + user.getPassword());
            System.out.println("\t user.getPhone() = " + user.getPhone());
            System.out.println("\t user.getEmail() = " + user.getEmail());
            System.out.println("\t user.getId() = " + user.getId());
        }
        catch (Exception e){
            System.out.println((char) 27 + "[31mWarning! " +"ПОЛЬЗОВАТЕЛЬ С ТАКИМ EMAIL УЖЕ ЕСТЬ В БД"+"\n"+
                    "СОХРАНЕНИЕ НЕ СОСТОЯЛОСЬ!!!"+(char) 27 + "[0m");
        }
        System.out.println("=================================================");
    }

    @Before("p9()")
    public void logBefore(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();
        System.out.println("ЗАПРОС К КОНТРОЛЛЕРУ ");
        forPrintBefore(joinPoint.getSignature().toString());
        HttpServletRequest request = (HttpServletRequest) signatureArgs[0];
        System.out.println("\t request.getPathInfo() = " + request.getPathInfo());
        System.out.println("\t request.getContextPath() = " + request.getContextPath());
        Enumeration<String> enumeration = request.getHeaderNames();

        while (enumeration.hasMoreElements()) {
            System.out.println("\t " + enumeration.nextElement() + " = " + request.getHeader(enumeration.nextElement()));
        }
        try {
            Collection<Part> parts = request.getParts();
            for (Part p : parts) {
                System.out.println("p.getName() = " + p.getName());
            }
        } catch (IOException e) {

        } catch (ServletException e) {

        }
        System.out.println("=================================================");
    }

    @Before("p8()")
    public void beforeMethod(JoinPoint joinPoint) {
        forPrintBefore(joinPoint.getSignature().toString());
        System.out.println("=================================================");
    }

    @AfterThrowing(pointcut = "p8()", throwing = "ex")
    public void throwAdvice(JoinPoint joinPoint, Exception ex) {
        forPrintAfterThrowing(joinPoint.getSignature().toString(), ex);
        System.out.println("=================================================");
    }

    @After("p8()")
    public void afterMethod(JoinPoint joinPoint) {
        forPrintAfter(joinPoint.getSignature().toString());
        System.out.println("=================================================");

    }


    private void forPrintAfterThrowing(String info, Exception ex) {
        List<String> stringList = new ArrayList<>(Arrays.asList(info.split(" ")));
        String typeOfValue = stringList.get(0);
        stringList = new ArrayList<>(Arrays.asList(stringList.get(1).split("\\.")));
        String nameMethod = stringList.get(stringList.size() - 1);
        String nameClass = stringList.get(stringList.size() - 2);
        System.out.println((char) 27 + "[31mWarning! " +
                "Выброшено исключение" + "\n" + "\t КЛАСС = " + nameClass + "\n" +
                "\t МЕТОД = " + nameMethod + "\n" +
                "\t ТИП ВОЗВРАЩАЕМОГО ЗНАЧЕНИЯ = " + typeOfValue + "\n" +
                "\t ТИП ОШИБКИ = " + ex.getMessage() + (char) 27 + "[0m");
    }

    private void forPrintAfter(String info) {
        List<String> stringList = new ArrayList<>(Arrays.asList(info.split(" ")));
        String typeOfValue = stringList.get(0);
        stringList = new ArrayList<>(Arrays.asList(stringList.get(1).split("\\.")));
        String nameMethod = stringList.get(stringList.size() - 1);
        String nameClass = stringList.get(stringList.size() - 2);
        System.out.println("Окончание работы" + "\n" + "\t КЛАСС = " + nameClass + "\n" +
                "\t МЕТОД = " + nameMethod + "\n" +
                "\t ТИП ВОЗВРАЩАЕМОГО ЗНАЧЕНИЯ = " + typeOfValue);
    }

    private void forPrintBefore(String info) {
        List<String> stringList = new ArrayList<>(Arrays.asList(info.split(" ")));
        String typeOfValue = stringList.get(0);
        stringList = new ArrayList<>(Arrays.asList(stringList.get(1).split("\\.")));
        String nameMethod = stringList.get(stringList.size() - 1);
        String nameClass = stringList.get(stringList.size() - 2);
        System.out.println("Начало работы" + "\n" + "\t КЛАСС = " + nameClass + "\n" +
                "\t МЕТОД = " + nameMethod + "\n" +
                "\t ТИП ВОЗВРАЩАЕМОГО ЗНАЧЕНИЯ = " + typeOfValue);

    }


}
