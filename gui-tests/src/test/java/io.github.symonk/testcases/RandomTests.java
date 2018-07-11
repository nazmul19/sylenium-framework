package io.github.symonk.testcases;

import com.codeborne.selenide.Configuration;
import io.github.symonk.common.helpers.localisation.ProvidesLanguageValues;
import io.github.symonk.configurations.guice.PropertiesModule;
import io.github.symonk.configurations.properties.ManagesFrameworkProperties;
import io.github.symonk.listeners.TestExecutionListener;
import io.github.symonk.pageobjects.pages.GooglePage;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.testng.annotations.*;

import javax.inject.Inject;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Slf4j
@Epic("This is an Epic")
@Feature("This is a Feature")
@Guice(modules = PropertiesModule.class)
@Listeners(TestExecutionListener.class)
public class RandomTests extends TestBaseTemplate {

  @Inject
  public RandomTests(final ManagesFrameworkProperties properties, final ProvidesLanguageValues languageHelper) {
    super(properties, languageHelper);
  }

  @Parameters({"browser"})
  @BeforeClass(alwaysRun = true, description = "Test Configuration")
  public void beforeClass(final String browser) {
    log.info("do something to begin with!");
    Configuration.browser = browser;
  }

  @Test(description = "Test One!", invocationCount = 1)
  @Story("This is a story")
  @Link(name = "allure", type = "mylink")
  @Issue("123")
  @TmsLink("456")
  @Severity(SeverityLevel.CRITICAL)
  public void testOne() {
    open("/", GooglePage.class).searchFor("simon").someElement().should(visible);
  }

  @Test(description = "Test Two!", invocationCount = 1)
  @Story("Another Story")
  @Link(name = "allure", type = "mylink")
  @Issue("999")
  @TmsLink("888")
  @Severity(SeverityLevel.CRITICAL)
  public void testTwo() {
    open("https://www.google.co.uk", GooglePage.class)
        .searchFor("not-duplicate")
        .someElement()
        .should(visible);
  }

  @Test(description = "Test Three!", invocationCount = 1)
  @Link(name = "allure", type = "mylink")
  public void testThree() {
    open("/", GooglePage.class).searchFor("jenkins").someElement().should(visible);
  }

  @Test(description = "Test Four!", invocationCount = 1)
  @Link(name = "allure", type = "mylink")
  public void testFour() {
    open("/", GooglePage.class).searchFor("jenkins").someElement().should(visible);
  }

  @Test(description = "Test Five!", invocationCount = 1)
  @Link(name = "allure", type = "mylink")
  public void testFive() {
    open("/", GooglePage.class).searchFor("jenkins").someElement().should(visible);
  }

  @Test(description = "Test Six!", invocationCount = 1)
  @Link(name = "allure", type = "mylink")


  @AfterClass(alwaysRun = true, description = "Test Teardown")
  public void afterClass() {}
}
