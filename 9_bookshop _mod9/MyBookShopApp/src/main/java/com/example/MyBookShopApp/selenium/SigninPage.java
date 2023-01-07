package com.example.MyBookShopApp.selenium;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SigninPage {
    private String url = "http://localhost:8085/signin";
    private ChromeDriver driver;

    public SigninPage(ChromeDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public SigninPage callPage() {
        driver.get(url);
        return this;
    }

    @FindBy(xpath = "/html/body/div/div[2]/main/form/div/div[1]/div[1]/label/span")
    WebElement message;

    @FindBy(xpath = "/html/body/div/div[2]/main/form/div/div[1]/div[2]/div/div[2]/label/span[2]")
    WebElement selectMailFiled;

    @FindBy(xpath = "//*[@id=\"mail\"]")
    WebElement mailFiled;

    @FindBy(xpath = "//*[contains(@id, 'phonecode')]")
    private WebElement codeField;

    @FindBy(xpath = "//*[contains(@id, 'sendauth')]")
    private WebElement btnLogin;

    @FindBy(xpath = "//*[@id=\"mailcode\"]")
    private WebElement mailCodeField;

    @FindBy(xpath = "//*[@id=\"toComeInMail\"]")
    private WebElement toComeInMailBtn;

    @FindBy(xpath = "//*[contains(@id, 'toComeInPhone')]")
    private WebElement btnComeInPhone;

    @FindBy(xpath = "/html/body/div/div[2]/main/form/div/div[2]/div[2]/div")
    private WebElement errorField;

    @FindBy(xpath = "/html/body/div/div[2]/main/form/div/div[4]/a")
    private WebElement registrationBtn;

    public  String getMessage(){
        return message.getText();
    }

    @FindBy(xpath = "//*[contains(@id, 'phone')]")
    private WebElement loginField;

    public SigninPage pause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }
    public SigninPage inputLogin(String login) {
        loginField.sendKeys(login);
    return this;}

    public SigninPage selectEmailField() {
        selectMailFiled.click();
        return this;}

    public SigninPage inputMail(String mail) {
       mailFiled.sendKeys(mail);
        return this;}

    public SigninPage inputMailCode(String code) {
        mailCodeField.sendKeys(code);
        return this;}


    public String getErrorMessage(){
    return errorField.getText();
}
public SigninPage clickLogin(){
    btnLogin.click();
    return this;
}
    public SigninPage clickComIn(){
        btnComeInPhone.click();
        return this;
    }
    public SigninPage clickComInByEmail(){
        toComeInMailBtn.click();
        return this;
    }
    public SigninPage inputCode(String code){
        codeField.sendKeys(code);
        return this;
    }
    public SigninPage regBtnClick(){
        registrationBtn.click();
        return this;
    }
}
