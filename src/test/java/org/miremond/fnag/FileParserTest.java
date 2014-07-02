package org.miremond.fnag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.miremond.fnag.FnagData.ProductDetail;
import org.miremond.fnag.FnagData.SaleDetail;

public class FileParserTest {

    @Test
    public void testStandardFile() {
        Path path = getFilePath("fnag.txt");
        FnagData data = FileParser.parse(path);

        assertEquals(new ProductDetail(2000, "Lance-missile USB"), data.getProductDetail("LMUSB"));
        assertEquals(new ProductDetail(20000, "Clavier mécanique"), data.getProductDetail("MKB"));
        assertEquals(new ProductDetail(1499, "T-shirt 'no place like 127.0.0.1'"), data.getProductDetail("T127"));

        assertEquals("Paris", data.getSellerLocation("Bob"));
        assertEquals("Lyon", data.getSellerLocation("Alice"));
        assertEquals("Paris", data.getSellerLocation("Chuck"));

        List<SaleDetail> sales = new ArrayList<>();
        sales.add(new SaleDetail("Bob", "LMUSB", 1, 2000));
        sales.add(new SaleDetail("Alice", "MKB", 1, 20000));
        sales.add(new SaleDetail("Alice", "T127", 2, 2998));
        sales.add(new SaleDetail("Bob", "T127", 1, 1499));
        sales.add(new SaleDetail("Chuck", "T127", 1, 1499));
        assertEquals(sales, data.getSales());
    }

    @Test
    public void testZeroFile() {
        Path path = getFilePath("fnag-zero.txt");
        FnagData data = FileParser.parse(path);

        assertNull(data.getProductDetail("LMUSB"));
        assertNull(data.getSellerLocation("Bob"));
        assertEquals(Collections.emptyList(), data.getSales());
    }

    @Test(expected = RuntimeException.class)
    public void testFileNotFound() {
        Path path = getFilePath("fnag-notfound.txt");
        FnagData data = FileParser.parse(path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyFile() {
        Path path = getFilePath("fnag-empty.txt");
        FnagData data = FileParser.parse(path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxProductSize() {
        Path path = getFilePath("fnag-maxProductSize.txt");
        FnagData data = FileParser.parse(path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxSaleSize() {
        Path path = getFilePath("fnag-maxSaleSize.txt");
        FnagData data = FileParser.parse(path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadLineNumber() {
        Path path = getFilePath("fnag-badLineNumber.txt");
        FnagData data = FileParser.parse(path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadProductFormat() {
        Path path = getFilePath("fnag-badProductFormat.txt");
        FnagData data = FileParser.parse(path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadSaleFormat() {
        Path path = getFilePath("fnag-badSaleFormat.txt");
        FnagData data = FileParser.parse(path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBlankField() {
        Path path = getFilePath("fnag-blankField.txt");
        FnagData data = FileParser.parse(path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadProductRef() {
        Path path = getFilePath("fnag-badProductRef.txt");
        FnagData data = FileParser.parse(path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultipleLocations() {
        Path path = getFilePath("fnag-multipleLocations.txt");
        FnagData data = FileParser.parse(path);
    }

    private Path getFilePath(String file) {
        return Paths.get("target/test-classes", file);
    }

}
