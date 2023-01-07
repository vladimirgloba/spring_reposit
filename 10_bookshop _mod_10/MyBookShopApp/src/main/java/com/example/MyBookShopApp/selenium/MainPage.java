package com.example.MyBookShopApp.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {

    private String url = "http://localhost:8085/";
    private ChromeDriver driver;

    public MainPage(ChromeDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public MainPage callPage() {
        driver.get(url);
        return this;
    }
    @FindBy(xpath = "/html/body/header/div[1]/div/div/div[3]/div/a[3]")
    private WebElement onMytBtn;

    @FindBy(xpath = "/html/body/header/div[1]/div/div/div[3]/div/a[3]")
    private WebElement logIn;

    public MainPage pause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }

    public MainPage setUpSearchToken(String token) {
        WebElement element = driver.findElement(By.id("query"));
        element.sendKeys(token);
        return this;
    }
    public MyPage redirectOnMyPage() {
       onMytBtn.click();
        return new MyPage(driver);
    }

    public MainPage submit() {
        WebElement element = driver.findElement(By.id("search"));
        element.submit();
        return this;
    }
    public MainPage logIn() {
        logIn.click();
        return this;
    }


}
