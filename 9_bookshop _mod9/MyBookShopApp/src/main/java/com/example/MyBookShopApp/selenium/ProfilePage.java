package com.example.MyBookShopApp.selenium;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilePage {
    private String url = "http://localhost:8085/profile";
    private ChromeDriver driver;
    public ProfilePage(ChromeDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    public ProfilePage callPage() {
        driver.get(url);
        return this;
    }
    public ProfilePage longPause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }
    public ProfilePage shortPause() throws InterruptedException {
        Thread.sleep(1000);
        return this;
    }

    @FindBy(xpath = "/html/body/header/div[1]/div/div/div[3]/div/a[4]/span[1]")
    private WebElement userField;

    @FindBy(xpath = "//*[@id=\"navigate\"]/ul/li[1]/a")
    private WebElement mainPageBtn;

    public String getUserPage(){
        return userField.getText();
    }
public MainPage redirectOnMainPage(){
        mainPageBtn.click();
        return new MainPage(driver);
}
}
