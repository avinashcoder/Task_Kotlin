package com.avinash.taskkotlin;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {
    String imageUrl, name, price, description, productId;
    int quantity;

    public Product(JSONObject jsonObject) {
        try {
            this.imageUrl = jsonObject.getString("image");
            this.name = jsonObject.getString("name");
            this.price = convertUTF8ToString(jsonObject.getString("price"));
            this.quantity = jsonObject.getInt("quantity");
            this.description = jsonObject.getString("description");
            this.productId = jsonObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String convertUTF8ToString(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
