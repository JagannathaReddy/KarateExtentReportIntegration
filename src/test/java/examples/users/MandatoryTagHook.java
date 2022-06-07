package examples.users;

import com.aventstack.extentreports.ExtentTest;
import com.intuit.karate.RuntimeHook;
import com.intuit.karate.Suite;
import com.intuit.karate.core.FeatureRuntime;
import com.intuit.karate.core.ScenarioRuntime;
import com.intuit.karate.core.Step;
import com.intuit.karate.core.StepResult;
import com.intuit.karate.http.HttpRequest;
import com.intuit.karate.http.Response;

public class MandatoryTagHook implements RuntimeHook {
    // return false if the scenario / item should be excluded from the test-run
    // throw RuntimeException (any) to abort
    public static ExtentTest test;
    String Status, Error, Tags;
    public boolean beforeScenario(ScenarioRuntime sr) {
        return true;
    }

    public void afterScenario(ScenarioRuntime sr) {

        if (sr.result.isFailed()) {
            Status = "Failed";

        } else {
            Status = "Passed";
        }

        if (sr.result.getError() == null) {
            Error = "No Error";
        } else {
            Error = sr.result.getError().toString();
        }

    }

    public boolean beforeFeature(FeatureRuntime fr) {
        return true;
    }

    public void afterFeature(FeatureRuntime fr) {

    }

    public void beforeSuite(Suite suite) {
        ExtentManager.createReport();
    }

    public void afterSuite(Suite suite) {
        ExtentManager.getInstance().flush();
    }

    public boolean beforeStep(Step step, ScenarioRuntime sr) {
        return true;
    }

    public void afterStep(StepResult result, ScenarioRuntime sr) {

    }

    // applicable only for Dynamic Scenario Outlines which have the need
    // to run background sections before executing the individual scenarios
    // to calculate the Examples table
    public void beforeBackground(ScenarioRuntime sr) {

    }

    public void afterBackground(ScenarioRuntime sr) {

    }

    public void beforeHttpCall(HttpRequest request, ScenarioRuntime sr) {

    }

    public void afterHttpCall(HttpRequest request, Response response, ScenarioRuntime sr) {
        Tags = "";
        if (sr.result.getScenario().getTags() == null) {
            Tags = "No Tags";
        } else {
            for (int z = 0; z < sr.result.getScenario().getTags().size(); z++) {

                Tags = Tags + sr.result.getScenario().getTags().get(z) + ",";
            }
            Tags = Tags.substring(0, Tags.length() - 1);
        }
        test = ExtentManager.getInstance().createTest("<b>Scenario: </b>" + sr.result.getScenario().getName());
        test.info("<b>Url: </b>" + request.getUrl());
        test.info("<b>Feature: </b>" + sr.result.getScenario().getFeature().getName());
        test.info("<b>Tag: </b>" + Tags);
        test.info("<b>Method: </b>" + request.getMethod());
        test.info("<b>Status: </b>" + Status);
        if (Status == "Failed") {
            test.fail("<b>Error: </b>" + Error);
        }
        String res = new String(request.getBody());
        test.info("<b>Response:</b>" + res);




    }

}
