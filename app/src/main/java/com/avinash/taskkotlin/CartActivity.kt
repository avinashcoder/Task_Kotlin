package com.avinash.taskkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val back = findViewById<FrameLayout>(R.id.back)

        back.setOnClickListener(View.OnClickListener {
            this@CartActivity.finish()
        })
    }
}
