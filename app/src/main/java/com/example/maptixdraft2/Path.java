package com.example.maptixdraft2;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Path {

    private static List<Vertex> nodes;
    private static List<Edge> edges;

//    public static void callThis(String username) {
//        Firebase.getIncidenceMap(getFromFirebaseCallback,username);
//    }

    public static LinkedHashMap<String,pt> testExecute(HashMap<String, HashMap<String, Integer>> testAdjacencyMap, HashMap<String, pt> allItemCoordinates, ArrayList<String> lookupItemArray) { //LinkedList<Vertex>


        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();

        ArrayList<String> locs = new ArrayList<String>();

        // Add vertices for locations
        for (String v: testAdjacencyMap.keySet()) {
            Vertex location = new Vertex(v, v);
            nodes.add(location);
            locs.add(v);
        }

        // Add edges between vertices
        for (String source: testAdjacencyMap.keySet()) {
            HashMap<String, Integer> h = testAdjacencyMap.get(source);
            for (String dest: h.keySet()) {
                addLane(source + "_" + dest, locs.indexOf(source), locs.indexOf(dest), h.get(dest));
            }
        }

        Graph graph = new Graph(nodes, edges);
        DijkstraAlgo dijkstra = new DijkstraAlgo(graph);
        LinkedList<Vertex> path = new LinkedList<>();

        String source = "IN";
        //Log.i("Path/Source", source);
        String dest = "IN";
        path.add(nodes.get(locs.indexOf(source)));
        //Log.i("Path/Destination",dest);
        //Log.i("lookuparray", lookupItemArray.toString());

        // Find shortest path for each item in each location
        while (lookupItemArray.size() > 1) {
            int shortest_dist = Integer.MAX_VALUE;
            dijkstra.findPath(nodes.get(locs.indexOf(source)));
            lookupItemArray.remove(source);

            for (String item: lookupItemArray) {
                int dist = dijkstra.getShortestDistance(nodes.get(locs.indexOf(item)));
                if (shortest_dist > dist) {
                    shortest_dist = dist;
                    dest = item;
                }
            }

            LinkedList<Vertex> subpath = dijkstra.returnPath(nodes.get(locs.indexOf(dest)));
            subpath.remove();
            path.addAll(subpath);

            source = dest;
        }

//        for (Vertex vertex : path) {
//            System.out.println(vertex);
//        }


        // putting of shortest path nodes into LHM
        LinkedHashMap<String, pt> shortpath = new LinkedHashMap<>();

        for (Vertex v : path) {
            //Log.i("shortpath", v.toString());
            shortpath.put(v.getName(), allItemCoordinates.get(v.getName()));

        }

        //Log.i("shortpath", shortpath.toString());

        return shortpath;
    }

    private static void addLane(String laneId, int sourceLocNo, int destLocNo,
                                int duration) {
        Edge lane = new Edge(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
        edges.add(lane);
    }

//    final Firebase.dijkstraMapperCallbackInterface getFromFirebaseCallback = new Firebase.dijkstraMapperCallbackInterface() {
//        @Override
//        public void onCallback(HashMap<String, HashMap<String, Integer>> itemIncidenceMap, HashMap<String, pt> allItemCoordinates, ArrayList<String> userShoppingList) {
//            LinkedHashMap<String, pt> orderedList = testExecute(itemIncidenceMap,allItemCoordinates,userShoppingList);
//
//
//        }
//    };

}
