package com.example.maptixdraft2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Homepage extends AppCompatActivity  {
    RecyclerView recyclerView;
    Button add_new_item;
    Button generate_map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        add_new_item = findViewById(R.id.add_new_items_button);
        generate_map = findViewById(R.id.generatemap_button);


            recyclerView = findViewById(R.id.grocery_list_recyclerview);
            recyclerView.hasFixedSize();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));



        Firebase.listitemCallbackInterface displayShoppingListCallback = new Firebase.listitemCallbackInterface() {
            @Override
            public void onCallback(final List<ListItem> myList) {
                Log.i("Kewen","inside displayShoppingListCallback, myList received is "+ myList);
                final TableAdapter tableAdapter = new TableAdapter(Homepage.this, myList);
                recyclerView.setAdapter(tableAdapter);


                Log.i("Kewen","tableAdapter set!");

                //for deleting entries

                ItemTouchHelper.SimpleCallback item_delete = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                        final int position = viewHolder.getLayoutPosition(); //gets index of the viewholder being swiped

                        switch (direction) {
                            case ItemTouchHelper.LEFT:

                                final String deleteditem = myList.get(position).getItems();
                                final String deletedquantity = myList.get(position).getQuantity();
                                Log.i("Test for item", deleteditem );
                                myList.remove(viewHolder.getAdapterPosition()); //get position of the user list
                                tableAdapter.notifyDataSetChanged();

                                Log.i("Kewen delete","LayoutPosition is "+ position);
                                Firebase.deleteItem(deleteditem, "Kewen"); //delete item based on index
                                Snackbar.make(recyclerView, deleteditem + " deleted",Snackbar.LENGTH_LONG).show();
                                //to undo the delete (implement if time permits
//                                Snackbar.make(recyclerView, deleteditem ,Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Firebase.addItem(deleteditem,deleteditem,"Kewen");
//                                        Log.i("FB","Add deleted item to FB");
//                                        tableAdapter.notifyItemInserted(position);
//                                    }
//                                }).show();

                                break;

                                case ItemTouchHelper.RIGHT:
                                    String editeditem = myList.get(position).getItems();
                                    Intent edit_intent = new Intent(Homepage.this, UpdateActivity.class);
                                    edit_intent.putExtra("unlock_edit",editeditem);
                                    startActivity(edit_intent);
                                    break;

                        }
                    }

                    @Override
                    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                                .addSwipeLeftBackgroundColor(ContextCompat.getColor(Homepage.this, R.color.red))
                                .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                                .addSwipeLeftLabel("Delete")
                                .addSwipeRightBackgroundColor(ContextCompat.getColor(Homepage.this, R.color.turquoise))
                                .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                                .addSwipeRightLabel("Edit Quantity")
                                .create()
                                .decorate();

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };
                //to remove from the recycler view the item swiped
                new ItemTouchHelper(item_delete).attachToRecyclerView(recyclerView);


            }
        };

        Firebase.displayShoppingList(displayShoppingListCallback,"Kewen"); //to trigger recyclerView to display user's shopping list retrieved from firebase

        //to add new item
        add_new_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bring user to new activity upon clicking the add_new_item
                Intent intent = new Intent(Homepage.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }





    @Override
    protected void onPause() {
        super.onPause();
        Log.i("TAG", "Log pause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}