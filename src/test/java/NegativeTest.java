import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class NegativeTest extends BaseTest{

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"ТОМСК", "moscow", "тест", "Ош", " ", "~!@#$%^&*()?>,./",
            "Посёлок центральной усадьбы совхоза 40 лет Октября", " Омск", "Омск "})
    void getNameRegionSearchNegativeTest(String i){
        Unirest.get("")
                .queryString("q", i)
                .asJson()
                .ifSuccess(jsonResponse -> {
                    try {
                        assertThat(jsonResponse.getStatus(), equalTo(200));
                        JSONArray array = jsonResponse.getBody().getObject().getJSONArray("items");
                        for(int j=0;j<array.length();j++){
                            String object = array.getJSONObject(j).getString("name");
                            assertThat(object,containsStringIgnoringCase(i));
                        }
                    }catch (Exception e){
                        assertThat(jsonResponse.getBody().getObject().get("error"), CoreMatchers.is(notNullValue()));
                        logger.error(e.getMessage());
                    }
                })
                .ifFailure(jsonResponse -> {logger.error(jsonResponse.getStatus());
                    jsonResponse.getParsingError().ifPresent(e -> logger.error("Error body: " + e.getOriginalBody()));});
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"999999999999999999999", "0", "3.0", "test", "-1", "01", "2 5", " 5", "9 ",
    "-999999999999999999999", " "})
    void getRegionsPageNegativeTest(String i){
        Unirest.get("")
                .queryString("page", i)
                .asJson()
                .ifSuccess(jsonResponse -> {
                    try {
                        assertThat(jsonResponse.getStatus(), equalTo(200));
                        assertThat(jsonResponse.getBody().getObject().getJSONArray("items"), CoreMatchers.is(notNullValue()));
                    }catch (Exception e){
                        assertThat(jsonResponse.getBody().getObject().get("error"), CoreMatchers.is(notNullValue()));
                        logger.error(e.getMessage());
                    }
                })
                .ifFailure(jsonResponse -> {logger.error(jsonResponse.getStatus());
                jsonResponse.getParsingError().ifPresent(e -> logger.error("Error body: " + e.getOriginalBody()));});
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"999999999999999999999", "0", "3.0", "test", "-1", "01", "1 5", " 5", "10 ",
            "-999999999999999999999", " ", "16", "1"})
    void getRegionsPageSizeNegativeTest(String i){
        Unirest.get("")
                .queryString("page_size", i)
                .asJson()
                .ifSuccess(jsonResponse -> {
                    try {
                        assertThat(jsonResponse.getStatus(), equalTo(200));
                        assertThat(jsonResponse.getBody().getObject().getJSONArray("items").length(), equalTo(i));
                    }catch (Exception e){
                        assertThat(jsonResponse.getBody().getObject().get("error"), CoreMatchers.is(notNullValue()));
                        logger.error(e.getMessage());
                    }
                })
                .ifFailure(jsonResponse -> {
                    logger.error(jsonResponse.getStatus());
                });
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"ua", "RU", "тест", "ca", "g", " ", "~!@#$%^&*()?>,./", "ее"})
    void getRegionsCountryCodeNegativeTest(String i) {
        Unirest.get("")
                .queryString("country_code", i)
                .asJson()
                .ifSuccess(jsonResponse -> {
                    try {
                        assertThat(jsonResponse.getStatus(), equalTo(200));
                        JSONArray array = jsonResponse.getBody().getObject().getJSONArray("items");
                        for(int j=0;j<array.length();j++){
                            String object = array.getJSONObject(j).getJSONObject("country").getString("code");
                            assertThat(object,equalTo(i));
                        }
                    }catch (Exception e){
                        assertThat(jsonResponse.getBody().getObject().get("error"), CoreMatchers.is(notNullValue()));
                        logger.error(e.getMessage());
                    }
                })
                .ifFailure(jsonResponse -> {
                    logger.error(jsonResponse.getStatus());
                });
    }
}
