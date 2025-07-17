package com.population;

import org.junit.jupiter.api.Test;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectorTest {

    @Test
    public void testDatabaseConnection() {
        assertDoesNotThrow(() -> {
            Connection conn = DatabaseConnector.connect();
            assertNotNull(conn);
            conn.close();
        });
    }
}
