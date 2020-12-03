package com.example.maptixdraft2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

        ListItem testtest = new ListItem("Fried Rice", "4");
        assertEquals("Fried Rice", testtest.getItems());
    }

    @Test
    public void ListQuantityTest() {

        ListItem testtest = new ListItem("Fried Rice", "4");
        assertEquals("4", testtest.getQuantity());
    }



    @Test
    public void Vertext() {
            Vertex newvertex = new Vertex("1", "Beauty");
            assertEquals("1",newvertex.getId());
            assertEquals("Autotest",newvertex.getName());

    }




//    public class Edge  {
//        private final String id;
//        private final Vertex source;
//        private final Vertex destination;
//        private final int weight;
//
//        //An edge must have a source and destination as well as a weight
//        public Edge(String id, Vertex source, Vertex destination, int weight) {
//            this.id = id;
//            this.source = source;
//            this.destination = destination;
//            this.weight = weight;
//        }
//
//        public String getId() {
//            return id;
//        }
//        public Vertex getDestination() {
//            return destination;
//        }
//
//        public Vertex getSource() {
//            return source;
//        }
//        public int getWeight() {
//            return weight;
//        }
//
//        @Override
//        public String toString() {
//            return source + " " + destination;
//        }


    }

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





