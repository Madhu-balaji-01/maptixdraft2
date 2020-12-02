package com.example.maptixdraft2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class AddActivity extends AppCompatActivity {
    AutoCompleteTextView items_atv,quantity_atv;
    Button add_button;
    String tag = "TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(tag, "Log Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

//        Intent signin_intent = getIntent();
//        final String signin_username = signin_intent.getStringExtra("username");
//        Log.i("YX/add_intent", signin_username);
//        Log.i("KEWEN","CALLED");

        items_atv = findViewById(R.id.Itemname);
        quantity_atv = findViewById(R.id.quantity);
        add_button = findViewById(R.id.add_button);



        final Firebase.booleanCallbackInterface addItemCallback = new Firebase.booleanCallbackInterface() {
            @Override
            public void onCallback(boolean itemExists) {

                Intent signin_intent = getIntent();
                final String signin_username = signin_intent.getStringExtra("username");
                Log.i("YX/add_intent", signin_username);
                Log.i("KEWEN","CALLED");

                if (itemExists) {
                    String itemEntered = items_atv.getText().toString(); //Get the values from the autotextfield
                    String qtyEntered = quantity_atv.getText().toString();
                    if (qtyEntered.equals("")) {
                        qtyEntered = "-"; // if the user does not enter a quantity, we display a dash instead
                    }
                    ListItem newItemObject = new ListItem(itemEntered, qtyEntered); //create new constructor
                    Firebase.addItem(newItemObject,signin_username); //use this method instead of push() for correct firebase format and to avoid auto adding UUID
                    Toast.makeText(AddActivity.this, "Item added successfully" , Toast.LENGTH_SHORT).show();
                    Log.i("KEWEN","EXISTS");
                    items_atv.setText("");
                    quantity_atv.setText("");
                } else {
                    Toast.makeText(AddActivity.this, "This item is not available at this supermarket!", Toast.LENGTH_LONG).show();
                    Log.i("KEWEN","NOT EXISTS");
                }
            }
        };

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemEntered = items_atv.getText().toString(); //Get the values from the autotextfield
                if (itemEntered.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please enter an item name", Toast.LENGTH_LONG).show();
                } else {
                    Firebase.itemAvailability(addItemCallback,itemEntered);
                }

            }
        });

        Firebase.callbackInterface itemsSuggestionsCallback = new Firebase.callbackInterface() {
            @Override
            public void onCallback(String[] myList) {
                Log.i("Kewen","inside myCallback, myList received is "+ Arrays.toString(myList));
                final ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(AddActivity.this, R.layout.support_simple_spinner_dropdown_item, myList);
                items_atv.setAdapter(itemAdapter);
                Log.i("Kewen","itemAdapter set!");
            }
        };
        Firebase.displayItemSuggestions(itemsSuggestionsCallback); //to trigger items_atv to display suggested items retrieved from firebase

    }
}


