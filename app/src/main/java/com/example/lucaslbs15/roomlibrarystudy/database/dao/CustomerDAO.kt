package com.example.lucaslbs15.roomlibrarystudy.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.lucaslbs15.roomlibrarystudy.model.Customer

class CustomerDAO : BasicDAO {

    constructor(context: Context) : super(context)

    private fun getValues(customer: Customer): ContentValues {
        val values = ContentValues()
        values.put("name", customer.name)
        values.put("middle_name", customer.middleName)
        values.put("last_name", customer.lastName)
        values.put("identity", customer.identity)
        return values
    }

    fun insert(customer: Customer) : Long {
        var ret: Long
        val sqliteDatabase: SQLiteDatabase = helper.writableDatabase

        try {
            ret = sqliteDatabase.insert(
                    "customer", null, getValues(customer))
        } catch (ex: Exception) {
            return -1
        } finally {
            if (sqliteDatabase.isOpen) {
                sqliteDatabase.close()
            }
        }
        return ret
    }

    fun update(customer: Customer): Boolean {
        var ret: Boolean
        val sqliteDatabase: SQLiteDatabase = helper.writableDatabase

        val parameters = arrayOf(customer.id.toString())

        try {
            ret = (sqliteDatabase.update("customer", getValues(customer), "id = ?", parameters) > 0)
        } catch (ex: Exception) {
            ret = false
        } finally {
            if (sqliteDatabase.isOpen) {
                sqliteDatabase.close()
            }
        }
        return ret
    }

    fun selectById(id: Int): Customer? {
        val sqliteDatabase: SQLiteDatabase = helper.readableDatabase
        var customer: Customer? = null
        val columns = arrayOf("id", "name", "middle_name", "last_name", "identity")
        val parameters = arrayOf(id.toString())

        val cursor = sqliteDatabase.query("customer", columns, "id = ?", parameters, null, null, null)

        try {
            if (cursor.count > 0) {
                cursor.moveToNext()

                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val middleName = cursor.getString(2)
                val lastName = cursor.getString(3)
                val identity = cursor.getString(4)

                customer = Customer(name, middleName, lastName, identity)
                customer.id = id
            }
            cursor.close()
        } catch (ex: Exception) {
            return null
        } finally {
            if (sqliteDatabase.isOpen) {
                sqliteDatabase.close()
            }
        }
        return customer
    }

    fun selectByName(name: String): Customer? {
        val sqliteDatabase: SQLiteDatabase = helper.readableDatabase
        var customer: Customer? = null
        val columns = arrayOf("id", "name", "middle_name", "last_name", "identity")
        val parameters = arrayOf(name)

        val cursor = sqliteDatabase.query("customer", columns, "name = ?", parameters, null, null, null)

        try {
            if (cursor.count > 0) {
                cursor.moveToNext()

                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val middleName = cursor.getString(2)
                val lastName = cursor.getString(3)
                val identity = cursor.getString(4)

                customer = Customer(name, middleName, lastName, identity)
                customer.id = id
            }
            cursor.close()
        } catch (ex: Exception) {
            return null
        } finally {
            if (sqliteDatabase.isOpen) {
                sqliteDatabase.close()
            }
        }
        return customer
    }

    fun listAll(): List<Customer>? {
        var customers: ArrayList<Customer>? = null
        val sqliteDatabase: SQLiteDatabase = helper.readableDatabase
        var customer: Customer? = null
        val columns = arrayOf("id", "name", "middle_name", "last_name", "identity")

        val cursor = sqliteDatabase.query("customer", columns, null, null, null, null, null)

        try {
            if (cursor.count > 0) {
                customers = ArrayList<Customer>()
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(0)
                    val name = cursor.getString(1)
                    val middleName = cursor.getString(2)
                    val lastName = cursor.getString(3)
                    val identity = cursor.getString(4)

                    customer = Customer(name, middleName, lastName, identity)
                    customer.id = id
                    customers.add(customer)
                }
            }
            cursor.close()
        } catch (ex: Exception) {
            return null
        } finally {
            if (sqliteDatabase.isOpen) {
                sqliteDatabase.close()
            }
        }
        return customers
    }

}