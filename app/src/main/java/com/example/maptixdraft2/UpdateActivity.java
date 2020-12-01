package com.example.maptixdraft2;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    TextView itemtobeupdated;
    AutoCompleteTextView new_quantity;
    Button update_button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qtyedit);

        Intent edit_intent = getIntent();
        String item_to_be_edited = edit_intent.getStringExtra("unlock_edit");

        Intent signin_intent = getIntent();
       String signin_username = signin_intent.getStringExtra("username");
        Log.i("YX/update_intent", signin_username);

        itemtobeupdated = findViewById(R.id.update_Itemname);
        new_quantity = findViewById(R.id.new_quantity);
        update_button = findViewById(R.id.update_button);

        itemtobeupdated.setText(item_to_be_edited);

        final Firebase.booleanCallbackInterface addupdatedQuantityCallback = new Firebase.booleanCallbackInterface() {
            @Override
            public void onCallback(boolean itemExists) {
                if (itemExists) {
                    Intent edit_intent = getIntent();
                    String item_to_be_edited = edit_intent.getStringExtra("unlock_edit");
                    //Log.i("item", item_to_be_edited);
                    Log.i("item to be edited",item_to_be_edited);
                    String qtyEntered = new_quantity.getText().toString();
                    Log.i("item_qty",qtyEntered);

                    Intent signin_intent = getIntent();
                    final String signin_username = signin_intent.getStringExtra("username");
                    Log.i("YX/update_intent", signin_username);


                    if (qtyEntered.equals("")) {
                        qtyEntered = "-"; // if the user does not enter a quantity, we display a dash instead
                    }
                    ListItem newItemObject = new ListItem(item_to_be_edited, qtyEntered); //create new constructor
                    Firebase.addItem(newItemObject,signin_username); //use this method instead of push() for correct firebase format and to avoid auto adding UUID
                    Toast.makeText(UpdateActivity.this, "Item updated successfully" , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateActivity.this, "This item is not available at this supermarket!", Toast.LENGTH_LONG).show();
                }
                new_quantity.setText("");

            }
        };

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit_intent = getIntent();
                String item_to_be_edited = edit_intent.getStringExtra("unlock_edit"); //Get the values from the autotextfield
                Firebase.itemAvailability(addupdatedQuantityCallback,item_to_be_edited);
//                Intent backtohomepage = new Intent(UpdateActivity.this, Homepage.class);
//                startActivity(backtohomepage);

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
