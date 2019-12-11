package com.avinash.taskkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ProductListAdapter


    var arrayList : ArrayList<Product> = ArrayList()
    var url : String = "http://www.mocky.io/v2/5def7b172f000063008e0aa2"// "http://dummy.restapiexample.com/api/v1/employees"//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initRecyclerView()
        getDataFromAPI()
    }

    private fun initRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        adapter = ProductListAdapter(arrayList,this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun getDataFromAPI() {
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val jsonObjectResponse = JSONObject(response)
                val jsonArrayData = jsonObjectResponse.getJSONArray("products")
                for (i in 0 until jsonArrayData!!.length()) {
                    val product = Product(jsonArrayData.getJSONObject(i))
                    arrayList.add(product)
                }
                adapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,error.toString(), Toast.LENGTH_LONG).show();
            })
        queue.add(stringRequest)
    }
}
