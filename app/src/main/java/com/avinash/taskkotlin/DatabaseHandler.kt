package com.avinash.taskkotlin

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException

//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "CartDatabase"
        private val TABLE_NAME = "CartTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_DESC = "description"
        private val KEY_IMAGE = "image"
        private val KEY_PRICE = "price"
        private val KEY_QUANTITY = "quantity"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createProductTable = ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_IMAGE + " TEXT,"  + KEY_PRICE + " TEXT,"
                + KEY_QUANTITY + " INTEGER," + KEY_DESC + " TEXT" + ")")
        db?.execSQL(createProductTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }


    //method to insert data
    fun addProduct(cart: CartModelClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, cart.productId)
        contentValues.put(KEY_NAME, cart.productName)
        contentValues.put(KEY_IMAGE,cart.productImage )
        contentValues.put(KEY_DESC,cart.productDescription)
        contentValues.put(KEY_PRICE,cart.productPrice)
        contentValues.put(KEY_QUANTITY,cart.quantity)
        // Inserting Row
        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success
    }
    //method to read data
    @SuppressLint("Recycle")
    fun getCartProducts():ArrayList<Product>{
        val cartList:ArrayList<Product> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor: Cursor?
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var productId: Int
        var productName: String
        var productDescription: String
        var productImage: String
        var productPrice: String
        var productQuantity: Int
        if (cursor.moveToFirst()) {
            do {
                productId = cursor.getInt(cursor.getColumnIndex("id"))
                productName = cursor.getString(cursor.getColumnIndex("name"))
                productDescription = cursor.getString(cursor.getColumnIndex("description"))
                productImage = cursor.getString(cursor.getColumnIndex("image"))
                productPrice = cursor.getString(cursor.getColumnIndex("price"))
                productQuantity = cursor.getInt(cursor.getColumnIndex("quantity"))
                val product = Product(productImage,productName,productPrice,productDescription,productId.toString(),productQuantity)
                cartList.add(product)
            } while (cursor.moveToNext())
        }
        return cartList
    }
    fun updateQuantity(qty: Int, productId: Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_QUANTITY, qty)

        // Updating Row
        val success = db.update(TABLE_NAME, contentValues, "id=$productId",null)
        db.close()
        return success
    }
    fun removeItem(productId:Int):Int{
        val db = this.writableDatabase
        val success = db.delete(TABLE_NAME, "id=$productId",null)
        db.close()
        return success
    }

    @SuppressLint("Recycle")
    fun countTotalItem() :Int{
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor: Cursor?
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return 0
        }
        return cursor!!.count
    }
}