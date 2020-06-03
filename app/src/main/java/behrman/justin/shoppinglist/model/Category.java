package behrman.justin.shoppinglist.model;

import behrman.justin.shoppinglist.R;

public enum Category {
    FOOD(R.drawable.baseline_local_dining_24),
    ELECTRONIC(R.drawable.baseline_devices_24),
    BOOK(R.drawable.baseline_menu_book_24),
    OTHER(R.drawable.baseline_shopping_bag_24);

    private int imageResId;

    Category(int imageResId) {
        this.imageResId = imageResId;
    }

    public int getImageResId() {
        return imageResId;
    }

    @Override
    public String toString() {
        String name = this.name();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }

}
