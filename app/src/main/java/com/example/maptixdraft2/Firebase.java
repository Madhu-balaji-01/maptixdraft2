package com.example.maptixdraft2;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
                    if (!currentItem.equals("entrance")) {
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
                    //define new object
                    ListItem newItem = new ListItem();
                    Log.i("Kewen shopping", snapshot.toString());
                    String itemName = snapshot.getKey();
                    String itemQty = snapshot.getValue().toString();
                    newItem.setItems(itemName);
                    newItem.setQuantity(itemQty);
                    listItemList.add(newItem);
                }
                callbackAction.onCallback(listItemList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

}
