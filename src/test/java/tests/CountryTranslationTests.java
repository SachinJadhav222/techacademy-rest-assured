package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ExcelUtils;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class CountryTranslationTests {

    /**
     * DataProvider method to supply country translation data to the test.
     * Reads data from an Excel file and returns it as a two-dimensional array.
     *
     * @return A two-dimensional array of country translations.
     */
    @DataProvider(name = "countryTranslations")
    public Object[][] getCountryTranslations() {
        // Path to the Excel file containing test data
        String excelFilePath = "src/test/resources/testdata/country_translations.xlsx";
        // Read the data from the specified sheet in the Excel file
        List<String> translations = ExcelUtils.readExcelData(excelFilePath, "Sheet1");
        // Initialize a two-dimensional array to hold the data
        Object[][] data = new Object[translations.size()][1];
        // Populate the array with data from the Excel file
        for (int i = 0; i < translations.size(); i++) {
            data[i][0] = translations.get(i);
        }
        // Return the populated data array
        return data;
    }

    /**
     * Test method to verify country translations using RestAssured.
     * Uses the data provided by the DataProvider method.
     *
     * @param translation The country translation to be tested.
     */
    @Test(dataProvider = "countryTranslations")
    public void testCountryTranslation(String translation) {
        // Construct the URL using the provided translation
        String url = String.format("https://restcountries.com/v3.1/translation/%s", translation);
        // Send a GET request to the URL
        Response response = RestAssured.get(url);

        // Verify the response status code and body content
        response.then()
                .assertThat()
                .statusCode(200)
                .body("translations", notNullValue());
    }
}
