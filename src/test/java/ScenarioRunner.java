import apiServices.ReportHook;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.Test;

import static com.intuit.karate.Runner.parallel;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScenarioRunner {
   @Test
    void testParallel(){
       Results results = Runner.path("classpath:apiServices")
                       .hook(new ReportHook())
                               .parallel(1);
       assertEquals(0, results.getFailCount(), results.getErrorMessages());
   }


}
