package com.weather.model;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class LocationTest {

	Location location = new Location();

	@Test
	public void testLocationName() {
		assertNull(location.getName());
	}
}
