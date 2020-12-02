package com.example.maptixdraft2;


public class ListItem {

    String items , quantity;
    public ListItem(){

    }

    public ListItem(String items, String quantity) {
        this.items = items;
        this.quantity = quantity;
    }


    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
