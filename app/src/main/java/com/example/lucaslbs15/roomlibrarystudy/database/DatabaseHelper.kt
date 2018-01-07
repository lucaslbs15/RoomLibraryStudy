package com.example.lucaslbs15.roomlibrarystudy.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper : SQLiteOpenHelper {

    private val context: Context
    private val LOG_TAG: String = "DatabaseHelper"

    private val createCustomer: String = "CREATE TABLE customer (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "name VARCHAR(25) NOT NULL, " +
            "middle_name VARCHAR(25), " +
            "last_name VARCHAR(25) NOT NULL, " +
            "identity VARCHAR(11) NOT NULL " +
            ");"

    private val createContact: String = "CREATE TABLE contact (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "e_mail VARCHAR(50) NOT NULL, " +
            "phone_number VARCHAR(11) NOT NULL, " +
            "customer_id INTEGER, " +
            "FOREIGN KEY(customer_id) REFERENCES customer(id) " +
            ");"

    private val createAddress: String = "CREATE TABLE address (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "zip_code VARCHAR(8) NOT NULL, " +
            "street VARCHAR(25) NOT NULL, " +
            "complement VARCHAR(25) NOT NULL, " +
            "state VARCHAR(25) NOT NULL, " +
            "city VARCHAR(25) NOT NULL, " +
            "customer_id INTEGER, " +
            "FOREIGN KEY(customer_id) REFERENCES customer(id) " +
            ");"

    constructor(context: Context, databaseName: String, databaseVersion: Int)
            : super(context, databaseName, null, databaseVersion) {
        this.context = context
        Log.i(LOG_TAG, "classes initialized")
    }


    override fun onCreate(p0: SQLiteDatabase?) {
        Log.i(LOG_TAG, "onCreate() called")
        createTables(p0)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        Log.i(LOG_TAG, "onUpgrade() not implemented")
    }

    private fun createTables(database: SQLiteDatabase?) {
        execSQL(database, createCustomer)
        execSQL(database, createContact)
        execSQL(database, createAddress)
    }

    private fun execSQL(database: SQLiteDatabase?, sql: String) {
        try {
            database!!.execSQL(sql)
        } catch (ex: Exception) {
            Log.e(LOG_TAG, String.format("execSQL() exception: %s", ex.message))
        }
    }

}