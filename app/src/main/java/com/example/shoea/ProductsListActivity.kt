package com.example.shoea

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable


class ProductsListActivity : AppCompatActivity(), productItemClicked{

    private val PERMISSION_CODE = 102
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val productList: ArrayList<Product> = ArrayList()
    private lateinit var productsListAdapter: ProductsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_list)

        // Check Internet permission & ask if not given
        if(ActivityCompat.checkSelfPermission(this@ProductsListActivity,
                android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@ProductsListActivity,
                arrayOf(android.Manifest.permission.INTERNET), PERMISSION_CODE)
        }

        // getting data from api
        getProducts()


        progressBar = findViewById(R.id.idListProgressBar)

        // creating recyclerView
        recyclerView = findViewById(R.id.idProductsListRV)
        recyclerView.layoutManager = LinearLayoutManager(this)
        productsListAdapter = ProductsListAdapter(this)
        recyclerView.adapter = productsListAdapter

    }


    private fun getProducts() {
        val products =  ApiServices.newInstance.getData()
        products.enqueue(object: Callback<MODAL>{
            override fun onResponse(call: Call<MODAL>, response: Response<MODAL>) {
                val responseBody = response.body()!!
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                val list: List<Product> = responseBody.products
                for(res in list) {
                    val product: Product = Product(
                        res.brand,
                        res.category,
                        res.description,
                        res.discountPercentage,
                        res.id,
                        res.images,
                        res.price,
                        res.rating,
                        res.stock,
                        res.thumbnail,
                        res.title
                    )
                    productList.add(product)
                    }
                // updating products list
                productsListAdapter.updateProductList(productList)
            }

            override fun onFailure(call: Call<MODAL>, t: Throwable) {
                recyclerView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                Toast.makeText(this@ProductsListActivity, "Sever is not working, try after sometime.", Toast.LENGTH_LONG).show()
            }
        })
    }

    // clicked item interface
    override fun onProductItemClicked(item: Product) {
        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra("current_item", item)
        startActivity(intent)
    }
}