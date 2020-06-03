package behrman.justin.shoppinglist.model;

import android.util.Log;

import java.io.Serializable;
import java.util.Comparator;

public class SortingSettings implements Serializable {

    private SortingOption sortingOption;
    private int ascending;

    {
        sortingOption = SortingOption.NO_SORT;
        ascending = 0;
    }

    public Comparator<ShoppingItem> getComparator() {
        if (ascending != 0) return sortingOption.getComparator();
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
        ascending = 1;
    }

    public void setToDescending() {
        ascending = 0;
    }

    public int getAscending() {
        return ascending;
    }

    public boolean noSort() {
        return sortingOption == SortingOption.NO_SORT;
    }

}
