package nextstep.subway.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class LineSteps {
    public static ExtractableResponse<Response> 지하철_노선_생성_요청(String name, String color) {
        return RestAssured
                .given().log().all()
                .body(createLineParams(name, color))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/lines")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 지하철_노선_생성_요청(Map<String, String> params) {
        return RestAssured
                .given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/lines")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 지하철_노선_생성_요청(String accessToken, Map<String, String> params) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/lines")
                .then().log().all().extract();
    }

    public static Long 지하철_노선_생성_요청(String name, String color, Long upStation, Long downStation, int distance) {
        return 지하철_노선_생성_요청(name, color, upStation, downStation, distance, 0);
    }

    public static Long 지하철_노선_생성_요청(String name, String color, Long upStation, Long downStation, int distance, int duration) {
        Map<String, String> lineCreateParams = createLineParams(name, color, 0, upStation, downStation, distance, duration);
        return 지하철_노선_생성_요청(lineCreateParams).jsonPath().getLong("id");
    }

    public static Long 지하철_노선_생성_요청(String name, String color, int additionalFare, Long upStation, Long downStation, int distance, int duration) {
        Map<String, String> lineCreateParams = createLineParams(name, color, additionalFare, upStation, downStation, distance, duration);
        return 지하철_노선_생성_요청(lineCreateParams).jsonPath().getLong("id");
    }

    public static ExtractableResponse<Response> 지하철_노선_목록_조회_요청() {
        return RestAssured
                .given().log().all()
                .when().get("/lines")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 지하철_노선_조회_요청(ExtractableResponse<Response> createResponse) {
        return RestAssured
                .given().log().all()
                .when().get(createResponse.header("location"))
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 지하철_노선_조회_요청(Long id) {
        return RestAssured
                .given().log().all()
                .when().get("/lines/{id}", id)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 지하철_노선에_지하철_구간_생성_요청(Long lineId, Long upStationId, Long downStationId, int distance, int duration) {

        Map<String, String> params = new HashMap<>();
        params.put("upStationId", String.valueOf(upStationId));
        params.put("downStationId", String.valueOf(downStationId));
        params.put("distance", String.valueOf(distance));
        params.put("duration", String.valueOf(distance));

        return 지하철_노선에_지하철_구간_생성_요청(lineId, params);
    }

    public static ExtractableResponse<Response> 지하철_노선에_지하철_구간_생성_요청(Long lineId, Map<String, String> params) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/lines/{lineId}/sections", lineId)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 지하철_노선에_지하철_구간_생성_요청(String accessToken, Long lineId, Map<String, String> params) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/lines/{lineId}/sections", lineId)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 지하철_노선에_지하철_구간_제거_요청(Long lineId, Long stationId) {
        return RestAssured.given().log().all()
                .when().delete("/lines/{lineId}/sections?stationId={stationId}", lineId, stationId)
                .then().log().all().extract();
    }

    private static Map<String, String> createLineParams(String name, String color, int additionalFare, Long upStation, Long downStation, int distance, int duration) {
        Map<String, String> params = new HashMap<>();
        params.put("name", String.valueOf(name));
        params.put("color", String.valueOf(color));
        params.put("additionalFare", String.valueOf(additionalFare));
        params.put("upStationId", String.valueOf(upStation));
        params.put("downStationId", String.valueOf(downStation));
        params.put("distance", String.valueOf(distance));
        params.put("duration", String.valueOf(duration));

        return params;
    }

    private static Map<String, String> createLineParams(String name, String color) {
        Map<String, String> params = new HashMap<>();
        params.put("name", String.valueOf(name));
        params.put("color", String.valueOf(color));

        return params;
    }

}

