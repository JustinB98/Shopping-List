package behrman.justin.shoppinglist.model;

import java.util.Comparator;

public class SortingSettings {

    private static SortingOption sortingOption;
    private static int ascending;

    static {
        sortingOption = SortingOption.NO_SORT;
        ascending = 0;
    }

    public static Comparator<ShoppingItem> getComparator() {
        if (ascending != 0) return sortingOption.getComparator();
        return new Comparator<ShoppingItem>() {
            @Override
            public int compare(ShoppingItem si1, ShoppingItem si2) {
                return sortingOption.getComparator().compare(si2, si1);
            }
        };
    }

    public static void setSortingOption(SortingOption sortingOption) {
        SortingSettings.sortingOption = sortingOption;
    }

    public static void setToAscending() {
        ascending = 1;
    }

    public static void setToDescending() {
        ascending = 0;
    }

}
