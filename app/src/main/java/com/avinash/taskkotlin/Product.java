package com.avinash.taskkotlin;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {
    private String imageUrl, name, price, description, productId;
    private int quantity;

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

    public Product(String imageUrl, String name, String price, String description, String productId, int quantity) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.description = description;
        this.productId = productId;
        this.quantity = quantity;
    }

    private static String convertUTF8ToString(String s) {
        String out;
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
