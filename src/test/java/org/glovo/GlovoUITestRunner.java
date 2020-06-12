package org.glovo;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(features = "src/test/resources/features", glue = "org.glovo", plugin = {"json:cucumber.json", "html:cucumber"})
public class GlovoUITestRunner extends AbstractTestNGCucumberTests {

    @DataProvider(parallel = true)
    public Object[][] tests() {
        return super.scenarios();
    }
}
