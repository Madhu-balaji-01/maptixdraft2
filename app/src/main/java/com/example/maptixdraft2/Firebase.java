package com.example.maptixdraft2;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Firebase {
    static DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();

    public static void addItem(String itemName, String itemQty, String username) {
        if (!itemName.equals("")) {
            myDatabaseRef.child("Users").child(username).child(itemName).setValue(itemQty);
            Log.i("Kewen","Item Added! "+itemName+": "+itemQty);
        }
    }

    public static void addItem(ListItem itemObject, String username) {
        String itemName = itemObject.getItems();
        String itemQty = itemObject.getQuantity();
        addItem(itemName, itemQty, username);
    }

    public static void deleteItem(String itemName, String username) {
        myDatabaseRef.child("Users").child(username).child(itemName).removeValue();
        Log.i("Kewen","Item Removed! "+itemName);
    }

    public static void deleteItem(final int itemIndex, final String username) {
        DatabaseReference userDatabaseReference = myDatabaseRef.child("Users").child(username);
        userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int itemCount = 0;
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    if (itemCount==itemIndex) {
                        String itemName = snapshot.getKey();
                        Log.i("Kewen delete",itemName+" with index "+itemIndex+" was detected to delete");
                        deleteItem(itemName, username);
                        break;
                    }
                    itemCount += 1;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void itemAvailability(final booleanCallbackInterface callbackAction, final String itemName) {
        Log.i("KEWEN","IN ITEM AVAILABILITY");
        DatabaseReference itemsDatabaseReference = myDatabaseRef.child("Items");
        itemsDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isItemFound = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){ //iterate through the category list
                    String currentItem = snapshot.getKey();
                    //Log.i("Kewen items",currentItem);
                    if (currentItem.equals(itemName)) {
                        callbackAction.onCallback(true);
                        isItemFound = true;
                    }
                }
                if (!isItemFound) {
                    callbackAction.onCallback(false);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static void displayItemSuggestions(final callbackInterface callbackAction) {
        DatabaseReference itemsDatabaseReference = myDatabaseRef.child("Items");
        itemsDatabaseReference.addValueEventListener(new ValueEventListener() {
            final ArrayList<String> itemsArraylist = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){ //iterate through the category list
                    String currentItem = snapshot.getKey();
                    if (!currentItem.equals("IN")) {
                        itemsArraylist.add(currentItem);
                    }
                }
                String[] itemsList = new String[itemsArraylist.size()];
                for (int i=0; i<itemsArraylist.size();i++){
                    itemsList[i] = itemsArraylist.get(i);
                }
                callbackAction.onCallback(itemsList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    };

    public static void displayShoppingList(final listitemCallbackInterface callbackAction, String username) {
        DatabaseReference userDatabaseReference = myDatabaseRef.child("Users").child(username);
        userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ListItem> listItemList = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    if (!snapshot.getKey().equals("Password")) {
                        //define new object
                        ListItem newItem = new ListItem();
                        Log.i("Kewen shopping", snapshot.toString());
                        String itemName = snapshot.getKey();
                        String itemQty = snapshot.getValue().toString();
                        newItem.setItems(itemName);
                        newItem.setQuantity(itemQty);
                        listItemList.add(newItem);
                    }
                }
                callbackAction.onCallback(listItemList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void signIn(final stringCallbackInterface callbackAction, final String signinUser, final String signinPassword) {
        DatabaseReference userDatabaseReference = myDatabaseRef.child("Users");
        userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isUserFound = false;
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String currentUser = snapshot.getKey();
                    if (currentUser.equals(signinUser)) {
                        isUserFound = true;
                        String correctPassword = snapshot.child("Password").getValue().toString();
                        Log.i("Kewen/signinpw", correctPassword);
                        if (correctPassword.equals(signinPassword)) {
                            // user exists, correct password
                            // add callback to go to homepage activity
                            callbackAction.onCallback("correct");
                        } else {
                            // user exists, wrong password
                            // add callback to send Toast "Incorrect password!"
                            callbackAction.onCallback("incorrect");
                        }
                        break;
                    }
                }
                if (!isUserFound) {
                    // user does not exist
                    // add callback to send Toast "User does not exist!" prompt to sign up
                    callbackAction.onCallback("signup");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void signUp(final booleanCallbackInterface callbackAction, final String signupUser, final String signupPassword) {
        final DatabaseReference userDatabaseReference = myDatabaseRef.child("Users");


        userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isUserFound = false;

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String currentUser = snapshot.getKey();
                    Log.i("Kewen/signup", currentUser );
                    if (currentUser.equals(signupUser)) {

                        Log.i("Kewen/signup","user found");
                        isUserFound = true;
                        // user exists
                        // add callback to send Toast "User exists! Sign in instead?"
                        callbackAction.onCallback(true);
                        break;
                    }
                }
                if (!isUserFound) {
                    // user does not exist
                    Log.i("Kewen/signup","user NOT found");
                    myDatabaseRef.child("Users").child(signupUser).child("Password").setValue(signupPassword);// add a node for user
                    // add callback to send Toast "User created!" and send to Homepage
                    callbackAction.onCallback(false);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getAllItemCoordinates(final ptHashmapCallbackInterface callbackAction) {
        DatabaseReference coordinatesDatabaseReference = myDatabaseRef.child("Coordinates");
        coordinatesDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            HashMap<String,pt> allItemCoordinates = new HashMap<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String itemName = snapshot.getKey();
                    float xCoordinate = (float) snapshot.child("x").getValue();
                    float yCoordinate = (float) snapshot.child("y").getValue();
                    pt itemPt = new pt(xCoordinate, yCoordinate);
                    allItemCoordinates.put(itemName, itemPt);
                }
                callbackAction.onCallback(allItemCoordinates); // create this callback in jianhui's file
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static void getIncidenceMap(final dijkstraMapperCallbackInterface callbackAction, final String username) {
        final HashMap<String, HashMap<String, Integer>> itemIncidenceMap = new HashMap<>();
        final ArrayList<String> userShoppingList = new ArrayList<>();
        final HashMap<String,pt> allItemCoordinates = new HashMap<>();
        myDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot itemsSnapshot = dataSnapshot.child("Items");
                DataSnapshot userSnapshot = dataSnapshot.child("Users").child(username);
                DataSnapshot coordinatesSnapshot = dataSnapshot.child("Coordinates");
                for (DataSnapshot currentItemsnapshot: itemsSnapshot.getChildren()) {
                    HashMap<String, Integer> currentItemMap = new HashMap<>();
                    Log.i("Kewen mapper/incidence", currentItemsnapshot.toString());
                    String itemName = currentItemsnapshot.getKey();
                    for (DataSnapshot incidenceSnapshot: currentItemsnapshot.getChildren()) {
                        currentItemMap.put(incidenceSnapshot.getKey(), (Integer) incidenceSnapshot.getValue());
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
                    }
                }
                for (DataSnapshot snapshot: coordinatesSnapshot.getChildren()) {
                    Log.i("Kewen mapper/coords", snapshot.toString());
                    String itemName = snapshot.getKey();
                    float xCoordinate = (float) snapshot.child("x").getValue();
                    float yCoordinate = (float) snapshot.child("y").getValue();
                    pt itemPt = new pt(xCoordinate, yCoordinate);
                    allItemCoordinates.put(itemName, itemPt);
                }
                callbackAction.onCallback(itemIncidenceMap, allItemCoordinates, userShoppingList); // create this callback in Matthew's file
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    interface callbackInterface { // equivalent of Bar, a nested interface
        void onCallback(String[] myList);
    }
    interface listitemCallbackInterface {
        void onCallback(List<ListItem> myList);
    }
    interface booleanCallbackInterface {
        void onCallback(boolean bool);
    }
    interface stringCallbackInterface {
        void onCallback(String string);
    }
    interface ptHashmapCallbackInterface {
        void onCallback(HashMap<String,pt> allItemCoordinates);
    }

    interface dijkstraMapperCallbackInterface { // give this to dijkstra if they pass coords to mapper
        void onCallback(HashMap<String, HashMap<String, Integer>> itemIncidenceMap, HashMap<String,pt> allItemCoordinates, ArrayList<String> userShoppingList);
    }



}
