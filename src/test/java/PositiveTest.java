import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PositiveTest extends BaseTest{
    @Test
    void getRegionsDefaultPositiveTest(){
        Unirest.get("")
                .asJson()
                .ifSuccess(jsonResponse -> {
                    try {
                        assertThat(jsonResponse.getStatus(), equalTo(200));
                        assertThat(jsonResponse.getBody().getObject().getJSONArray("items").length(), equalTo(15));
                    } catch (Exception e){
                        logger.error(e.getMessage());
                    }
                })
                .ifFailure(jsonResponse -> {
                    logger.error(jsonResponse.getStatus());
                });
    }

    @Test
    void getRegionsPairParametersPositiveTest(){
        Unirest.get("")
                .queryString("country_code", "ru")
                .queryString("page_size", "5")
                .asJson()
                .ifSuccess(jsonResponse -> {
                    try {
                        assertThat(jsonResponse.getStatus(), equalTo(200));
                        assertThat(jsonResponse.getBody().getObject().getJSONArray("items").length(), equalTo(5));
                        JSONArray array = jsonResponse.getBody().getObject().getJSONArray("items");
                        for(int j=0;j<array.length();j++){
                            String object = array.getJSONObject(j).getJSONObject("country").getString("code");
                            assertThat(object,equalTo("ru"));
                        }
                    } catch (Exception e){
                        logger.error(e.getMessage());
                    }
                })
                .ifFailure(jsonResponse -> {
                    logger.error(jsonResponse.getStatus());
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"влад", "Актобе", "Уфа", "Омск", "бирск", "москва",
            "Усть-Каменогорск","Нижний Новгород"})
    void getNameRegionSearchPositiveTest(String i){
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
                    } catch (Exception e){
                        logger.error(e.getMessage());
                    }
                })
                .ifFailure(jsonResponse -> {
                    logger.error(jsonResponse.getStatus());
                });
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 100, 57, 7999})
    void getRegionsPagePositiveTest(int i){
       Unirest.get("")
                .queryString("page", i)
                .asJson()
                .ifSuccess(jsonResponse -> {
                   try {
                       assertThat(jsonResponse.getStatus(), equalTo(200));
                       assertThat(jsonResponse.getBody().getObject().getJSONArray("items"), CoreMatchers.is(notNullValue()));
                   } catch (Exception e){
                       logger.error(e.getMessage());
                   }
               })
                .ifFailure(jsonResponse -> {
                    logger.error(jsonResponse.getStatus());
                });
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15})
    void getRegionsPageSizePositiveTest(int i){
        Unirest.get("")
                .queryString("page_size", i)
                .asJson()
                .ifSuccess(jsonResponse -> {
                    try {
                        assertThat(jsonResponse.getStatus(), equalTo(200));
                        assertThat(jsonResponse.getBody().getObject().getJSONArray("items").length(), equalTo(i));
                    }catch (Exception e){
                        logger.error(e.getMessage());
                    }
                })
                .ifFailure(jsonResponse -> {
                    logger.error(jsonResponse.getStatus());
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"ru", "kg", "kz", "cz"})
    void getRegionsCountryCodePositiveTest(String i){
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
                    }catch (Error e){
                        logger.error(e.getMessage());
                    }
                })
                .ifFailure(jsonResponse -> {
                    logger.error(jsonResponse.getStatus());
                });
    }
}