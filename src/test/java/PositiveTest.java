import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PositiveTest extends BaseTest{
    @Test
    void getRegionsPositiveTest(){
        HttpResponse<JsonNode> response = Unirest.get("")
                .asJson();
        int total = response.getBody().getObject().getInt("total");
        //int s = response.getBody().getObject().getJSONArray("items").length();
        //System.out.println(s);
        assertThat(response.getStatus(), equalTo(200));
        assertThat(total, equalTo(22));
    }

    @ParameterizedTest
    @ValueSource(strings = {"вла"})
    void getCodeRegionSearchPositiveTest(String i){
        HttpResponse<JsonNode> response = Unirest.get("")
                .queryString("q", i)
                .asJson();
        assertThat(response.isSuccess(), CoreMatchers.is(true));
        assertThat(response.getStatus(), equalTo(200));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 100, 57, 7999})
    void getRegionsPagePositiveTest(int i){
        HttpResponse<JsonNode> response = Unirest.get("")
                .queryString("page", i)
                .asJson();
        assertThat(response.isSuccess(), CoreMatchers.is(true));
        assertThat(response.getStatus(), equalTo(200));
    }
}
