package com.population;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CountryReportTest {

    @Test
    public void testReportRunsWithoutError() {
        assertDoesNotThrow(() -> CountryReport.generate());
    }
}
