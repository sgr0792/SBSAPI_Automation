package restAssuredPackage;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import static io.restassured.RestAssured.*;

public class getRequest {
    @Test
    public void getAudiofile(){
//        Create the api endpoint with perform get operation
        RestAssured.baseURI = "https://www.sbs.com.au/guide/ajax_radio_program_catchup_data/language/mandarin/location/NSW/sublocation";
        RequestSpecification httpsRequest = RestAssured.given();
        Response response = httpsRequest.get("/Sydney");

//        Validate the response status code is 200
        Assert.assertEquals(response.getStatusCode(), 200, "the response is not success and the status code is : "+response.getStatusCode());

//        Assign the response's body to a local variable
        ResponseBody responseBody = response.body();
        System.out.println("The response is : " +responseBody.asPrettyString());

//        Assign the json response to a jsonPath class
        JsonPath jsonPathFinder = response.jsonPath();
        System.out.println("The value of Mp3 is : " +  jsonPathFinder.getJsonObject("archiveAudio.mp3"));

//        Store the values of mp3 object in whole response
        ArrayList<String> arrayList = new ArrayList<>(jsonPathFinder.getJsonObject("archiveAudio.mp3"));
        mp3Values classObject = new mp3Values();
        String[] expectedMPValue = classObject.returnMP3values();
        System.out.println("The value of arraylist size is : " + arrayList.size());

//        Validating the array in reverse order
        while (arrayList.size()>=1) {
            System.out.println("The value of Mp3 are : " + arrayList.get((arrayList.size())-1));
            Assert.assertEquals(arrayList.get((arrayList.size())-1), expectedMPValue[((arrayList.size())-1)], "The MP3 value is not matching in according to expected value at position : " +(arrayList.size()-1));
            arrayList.remove(arrayList.size()-1);
        }

//        Another way of validating the response using BDD format
        given()
                .when()
                    .get("https://www.sbs.com.au/guide/ajax_radio_program_catchup_data/language/mandarin/location/NSW/sublocation/Sydney")
                .then()
                    .statusCode(200);
    }
}
