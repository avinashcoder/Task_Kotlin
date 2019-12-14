package com.avinash.taskkotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_view_product.*
import kotlinx.android.synthetic.main.activity_view_product.cartMenu
import kotlinx.android.synthetic.main.activity_view_product.productImage
import kotlinx.android.synthetic.main.activity_view_product.product_description
import kotlinx.android.synthetic.main.activity_view_product.product_name
import kotlinx.android.synthetic.main.activity_view_product.product_price

class ViewProduct : AppCompatActivity() {

    val databaseHandler: DatabaseHandler = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_product)

        val name: String = intent.getStringExtra("name")
        val url: String = intent.getStringExtra("imageUrl")
        val price: String = intent.getStringExtra("price")
        val description: String = intent.getStringExtra("description")
        val id: Int = intent.getStringExtra("id").toInt()
        product_name.text = name
        productHeader.text = name
        product_price.text = price
        product_description.text = description

        Glide
            .with(this)
            .load(url)
            .centerCrop()
            .placeholder(null)
            .into(productImage)

        val back = findViewById<FrameLayout>(R.id.back)
        back.setOnClickListener {
            this@ViewProduct.finish()
        }

        cartMenu.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        addToCart.setOnClickListener {
            val status = databaseHandler.addProduct(CartModelClass(id,
                name,
                url,
                description,
                price,
                1))
            if(status > -1) {
                Toast.makeText(applicationContext, "Item added in the cart", Toast.LENGTH_SHORT).show()
                countTotalItem()
            }else{
                Toast.makeText(applicationContext, "Item already available in cart", Toast.LENGTH_SHORT).show()
            }
        }

        buy_now.setOnClickListener {
            val status = databaseHandler.addProduct(CartModelClass(id,
                name,
                url,
                description,
                price,
                1))
            if(status > -1) {
                Toast.makeText(applicationContext, "Item added in the cart", Toast.LENGTH_SHORT).show()
                countTotalItem()
            }
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        countTotalItem()
    }

    private fun countTotalItem() {
        val itemcount: Int = databaseHandler.countTotalItem()
        if(itemcount>0){
            cart_item_count.text = itemcount.toString()
            cart_item_count.visibility = View.VISIBLE
        }else{
            cart_item_count.visibility = View.GONE
        }

    }
}
