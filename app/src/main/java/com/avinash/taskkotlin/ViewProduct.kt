package com.avinash.taskkotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_view_product.*
import kotlinx.android.synthetic.main.activity_view_product.cartMenu
import kotlinx.android.synthetic.main.activity_view_product.productImage
import kotlinx.android.synthetic.main.activity_view_product.product_description
import kotlinx.android.synthetic.main.activity_view_product.product_name
import kotlinx.android.synthetic.main.activity_view_product.product_price

class ViewProduct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_product)

        var name:String = intent.getStringExtra("name")
        var url:String = intent.getStringExtra("imageUrl")
        var price:String = intent.getStringExtra("price")
        var description :String = intent.getStringExtra("description")
        product_name.setText(name)
        productHeader.setText(name)
        product_price.setText(price)
        product_description.setText(description)

        Glide
            .with(this)
            .load(url)
            .centerCrop()
            .placeholder(null)
            .into(productImage)

        val back = findViewById<FrameLayout>(R.id.back)
        back.setOnClickListener(View.OnClickListener {
            this@ViewProduct.finish()
        })

        cartMenu.setOnClickListener {
            val intent = Intent(this,CartActivity::class.java)
            startActivity(intent)
        }
    }
}
