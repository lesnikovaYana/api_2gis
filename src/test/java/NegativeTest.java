import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class NegativeTest extends BaseTest{

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"999999999999999999999", "0", "3.0", "test", "-1", "01", "2 5", " 5", "9 ",
    "-999999999999999999999"})
    void getRegionsPageNegativeTest(String i){

        Unirest.get("")
                .queryString("page", i)
                .asJson()
                .ifSuccess(jsonResponse -> assertThat(jsonResponse.getStatus(), equalTo(200)))
                .ifFailure(jsonResponse -> {System.out.println(jsonResponse.getStatus());
                jsonResponse.getParsingError().ifPresent(e -> System.out.println("Error body: " + e.getOriginalBody()));});
    }


}