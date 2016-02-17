package com.taxholic.selenium;

import java.io.IOException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestSelenium  {
	
   
	   
   @Test
   public void testGoogleSearch() throws IOException {
	   
	   WebDriver driver = new FirefoxDriver();
	   driver.get("http://www.google.com");
	   WebElement element = driver.findElement(By.name("q"));
	   element.sendKeys("바보");
	   driver.findElement(By.name("btnK")).click();
     
	   new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("resultStats")));
     
	   System.out.println( driver.findElement(By.id("resultStats")).getText());
	   System.out.println( driver.findElement(By.id("resultStats")).getText().replaceAll("[^0-9]", ""));
 
	   driver.quit();
 
   }

}