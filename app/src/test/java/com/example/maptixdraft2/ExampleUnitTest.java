package com.example.maptixdraft2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void ListItemTest() {

        ListItem testtest = new ListItem("Fried Rice" ,"4");
        assertEquals("Fried Rice", testtest.getItems());}

    @Test
    public void ListQuantityTest() {

        ListItem testtest = new ListItem("Fried Rice" ,"4");
        assertEquals("4", testtest.getQuantity());}


//        public ListItem(){
//
//        }
//
//    public ListItem(String items, String quantity) {
//            this.items = items;
//            this.quantity = quantity;
//        }
//
//
//        public String getItems() {
//            return items;
//        }
//
//        public void setItems(String items) {
//            this.items = items;
//        }
//
//        public String getQuantity() {
//            return quantity;
//        }
//
//        public void setQuantity(String quantity) {
//            this.quantity = quantity;
//        }





}