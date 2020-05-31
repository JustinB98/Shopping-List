package behrman.justin.shoppinglist.model;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ShoppingItem {

    private static final String TAG = ShoppingItem.class.getSimpleName();

    private String name, description;
    private double estimatedPrice;
    private Category category;
    private boolean purchased;

    public ShoppingItem() {}

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

    public void copy(ShoppingItem item) {
        this.name = item.name;
        this.category = item.category;
        this.purchased = item.purchased;
        this.description = item.description;
        this.estimatedPrice = item.estimatedPrice;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("category", category);
            jsonObject.put("purchased", purchased);
            jsonObject.put("description", description);
            jsonObject.put("estimatedPrice", estimatedPrice);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return jsonObject;
    }

    public static ShoppingItem fromJSONObject(JSONObject jsonObject) {
        try {
            String name = jsonObject.getString("name");
            Category category = Category.valueOf(jsonObject.getString("category"));
            boolean purchased = jsonObject.getBoolean("purchased");
            String description = jsonObject.getString("description");
            double estimatedPrice = jsonObject.getDouble("estimatedPrice");
            return new ShoppingItem(name, description, estimatedPrice, category, purchased);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            return new ShoppingItem();
        }
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
