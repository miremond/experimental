package org.miremond.fnag;

import java.util.List;
import java.util.Map;

/**
 * Data container extracted from the file. The prices are stored as long integer expressing an amount of cents.
 * 
 * @author mremond
 *
 */
public class FnagData {

    private Map<String, ProductDetail> products;
    private Map<String, String> sellerLocation;
    private List<SaleDetail> sales;

    public FnagData(Map<String, ProductDetail> products, Map<String, String> sellerLocation, List<SaleDetail> sales) {
        this.products = products;
        this.sellerLocation = sellerLocation;
        this.sales = sales;
    }

    public ProductDetail getProductDetail(String ref) {
        return products.get(ref);
    }

    public String getSellerLocation(String seller) {
        return sellerLocation.get(seller);
    }

    public List<SaleDetail> getSales() {
        return sales;
    }

    /**
     * Product details.
     * 
     * @author mremond
     *
     */
    public static class ProductDetail {

        private long price;
        private String description;

        public ProductDetail(long price, String description) {
            super();
            this.price = price;
            this.description = description;
        }

        public long getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((description == null) ? 0 : description.hashCode());
            result = prime * result + (int) (price ^ (price >>> 32));
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ProductDetail other = (ProductDetail) obj;
            if (description == null) {
                if (other.description != null)
                    return false;
            } else if (!description.equals(other.description))
                return false;
            if (price != other.price)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "ProductDetail [price=" + price + ", description=" + description + "]";
        }

    }

    /**
     * Sale details.
     * 
     * @author mremond
     *
     */
    public static class SaleDetail {

        private String seller;
        private String productReference;
        private long income;
        private int quantity;

        public SaleDetail(String seller, String productReference, int quantity, long income) {
            super();
            this.seller = seller;
            this.productReference = productReference;
            this.quantity = quantity;
            this.income = income;
        }

        public String getSeller() {
            return seller;
        }

        public String getProductReference() {
            return productReference;
        }

        public long getIncome() {
            return income;
        }

        public int getQuantity() {
            return quantity;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (income ^ (income >>> 32));
            result = prime * result + ((productReference == null) ? 0 : productReference.hashCode());
            result = prime * result + quantity;
            result = prime * result + ((seller == null) ? 0 : seller.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            SaleDetail other = (SaleDetail) obj;
            if (income != other.income)
                return false;
            if (productReference == null) {
                if (other.productReference != null)
                    return false;
            } else if (!productReference.equals(other.productReference))
                return false;
            if (quantity != other.quantity)
                return false;
            if (seller == null) {
                if (other.seller != null)
                    return false;
            } else if (!seller.equals(other.seller))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "SaleDetail [seller=" + seller + ", productReference=" + productReference + ", income=" + income
                    + ", quantity=" + quantity + "]";
        }

    }

}
