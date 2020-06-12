package org.glovo.pageobjects;

import io.cucumber.spring.CucumberTestContext;
import org.glovo.utils.BrowserUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
public class EmployeeHomePage {

    public class SelectedEmployee {
        private WebElement element;
        private String empName;
        private String location;

        public SelectedEmployee(WebElement element) {
            this.empName = element.getText().split(" ")[0];
            String[] arr = element.getText().split(" ");
            this.location = arr[arr.length - 1];
        }

        public String getEmpName() {
            return empName;
        }

        public String getLocation() {
            return location;
        }
    }

    @Autowired
    private BrowserUtil browserUtil;

    public WebElement getRecord(String empName) {
        return browserUtil.getElement(By.xpath(".//span[text()='" + empName + "']/preceding-sibling::span[contains(@class,'checkbox')]"));
    }

    public List<SelectedEmployee> getSelectedRecords() {
        return browserUtil.getElements(By.cssSelector("div[role='option'] span")).stream().map(SelectedEmployee::new).collect(Collectors.toList());
    }

}
