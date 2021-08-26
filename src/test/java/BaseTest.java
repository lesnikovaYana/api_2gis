import kong.unirest.Unirest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    static final Logger logger = LogManager.getLogger(Unirest.class);

    @BeforeAll
    static void beforeAll(){
        Unirest.config().defaultBaseUrl("https://regions-test.2gis.com/1.0/regions");
    }
}
