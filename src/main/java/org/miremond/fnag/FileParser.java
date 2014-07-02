package org.miremond.fnag;

import static org.apache.commons.lang3.Validate.notBlank;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.miremond.fnag.FnagData.ProductDetail;
import org.miremond.fnag.FnagData.SaleDetail;

/**
 * This class parses the input file, checks it has a correct format and returns the corresponding data structure.
 * 
 * @author mremond
 *
 */
public class FileParser {

    private static final int MAX_PRODUCT = 100;

    private static final int MAX_SALE = 1000;

    private static final int PRODUCT_SIZE = 3;

    private static final int SALE_SIZE = 4;

    private static final Charset FILE_CHARSET = Charset.forName("UTF-8");

    private static final String SEPARATOR_REGEX = "\\|";

    /**
     * Parse input file and return data container
     * 
     * @param path
     * @return
     */
    public static FnagData parse(Path path) {

        Map<String, ProductDetail> products = new HashMap<>();
        Map<String, String> sellerLocation = new HashMap<>();
        List<SaleDetail> sales = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, FILE_CHARSET)) {
            // PRODUCTS
            int productSize = getLineNumber(reader, MAX_PRODUCT);
            for (int i = 0; i < productSize; i++) {
                String[] product = getLineElements(reader, PRODUCT_SIZE);
                String productRef = getNonBlankString(product[0]);
                long price = DecimalUtils.toCents(product[1]);
                String desc = getNonBlankString(product[2]);

                products.put(productRef, new ProductDetail(price, desc));
            }

            // SALES
            int saleSize = getLineNumber(reader, MAX_SALE);
            for (int i = 0; i < saleSize; i++) {
                String[] sale = getLineElements(reader, SALE_SIZE);
                String shop = getNonBlankString(sale[0]);
                String seller = getNonBlankString(sale[1]);
                String productRef = getNonBlankString(sale[2]);

                ProductDetail product = products.get(productRef);
                if (product == null) {
                    throw new IllegalArgumentException("Product with reference " + productRef + " was not found");
                }
                long productPrice = product.getPrice();
                int quantity = Integer.parseInt(sale[3]);
                if (sellerLocation.containsKey(seller) && !StringUtils.equals(shop, sellerLocation.get(seller))) {
                    throw new IllegalArgumentException("Seller " + seller + " cannot belong to different shops : "
                            + shop + ", " + sellerLocation.get(seller));
                }

                sellerLocation.put(seller, shop);
                sales.add(new SaleDetail(seller, productRef, quantity, productPrice * quantity));
            }
            return new FnagData(products, sellerLocation, sales);

        } catch (IOException e) {
            throw new RuntimeException("Error reading input file", e);
        }
    }

    private static String getNonBlankString(String string) {
        notBlank(string, "Field cannot be blank");
        return string;
    }

    private static String[] getLineElements(BufferedReader reader, int size) throws IOException {
        String line = reader.readLine();
        if (line == null) {
            throw new IllegalArgumentException("Line element is required");
        }
        String[] tab = line.split(SEPARATOR_REGEX, -1);
        if (tab.length != size) {
            throw new IllegalArgumentException("Bad element number, expected " + size + ", actual " + tab.length);
        }
        return tab;
    }

    private static int getLineNumber(BufferedReader reader, int max) throws IOException {
        String line = reader.readLine();
        if (line == null) {
            throw new IllegalArgumentException("Line number is required");
        }
        int number = Integer.parseInt(line);
        if (number >= max) {
            throw new IllegalArgumentException("Bad line number, max allowed " + max + ", actual " + number);
        }
        return number;
    }

}
