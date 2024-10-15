package com.example.vamsi.login;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ListProducts extends AppCompatActivity {

    private RecyclerView recyclerViewProducts;
    private ProductsAdapter productsAdapter;
    private DatabaseHelper databaseHelper;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);

        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        productList = databaseHelper.getAllProducts();

        productsAdapter = new ProductsAdapter(this, productList);
        recyclerViewProducts.setAdapter(productsAdapter);
    }
}
