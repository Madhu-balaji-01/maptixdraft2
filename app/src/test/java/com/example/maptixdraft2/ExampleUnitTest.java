package com.example.maptixdraft2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
            assertEquals("Beauty",newvertex.getName());

    }

    @Test
    public void Dijkstra() {
        HashMap<String, HashMap<String, Integer>> testIncidenceMap = new HashMap<>();
        HashMap<String, pt> coordinates = new HashMap<>();

        HashMap<String, Integer> entranceAdjList = new HashMap<>(); // entrance node only goes to choc and chips
        entranceAdjList.put("choc",7);
        entranceAdjList.put("chips",5);
        testIncidenceMap.put("in", entranceAdjList); // in is the name of the node representing the entrance

        HashMap<String, Integer> chocAdjList = new HashMap<>(); // choc node only goes to entrance, milk and chips
        chocAdjList.put("in",7);
        chocAdjList.put("chips",2);
        chocAdjList.put("milk",4);
        testIncidenceMap.put("choc", chocAdjList);

        HashMap<String, Integer> chipAdjList = new HashMap<>(); // milk node only goes to choc
        chipAdjList.put("in",5);
        chipAdjList.put("choc", 2);
        testIncidenceMap.put("chips", chipAdjList);

        HashMap<String, Integer> milkAdjList = new HashMap<>(); // milk node only goes to choc
        milkAdjList.put("choc",4);
        testIncidenceMap.put("milk", milkAdjList);

        ArrayList<String> lookupItemArray = new ArrayList<>();
        lookupItemArray.add("in");
        lookupItemArray.add("choc");
        lookupItemArray.add("chips");
        lookupItemArray.add("milk");

        coordinates.put("in", new pt(4, 15));
        coordinates.put("beauty", new pt(0,4));
        coordinates.put("choc", new pt(0, 13));
        coordinates.put("milk", new pt(12,14));
        coordinates.put("juice", new pt(12,8));
        coordinates.put("chips", new pt(5,0));

        LinkedHashMap<String,pt> orderedPoints = new LinkedHashMap<>();
        orderedPoints.put("in", new pt(4, 15));
        orderedPoints.put("chips", new pt(5,0));
        orderedPoints.put("choc", new pt(0, 13));
        orderedPoints.put("milk", new pt(12,14));


        assertEquals(orderedPoints,Path.testExecute(testIncidenceMap, coordinates, lookupItemArray));
    }

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





