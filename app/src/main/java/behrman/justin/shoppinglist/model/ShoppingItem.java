package behrman.justin.shoppinglist.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ShoppingItem {

    private String name, description;
    private double estimatedPrice;
    private Category category;
    private boolean purchased;

    public ShoppingItem(String name, String description, double estimatedPrice, Category category, boolean purchased) {
        this.name = name;
        this.description = description;
        this.estimatedPrice = estimatedPrice;
        this.category = category;
        this.purchased = purchased;
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

    public double getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(double estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    @Override
    public String toString() {
        return "ShoppingItem{" +
                "category=" + category +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", estimatedPrice=" + estimatedPrice +
                ", purchased=" + purchased +
                '}';
    }
}
