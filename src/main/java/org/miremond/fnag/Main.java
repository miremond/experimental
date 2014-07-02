package org.miremond.fnag;

import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Program displaying the top sale product and the top sellers from the F.N.A.G. company report file.
 * 
 * @author mremond
 * 
 */
public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Please give a file as input");
        }
        FnagData data = FileParser.parse(Paths.get(args[0]));

        Stream<String> topSale = StatsService.getTopSales(data);
        Stream<String> topSeller = StatsService.getTopSellers(data);

        topSale.forEach(s -> System.out.println(s));
        topSeller.forEach(s -> System.out.println(s));
    }
}
