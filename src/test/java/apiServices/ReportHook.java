package apiServices;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.intuit.karate.Results;
import com.intuit.karate.RuntimeHook;
import com.intuit.karate.Suite;
import com.intuit.karate.core.FeatureRuntime;
import com.intuit.karate.core.ScenarioRuntime;
import com.intuit.karate.core.Step;
import com.intuit.karate.core.StepResult;
import com.intuit.karate.http.HttpRequest;
import com.intuit.karate.http.Response;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportHook implements RuntimeHook {
    public static ExtentTest test;
    String Status, Error, Tags;

    @Override
    public void beforeSuite(Suite suite) {
        ExtentManager.createReport();

    }

    @Override
    public void afterSuite(Suite suite) {
        ExtentManager.getInstance().flush();

    }

    @Override
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
        test.info("<b>Status: </b>" + Status);
        if (Status == "Failed") {
            test.fail("<b>Error : </b>" + Error);
        }

    }


    @Override
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

        test = ExtentManager.getInstance().createTest("<b>Scenario : </b>" + sr.scenario.getName());
        test.info("<b>Feature : </b>" + sr.scenario.getFeature().getName());
        test.info("<b>Url : </b>" + request.getUrl());
        test.info("<b> Steps :</b>" + sr.result.getScenario().getSteps());
        test.info("<b>Status Code :</b>" + response.getStatus());
        test.info("<b>Method :</b>" + request.getMethod());
        test.info("<b> Tags :</b>" + Tags);
        String res = new String(response.getBody());
        test.info("<b>Response :</b>" + res);
    }
}
