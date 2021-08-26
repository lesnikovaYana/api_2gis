import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class NegativeTest extends BaseTest{

    //TODO доделать негативные тесты
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"ТОМСК", "moscow", "тест", "Ош", " ", "~!@#$%^&*()?>,./",
            "Уилла Реал де ла Санта-Фе де Сан-Франциско де Асис"})
    void getNameRegionSearchNegativeTest(String i){
        Unirest.get("")
                .queryString("page", i)
                .asJson()
                .ifSuccess(jsonResponse -> assertThat(jsonResponse.getStatus(), equalTo(200)))
                .ifFailure(jsonResponse -> {logger.error(jsonResponse.getStatus());
                    jsonResponse.getParsingError().ifPresent(e -> logger.error("Error body: " + e.getOriginalBody()));});
    }

    //TODO разделить обрабатываемые ошибки и обычные запросы
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"999999999999999999999", "0", "3.0", "test", "-1", "01", "2 5", " 5", "9 ",
    "-999999999999999999999", " "})
    void getRegionsPageNegativeTest(String i){
        Unirest.get("")
                .queryString("page", i)
                .asJson()
                .ifSuccess(jsonResponse -> {
                    assertThat(jsonResponse.getStatus(), equalTo(200));
                    assertThat(jsonResponse.getBody().getObject().get("error"), CoreMatchers.is(notNullValue()));})
                .ifFailure(jsonResponse -> {logger.error(jsonResponse.getStatus());
                jsonResponse.getParsingError().ifPresent(e -> logger.error("Error body: " + e.getOriginalBody()));});
    }
}
