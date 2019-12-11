package com.avinash.taskkotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: GridLayoutManager
    private lateinit var adapter: ProductListAdapter


    private var arrayList : ArrayList<Product> = ArrayList()
    private var url : String = "http://www.mocky.io/v2/5def7b172f000063008e0aa2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        progresBar.visibility = View.VISIBLE
        initRecyclerView()
        getDataFromAPI()
    }

    private fun initRecyclerView() {
        linearLayoutManager = GridLayoutManager(this,2)
        recyclerView.layoutManager = linearLayoutManager
        adapter = ProductListAdapter(arrayList,this, ProductListAdapter.ProductInterFace { position ->

            val intent = Intent(this,ViewProduct::class.java)
            intent.putExtra("name",arrayList[position].getName())
            intent.putExtra("price",arrayList[position].getPrice())
            intent.putExtra("description",arrayList[position].getDescription())
            intent.putExtra("imageUrl",arrayList[position].getImageUrl())
            startActivity(intent)
            overridePendingTransition(0,0)
        })
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        cartMenu.setOnClickListener {
            val intent = Intent(this,CartActivity::class.java)
            startActivity(intent)
        }
    }

    fun getDataFromAPI() {
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val jsonObjectResponse = JSONObject(response)
                val jsonArrayData = jsonObjectResponse.getJSONArray("products")
                for (i in 0 until jsonArrayData.length()) {
                    val product = Product(jsonArrayData.getJSONObject(i))
                    arrayList.add(product)
                }
                adapter.notifyDataSetChanged()
                progresBar.visibility = View.GONE
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Something happen wrong, Please try later", Toast.LENGTH_LONG).show()
            })
        queue.add(stringRequest)
    }
}
