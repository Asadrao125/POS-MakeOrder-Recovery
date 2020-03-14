package com.example.pointofsale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Order extends AppCompatActivity {
    DatabaseReference ref;
    TextView tvName, tvprice, tvquantity;
    String name;
    Long price;
    Long quantity;
    TextView tvTotal;
    int count;
    int i;
    String shops_name;
    AutoCompleteTextView actShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView tv = findViewById(R.id.tvTitle);
        tv.setText("Make Order");

        ref = FirebaseDatabase.getInstance().getReference("Products");
        tvName = findViewById(R.id.tv);
        tvprice = findViewById(R.id.tvPrice);
        tvquantity = findViewById(R.id.quantity);
        tvTotal = findViewById(R.id.tvTotal);
        actShop = findViewById(R.id.actShop);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("name").getValue(String.class);
                price = dataSnapshot.child("price").getValue(Long.class);
                quantity = dataSnapshot.child("quantity").getValue(Long.class);

                tvName.setText(name);
                tvprice.setText("" + price);
                tvquantity.setText("" + quantity);
                tvTotal.setText("" + price * quantity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference("Areas").child("nawabshah");
        final ArrayAdapter<String> autoComplete = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = Integer.parseInt(dataSnapshot.child("counter").child("count").getValue(String.class));
                Toast.makeText(Order.this, "Value: " + count, Toast.LENGTH_SHORT).show();
                i = count;
                for (i = 1; i <= count; i++) {
                    shops_name = dataSnapshot.child("shop").child(String.valueOf(i)).child("name").getValue(String.class);
                    Toast.makeText(Order.this, "name: " + shops_name, Toast.LENGTH_SHORT).show();
                    Log.d("Loop", "onDataChange: " + shops_name);
                    autoComplete.add(shops_name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        AutoCompleteTextView ACTV = (AutoCompleteTextView) findViewById(R.id.spinnerAreas);
        ACTV.setAdapter(autoComplete);
    }
}
