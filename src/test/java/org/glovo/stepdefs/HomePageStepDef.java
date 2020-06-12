package org.glovo.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.glovo.pageobjects.EmployeeHomePage;
import org.glovo.utils.BrowserUtil;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.List;

public class HomePageStepDef {

    @Autowired
    private BrowserUtil browserUtil;
    @Autowired
    private Environment environment;
    @Autowired
    private EmployeeHomePage employeeHomePage;

    @Given("user launches {word} application")
    public void launchApp(String appName) {
        browserUtil.getBrowser().get(environment.getProperty(appName + ".url"));
    }

    @When("user {word} following records from the grid")
    public void selectRecords(List<String> records) {
        for (String name :
                records) {
            browserUtil.click(employeeHomePage.getRecord(name));
        }
    }

    @When("user clicks on view selected data")
    public void clickOnViewSelectedData() {
        browserUtil.click(By.cssSelector("button#btn"));
        Assertions.assertThat(browserUtil.getElements(By.className("jqx-listitem-element")).size()).isGreaterThanOrEqualTo(1);
    }

    @Then("assert following records should be visible in resultant grid")
    public void assertRecords(List<List<String>> records) {
        List<EmployeeHomePage.SelectedEmployee> employees = employeeHomePage.getSelectedRecords();
        for (int i = 0; i < records.size(); i++) {
            String name = records.get(i).get(0);
            String location = records.get(1).get(1);
            Assertions.assertThat(employees.get(i).getEmpName()).isEqualToIgnoringCase(name);
            Assertions.assertThat(employees.get(i).getLocation()).isEqualToIgnoringCase(location);
        }
    }
}
