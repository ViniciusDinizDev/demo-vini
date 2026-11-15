package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumTeste {
    @Test 
    public void abrirNavegador(){
        WebDriver webDriver = new ChromeDriver();

        webDriver.get("https://www.selenium.dev/selenium/web/web-form.html");
        
        webDriver.findElement(By.name("my-text")).
        sendKeys("Selenium");
        
        webDriver.findElement(By.cssSelector("button")).click();
        
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        // String msg = webDriver.findElement(By.id("message")).getText();
        
        assertEquals("Received!", msg);
    }
}