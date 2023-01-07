package com.example.MyBookShopApp.selenium;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
class MainPageSeleniumTests {

    private static ChromeDriver driver;
public static MainPage mainPage;
public static MyPage myPage;
private static ProfilePage profilePage;
private static RegistrationPage registrationPage;
private static SigninPage signinPage;
    @BeforeAll
    static void setup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("вход на главную страницу")
    public void test_1() throws InterruptedException {//_MainPageAccess
        mainPage = new MainPage(driver);
        mainPage
                .callPage()
                .pause();

        assertTrue(driver.getPageSource().contains("BOOKSHOP"));
    }

    @Test
    @DisplayName("поиск по названию книги")
    public void test_2() throws InterruptedException {//_MainPageSearchByQuery
        mainPage = new MainPage(driver);
        mainPage
                .callPage()
                .pause()
                .setUpSearchToken("Sudden")
                .pause()
                .submit()
                .pause();

        assertTrue(driver.getPageSource().contains("Sudden Manhattan"));
    }

    @Test
    @DisplayName("проверка переадресации при входе без пароля")
    public void test_3() throws InterruptedException {//_RedirectOnSignin
        mainPage = new MainPage(driver);
        mainPage.callPage()
                .pause()
                .logIn()
                .pause();

        String actualUrl = "http://localhost:8085/signin";
        String expectedUrl = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl, actualUrl);
    }

    @Test
    @DisplayName("вход незарегистрированного пользователя")
    public void test_4() throws InterruptedException {//_FakerUser
         signinPage = new SigninPage(driver);
        signinPage.callPage()
                .pause()
                .inputLogin("+7(999)997799")
                .pause()
                .clickLogin()
                .pause()
                .inputCode("111111")
                .pause()
                .clickComIn()
                .pause();
        Assert.assertEquals(signinPage.getErrorMessage(), ("Пользователь не найден или неверный логин/пароль!"));
    }

    @Test
    @DisplayName("регистрация пользователя")
    public void test_5() throws InterruptedException {
         signinPage = new SigninPage(driver);
        signinPage.callPage()
                .pause().regBtnClick().pause();
         registrationPage = new RegistrationPage(driver);
        registrationPage.callPage()
                .longPause()
                .inputName("Новый Пользователь")
                .shortPause()
                .inputPhone("+7(999)999999")
                .shortPause()
                .inputMail("mailmail@yandex.ru")
                .shortPause()
                .submitPhoneClick()
                .shortPause()
                .inputCodePhone("111111")
                .shortPause()
                .submitMailClick()
                .shortPause()
                .inputCodeMail("111111")
                .shortPause()
                .inpuPassword("111111")
                .shortPause()
                .forActivationClick()
                .shortPause()
                .registrationClick()
                .longPause()
                .shortPause();

        String actual = "РЕГИСТРАЦИЯ ПРОШЛА УСПЕШНО!";
        String expectedUrl = driver.getCurrentUrl();
        Assert.assertEquals(signinPage.getMessage(), actual);
    }
    @Test
    @DisplayName("вход после регистрации")
    public void test_6() throws InterruptedException {
         signinPage = new SigninPage(driver);
        signinPage.callPage()
                .pause()
                .inputLogin("+7(999)999999")
                .pause()
                .clickLogin()
                .pause()
                .inputCode("111111")
                .pause()
                .clickComIn()
                .pause();
        myPage=new MyPage(driver);
        String actual="Дмитрий Петров";
        Assert.assertEquals(myPage.getUserName(),actual);
    }
    @Test
    @DisplayName("переход между страницами во время сессии")
    public void test_7() throws InterruptedException {
        myPage=new MyPage(driver);
        myPage.redirectOnProfilePage()
                .longPause()
                .redirectOnMainPage()
                .pause()
                .redirectOnMyPage()
                .longPause()
                .logOut()
                .pause();
        String actualUrl = "http://localhost:8085/signin";
        String expectedUrl = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl, actualUrl);
    }
    @Test
    @DisplayName("повторный вход по email")
    public void test_8() throws InterruptedException {
        signinPage = new SigninPage(driver);
        signinPage.callPage()
                .pause()
                .selectEmailField()
                .pause()
                .inputMail("mailmail@yandex.ru")
                .pause()
                .clickLogin()
                .pause()
                .inputMailCode("111111")
                .pause()
                .clickComInByEmail()
                .pause()
                .pause()
                .pause();
        myPage=new MyPage(driver);
        String actual="Дмитрий Петров";
        Assert.assertEquals(myPage.getUserName(),actual);
    }
}