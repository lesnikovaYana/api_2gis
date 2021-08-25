import kong.unirest.Unirest;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    @BeforeAll
    static void beforeAll(){
        Unirest.config().defaultBaseUrl("https://regions-test.2gis.com/1.0/regions");
    }
}
