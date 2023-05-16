package subway.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import subway.ui.dto.request.SectionRequest;

@DisplayName("지하철 구간 관련 기능")
@Sql("/test-data.sql")
public class SectionIntegrationTest extends IntegrationTest{

	private SectionRequest request;
	private ExtractableResponse<Response> response;

	@BeforeEach
	public void setUp(){
		super.setUp();
		request = new SectionRequest("2호선","잠실","역삼", 5L);
		response = RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(request)
			.when().post("/sections")
			.then().log().all()
			.extract();
	}

	@DisplayName("지하철 구간을 생성한다")
	@Test
	void createSection(){
		// then
		Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
	}

	@DisplayName("특정 노선의 지하철역을 조회한다")
	@Test
	void findAllByLine(){
		// when
		final long lineId = 1;
		final ExtractableResponse<Response> findResponse = RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/sections/{lineId}", lineId)
			.then().log().all()
			.extract();

		// then
		Assertions.assertThat(findResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
	}
}
