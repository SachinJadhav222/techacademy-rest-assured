package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ExcelUtils;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class CountryTranslationTests {

    @DataProvider(name = "countryTranslations")
    public Object[][] getCountryTranslations() {
        String excelFilePath = "src/test/resources/testdata/country_translations.xlsx";
        List<String> translations = ExcelUtils.readExcelData(excelFilePath, "Sheet1");
        Object[][] data = new Object[translations.size()][1];
        for (int i = 0; i < translations.size(); i++) {
            data[i][0] = translations.get(i);
        }
        return data;
    }

    @Test(dataProvider = "countryTranslations")
    public void testCountryTranslation(String translation) {
        String url = String.format("https://restcountries.com/v3.1/translation/%s", translation);
        Response response = RestAssured.get(url);

        response.then()
                .assertThat()
                .statusCode(200)
                .body("translations", notNullValue());
    }
}
