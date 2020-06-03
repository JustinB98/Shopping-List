package behrman.justin.shoppinglist.model;

import android.util.Log;

import java.io.Serializable;
import java.util.Comparator;

public class SortingSettings implements Serializable {

    private SortingOption sortingOption;
    private boolean ascending;

    {
        sortingOption = SortingOption.DATE_ADDED;
        ascending = true;
    }

    public Comparator<ShoppingItem> getComparator() {
        if (ascending) return sortingOption.getComparator();
        return new Comparator<ShoppingItem>() {
            @Override
            public int compare(ShoppingItem si1, ShoppingItem si2) {
                return sortingOption.getComparator().compare(si2, si1);
            }
        };
    }

    public void setSortingOption(SortingOption sortingOption) {
        this.sortingOption = sortingOption;
    }

    public void setToAscending() {
        ascending = true;
    }

    public void setToDescending() {
        ascending = false;
    }

}
