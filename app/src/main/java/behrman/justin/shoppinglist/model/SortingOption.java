package behrman.justin.shoppinglist.model;

import androidx.annotation.NonNull;

import java.util.Comparator;

public enum SortingOption {

    NO_SORT(new Comparator<ShoppingItem>() {
        @Override
        public int compare(ShoppingItem shoppingItem, ShoppingItem t1) {
            return 0;
        }
    }),
    NAME(new Comparator<ShoppingItem>() {
        @Override
        public int compare(ShoppingItem si1, ShoppingItem si2) {
            return si1.getName().compareToIgnoreCase(si2.getName());
        }
    }),
    PRICE(new Comparator<ShoppingItem>() {
        @Override
        public int compare(ShoppingItem si1, ShoppingItem si2) {
            return Integer.compare(si1.getEstimatedPrice(), si2.getEstimatedPrice());
        }
    }),
    PURCHASE_STATUS(new Comparator<ShoppingItem>() {
        @Override
        public int compare(ShoppingItem si1, ShoppingItem si2) {
            boolean firstPurchased = si1.isPurchased();
            boolean secondPurchased = si2.isPurchased();
            if (firstPurchased && !secondPurchased) {
                return 1;
            } else if (!firstPurchased && secondPurchased) {
                return -1;
            } else {
                return 0;
            }
        }
    });

    private Comparator<ShoppingItem> comparator;

    SortingOption(Comparator<ShoppingItem> comparator) {
        this.comparator = comparator;
    }

    public Comparator<ShoppingItem> getComparator() {
        return comparator;
    }

    @NonNull
    @Override
    public String toString() {
        String name = name();
        return name.charAt(0) + name.substring(1).toLowerCase().replace('_', ' ');
    }
}
