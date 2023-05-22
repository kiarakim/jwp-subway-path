package subway.fixture;

import subway.domain.subway.Line;

import static subway.fixture.SectionsFixture.createSections;

public class LineFixture {

	public static Line createLine() {
		return new Line(createSections(), "2호선");
	}
}