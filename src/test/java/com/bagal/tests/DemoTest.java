package com.bagal.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DemoTest {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    String username="jsmith";
    String password="demo1234";


    @BeforeMethod
    void beforeMethod() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
    }
    @Test
    void demoTest1(){
        page.navigate("https://demo.testfire.net/index.jsp");
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.locator("id=LoginLink").click();
        assertThat(page.getByRole(AriaRole.HEADING,new Page.GetByRoleOptions().setName("Online Banking Login"))).isVisible();
        page.locator("id=uid").fill(username);
        page.locator("id=passw").fill(password);
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Login")).click();
        assertThat(page.locator("//a[normalize-space()='Sign Off']")).isVisible();
    }
    @AfterMethod
    void afterMethod() {
        page.close();
        context.close();
        browser.close();
        playwright.close();
    }
}
