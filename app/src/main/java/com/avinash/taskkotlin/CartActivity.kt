package com.avinash.taskkotlin

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CartAdapter


    private var arrayList : ArrayList<Product> = ArrayList()
    private val databaseHandler: DatabaseHandler = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val back = findViewById<FrameLayout>(R.id.back)

        back.setOnClickListener {
            this@CartActivity.finish()
        }
        getData()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        adapter = CartAdapter(arrayList,this, object : CartAdapter.ProductInterFace{
            override fun removeItem(adapterPosition: Int) {
                removeItem(productId = arrayList[adapterPosition].getProductId().toInt())
            }

            override fun increaseQty(position: Int) {
                updateQty(
                    arrayList[position].getQuantity()+1,
                    arrayList[position].getProductId().toInt()
                )
            }

            override fun decreaseQty(position: Int) {
                val qty = arrayList[position].getQuantity()
                if(qty>1)
                    updateQty(
                        arrayList[position].getQuantity()-1,
                        arrayList[position].getProductId().toInt()
                    )
            }

        })
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    private fun getData(){
        arrayList = databaseHandler.getCartProducts()
        if(arrayList.size>0){
            no_items.visibility=View.GONE
            myCartHeader.text = "My Cart ("+arrayList.size+")"
            proceed_to_checkout.visibility=View.VISIBLE
        }else{
            no_items.visibility=View.VISIBLE
            myCartHeader.text = "My Cart"
            proceed_to_checkout.visibility=View.GONE
        }
        //adapter.notifyDataSetChanged()
    }

    private fun removeItem(productId: Int){
        val status = databaseHandler.removeItem(productId)
        if(status > -1){
            getData()
            initRecyclerView()
        }
    }

    private fun updateQty(quantity: Int, productId: Int){
        val status = databaseHandler.updateQuantity(quantity,productId)
        if(status > -1){
            getData()
            initRecyclerView()
        }
    }
}
