package behrman.justin.shoppinglist.model;

import android.util.Log;

public class ShoppingItem {

    private static final String TAG = ShoppingItem.class.getSimpleName();

    private long id;
    private String name, description;
    private int estimatedPrice;
    private Category category;
    private boolean purchased, hideDetails;

    {
        id = -1;
    }

    public ShoppingItem() {}

    public ShoppingItem(String name, String description, int estimatedPrice, Category category, boolean purchased) {
        this.name = name;
        this.description = description;
        this.estimatedPrice = estimatedPrice;
        this.category = category;
        this.purchased = purchased;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(int estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public boolean shouldHideDetails() {
        return hideDetails;
    }

    public void setHideDetails(boolean hideDetails) {
        this.hideDetails = hideDetails;
    }

    public void copy(ShoppingItem item) {
        this.name = item.name;
        this.category = item.category;
        this.purchased = item.purchased;
        this.description = item.description;
        this.estimatedPrice = item.estimatedPrice;
    }

    @Override
    public String toString() {
        return "ShoppingItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", estimatedPrice=" + estimatedPrice +
                ", category=" + category +
                ", purchased=" + purchased +
                '}';
    }
}
