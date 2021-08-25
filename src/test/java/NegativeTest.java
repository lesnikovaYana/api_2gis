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

    //TODO обработать ошибку ноль
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"999999999999999999999", "0", "3.0", "test", "-1", "01", "2 5", " 5", "9 ",
    "-999999999999999999999"})
    void getRegionsPageNegativeTest(String i){
        HttpResponse<JsonNode> response = Unirest.get("")
                .queryString("page", i)
                .asJson();
        assertThat(response.isSuccess(), CoreMatchers.is(true));
        assertThat(response.getStatus(), equalTo(200));
    }
}
