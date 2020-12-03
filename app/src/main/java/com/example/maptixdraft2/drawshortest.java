package com.example.maptixdraft2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class drawshortest extends AppCompatActivity {

    LinkedHashMap<String, pt> coordinates;      // meant to take in shortest path in coordinates
    ArrayList<String> buylist = new ArrayList<>();                      // list for shopping cart
    int scrWidth, scrHeight;                                            // screen height & width



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO: same as the below, putting entries into the LHM, but with calling firebase
        // so some sort of for-loop going through the arraylist of vertices (path) to draw and insert

//        // testing code
//        coordinates.put("in", new pt(4, 15));
//        coordinates.put("Beauty", new pt(0,4));
//        coordinates.put("Stationery", new pt(0, 13));
//        coordinates.put("Milk", new pt(12,14));
//        coordinates.put("Juice", new pt(12,8));
//        coordinates.put("Pasta", new pt(5,0));
//
//        buylist.add("Beauty");
//        buylist.add("Milk");
//        buylist.add("Pasta");
        super.onCreate(savedInstanceState);


        DatabaseReference shortestDatabaseRef = FirebaseDatabase.getInstance().getReference();
        shortestDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            final HashMap<String, HashMap<String, Integer>> itemIncidenceMap = new HashMap<>();
            final ArrayList<String> userShoppingList = new ArrayList<>();
            final HashMap<String,pt> allItemCoordinates = new HashMap<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent draw_path_username = getIntent();
                String username = draw_path_username.getStringExtra("username");
                Log.i("YX/update_intent", username);

                DataSnapshot itemsSnapshot = dataSnapshot.child("Items");
                DataSnapshot userSnapshot = dataSnapshot.child("Users").child(username);
                DataSnapshot coordinatesSnapshot = dataSnapshot.child("Coordinates");
                for (DataSnapshot currentItemsnapshot: itemsSnapshot.getChildren()) {
                    HashMap<String, Integer> currentItemMap = new HashMap<>(); //currentitemlist definition
                    Log.i("Kewen mapper/incidence", currentItemsnapshot.toString());
                    String itemName = currentItemsnapshot.getKey();
                    for (DataSnapshot incidenceSnapshot: currentItemsnapshot.getChildren()) {
                        Log.i("incidenceSnapshot", incidenceSnapshot.toString());
                        currentItemMap.put(incidenceSnapshot.getKey(), Integer.parseInt(incidenceSnapshot.getValue().toString()));

                    }
                    Log.i("Kewen mapper/incidence","CurrentItemMap: "+currentItemMap);
                    itemIncidenceMap.put(itemName,currentItemMap);
                }
                Log.i("Kewen mapper/incidence","Final itemIncidenceMap: "+itemIncidenceMap);
                for (DataSnapshot snapshot: userSnapshot.getChildren()) {
                    if (!snapshot.getKey().equals("Password")) {
                        Log.i("Kewen dijkstra/shoplist", snapshot.toString());
                        String itemName = snapshot.getKey();
                        userShoppingList.add(itemName);
                        buylist.add(itemName);
                        Log.i("Kewen dijkstra/itemname", userShoppingList.toString());
                    }
                }

                for (DataSnapshot snapshot: coordinatesSnapshot.getChildren()) {
                    Log.i("Kewen mapper/coords", snapshot.toString());
                    String itemName = snapshot.getKey();
                    float xCoordinate = Float.parseFloat(snapshot.child("x").getValue().toString()) ;
                    float yCoordinate =  Float.parseFloat(snapshot.child("y").getValue().toString());
                    pt itemPt = new pt(xCoordinate, yCoordinate);
                    allItemCoordinates.put(itemName, itemPt);
                }

                coordinates =com.example.maptixdraft2.Path.testExecute(itemIncidenceMap,allItemCoordinates,userShoppingList); //return a linkedhashmap
                Log.i("mapcoordinates", coordinates.toString());
                Log.i("mapitem", buylist.toString());
                setContentView(new MapView(drawshortest.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public class MapView extends View {
        MapView(Context context) {
            super(context);
        }

        // called when view is assigned a size
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            // find screen height and screen width
            scrHeight = h - getPaddingBottom() - getPaddingTop();
            scrWidth = w - getPaddingLeft() - getPaddingRight();
        }


        @Override
        protected void onDraw(Canvas mapcanv) {
            super.onDraw(mapcanv);

            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inMutable = true;                   // drawBitmap needs mutable bitmap
            Bitmap mapBM = BitmapFactory.decodeResource(getResources(), R.drawable.map, opt);           // obtain map image to be used as bitmap
            Canvas canv = new Canvas(mapBM);        // initialise new canvas to draw onto this image bitmap

            // properties for the path to be drawn
            Paint myPaint = new Paint();
            myPaint.setColor(Color.RED);
            myPaint.setStrokeWidth(15);
            myPaint.setStyle(Paint.Style.STROKE);

            // concatenating the coordinates to a path
            Path myPath = new Path();
            pt start = new pt(((coordinates.get("IN").x)*112+200)*3, ((coordinates.get("IN").y)*112+200)*3);  // find entrance; note the scaling factors
            coordinates.remove("IN");               // remove entrance since it is the source vertex
            myPath.moveTo(start.x, start.y);

            // for each subsequent vertex in the shortest path found,
            for (String item : coordinates.keySet()) {
                // find their coordinates,
                pt currentPt = new pt((coordinates.get(item).x*112+200)*3, (coordinates.get(item).y*112+200)*3);
                // then draw a line to it
                myPath.lineTo(currentPt.x, currentPt.y);

                // however this vertex may be an intermediate vertex (i.e. nothing is to be bought from it)
                // so, double check it with the buyer's shopping cart
                if (buylist.contains(item)) {
                    // circle is used to denote that a stop is to be made at the shelf (because the item is found there)
                    myPath.addCircle(currentPt.x, currentPt.y, 50, Path.Direction.CCW);
                }
            }
            // the drawing of the path
            canv.drawPath(myPath, myPaint);
            // finally the drawing of this bitmap onto the main canvas
            mapcanv.drawBitmap(Bitmap.createScaledBitmap(mapBM, scrWidth, scrHeight, false), 0, 0, null);

        }
    }


}

