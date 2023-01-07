package com.example.MyBookShopApp.selenium;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyPage {

    private String url = "http://localhost:8085/my";
    private ChromeDriver driver;

    public MyPage(ChromeDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    public MyPage callPage() {
        driver.get(url);
        return this;
    }
    public MyPage longPause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }
    public MyPage shortPause() throws InterruptedException {
        Thread.sleep(1000);
        return this;
    }


    @FindBy(xpath = "/html/body/header/div[1]/div/div/div[3]/div/a[4]/span[1]")
    private WebElement userField;

    @FindBy(xpath = "//*[@id=\"navigate\"]/ul/li[1]/a")
    private WebElement mainPageBtn;

    @FindBy(xpath = "/html/body/header/div[1]/div/div/div[3]/div/a[4]")
    private WebElement profilePageBtn;

    @FindBy(xpath = "/html/body/header/div[1]/div/div/div[3]/div/a[5]")
    private WebElement outBtn;

    @FindBy(xpath = "/html/body/header/div[1]/div/div/div[3]/div/a[3]")
    private WebElement logoutBtn;


    public MyPage  userLogout() {
        logoutBtn.click();
        return this;
    }
    public String getUserName(){
        return userField.getText();
    }

    public SigninPage logOut(){
        outBtn.click();
        return new SigninPage(driver);
    }

    public MainPage redirectOnMainPage(){
        mainPageBtn.click();
        return new MainPage(driver);
    }
    public ProfilePage redirectOnProfilePage(){
        profilePageBtn.click();
        return new ProfilePage(driver);
    }
}
