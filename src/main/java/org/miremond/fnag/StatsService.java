package org.miremond.fnag;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.miremond.fnag.FnagData.SaleDetail;

/**
 * Service returning the top sold products and the top sellers.
 * 
 * @author mremond
 *
 */
public class StatsService {

    private static final String DELIMITER = "|";
    private static final String TOPSALE_PREFIX = "TOPSALE";
    private static final String TOPSELLER = "TOPSELLER";

    /**
     * Return a stream containing all top sales.
     * 
     * @param data
     * @return
     */
    public static Stream<String> getTopSales(FnagData data) {

        if (data.getSales().isEmpty()) {
            return Stream.empty();
        }
        Map<String, Integer> totalByProduct = data
                .getSales()
                .stream()
                .collect(
                        Collectors.groupingBy(SaleDetail::getProductReference,
                                Collectors.summingInt(SaleDetail::getQuantity)));
        Integer max = totalByProduct.values().stream().max(Comparator.naturalOrder()).get();

        Stream<String> output = totalByProduct.entrySet().stream().filter(e -> e.getValue().equals(max))
                .map(e -> topSalesMapper(e, data));

        return output;
    }

    /**
     * Return a stream containing all top sellers.
     * 
     * @param data
     * @return
     */
    public static Stream<String> getTopSellers(FnagData data) {

        if (data.getSales().isEmpty()) {
            return Stream.empty();
        }
        Map<String, Long> totalBySeller = data.getSales().stream()
                .collect(Collectors.groupingBy(SaleDetail::getSeller, Collectors.summingLong(SaleDetail::getIncome)));
        Long max = totalBySeller.values().stream().max(Comparator.naturalOrder()).get();

        Stream<String> output = totalBySeller.entrySet().stream().filter(e -> e.getValue().equals(max))
                .map(e -> topSellersMapper(e, data));

        return output;
    }

    private static String topSalesMapper(Entry<String, Integer> e, FnagData data) {
        StringJoiner joiner = new StringJoiner(DELIMITER);
        joiner.add(TOPSALE_PREFIX).add(e.getKey()).add(data.getProductDetail(e.getKey()).getDescription())
                .add(String.valueOf(e.getValue()));
        return joiner.toString();
    }

    private static String topSellersMapper(Entry<String, Long> e, FnagData data) {
        StringJoiner joiner = new StringJoiner(DELIMITER);
        joiner.add(TOPSELLER).add(data.getSellerLocation(e.getKey())).add(e.getKey())
                .add(DecimalUtils.toString(e.getValue()));
        return joiner.toString();
    }

}
