package com.example.shoea

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
class ProductActivity : AppCompatActivity() {

    private var quantity:Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        // getting object of product clicked by user
        val item: Product = intent.getSerializableExtra("current_item") as Product

        // creating variable for views
        val titleTV: TextView = findViewById(R.id.idTitleText)
        val imageIV: ImageView = findViewById(R.id.idTitleImage)
        val descTV: TextView = findViewById(R.id.idDesc)
        val quantityTV: TextView = findViewById(R.id.idtotalQuantity)
        val priceTV: TextView = findViewById(R.id.idProductPrice)
        val plusBtn: Button = findViewById(R.id.idPlusBtn)
        val minusBtn: Button = findViewById(R.id.idMinusBtn)

        // initializing required views
        titleTV.text = item.title
        Glide.with(applicationContext).load(item.thumbnail).into(imageIV)
        descTV.text = item.description
        quantityTV.text = quantity.toString()
        priceTV.text = "$"+item.price.toString()+".00"

        // increasing quantity of products
        plusBtn.setOnClickListener {
            quantity+=1
            quantityTV.text = quantity.toString()
        }

        // decreasing quantity of products
        minusBtn.setOnClickListener {
            if(quantity > 1){
                quantity-=1
                quantityTV.text = quantity.toString()
            }else{
                Toast.makeText(this, "Quantity cannot be zero to purchase.", Toast.LENGTH_SHORT).show()
            }
        }

    }

}