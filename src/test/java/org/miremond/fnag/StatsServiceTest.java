package org.miremond.fnag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class StatsServiceTest {

    @Test
    public void testStandardStats() {
        FnagData data = FileParser.parse(getFilePath("fnag.txt"));
        Object[] sales = StatsService.getTopSales(data).toArray();
        assertEquals(1, sales.length);
        assertEquals("TOPSALE|T127|T-shirt 'no place like 127.0.0.1'|4", sales[0]);

        Object[] sellers = StatsService.getTopSellers(data).toArray();
        assertTrue(sellers.length == 1);
        assertEquals("TOPSELLER|Lyon|Alice|229.98", sellers[0]);
    }

    @Test
    public void testMultipleProducts() {
        FnagData data = FileParser.parse(getFilePath("fnag-multipleProducts.txt"));
        Set<Object> sales = new HashSet(Arrays.asList(StatsService.getTopSales(data).toArray()));
        Set<String> expected = new HashSet<>(Arrays.asList("TOPSALE|T127|T-shirt 'no place like 127.0.0.1'|4",
                "TOPSALE|LMUSB|Lance-missile USB|4"));

        assertEquals(expected, sales);
    }

    @Test
    public void testMultipleSellers() {
        FnagData data = FileParser.parse(getFilePath("fnag-multipleSellers.txt"));
        Set<Object> sellers = new HashSet(Arrays.asList(StatsService.getTopSellers(data).toArray()));
        Set<String> expected = new HashSet<>(
                Arrays.asList("TOPSELLER|Paris|Bob|400.00", "TOPSELLER|Paris|Chuck|400.00"));

        assertEquals(expected, sellers);
    }

    private Path getFilePath(String file) {
        return Paths.get("target/test-classes", file);
    }

}
