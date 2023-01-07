package com.example.MyBookShopApp.selenium;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationPage {
    private String url = "http://localhost:8085/signup";
    private ChromeDriver driver;

    public RegistrationPage(ChromeDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    public RegistrationPage callPage() {
        driver.get(url);
        return this;
    }
    public RegistrationPage longPause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }
    public RegistrationPage shortPause() throws InterruptedException {
        Thread.sleep(1000);
        return this;
    }


    @FindBy(xpath = "//*[contains(@id, 'mail')]")
    private WebElement mailField;

    @FindBy(xpath = "//*[contains(@id, 'name')]")
    private WebElement  nameField;

    @FindBy(xpath = "//*[contains(@id, 'phone')]")
    private WebElement phoneField;

    @FindBy(xpath = "//*[contains(@id, 'pass')]")
    private WebElement passwordField;

    @FindBy(xpath = "//*[contains(@id, 'submitPhone')]")
    private WebElement submitPhoneBtn;

    @FindBy(xpath = "//*[contains(@id, 'phoneCode')]")
    private WebElement phoneCodeField;

    @FindBy(xpath = "//*[contains(@id, 'submitMail')]")
    private WebElement submitMailBtn;

    @FindBy(xpath = "/html/body/div/div[2]/main/form/div/div/div[3]/div[3]/div")
    private WebElement forActivation;

    @FindBy(xpath = "//*[contains(@id, 'mailCode')]")
    private WebElement mailCodeField;

    @FindBy(xpath = "//*[contains(@id, 'registration')]")
    private WebElement registrationBtn;

    public RegistrationPage inputName(String name){
        nameField.sendKeys(name);
        return this;
    }
    public RegistrationPage inputPhone(String name){
        phoneField.sendKeys(name);
        return this;
    }
    public RegistrationPage inpuPassword(String name){
        passwordField.sendKeys(name);
        return this;
    }
    public RegistrationPage inputMail(String name){
        mailField.sendKeys(name);
        return this;
    }
    public RegistrationPage inputCodeMail(String name){
        mailCodeField.sendKeys(name);
        return this;
    }
    public RegistrationPage inputCodePhone(String name){
        phoneCodeField.sendKeys(name);
        return this;
    }
    public RegistrationPage submitMailClick(){
        submitMailBtn.click();
        return this;
    }
    public RegistrationPage submitPhoneClick(){
        submitPhoneBtn.click();
        return this;
    }
    public RegistrationPage registrationClick(){
        registrationBtn.click();
        return this;
    }
    public RegistrationPage forActivationClick(){
       forActivation.click();
        return this;
    }

}
