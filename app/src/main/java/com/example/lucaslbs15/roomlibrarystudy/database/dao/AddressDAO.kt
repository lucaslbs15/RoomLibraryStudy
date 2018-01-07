package com.example.lucaslbs15.roomlibrarystudy.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.lucaslbs15.roomlibrarystudy.model.Address

class AddressDAO : BasicDAO {

    constructor(context: Context) : super(context)

    private fun getValues(address: Address): ContentValues {
        val values = ContentValues()
        values.put("zip_code", address.zipCode)
        values.put("street", address.street)
        values.put("complement", address.complement)
        values.put("state", address.state)
        values.put("city", address.city)
        values.put("customer_id", address.customerId)
        return values
    }

    fun insert(address: Address) : Boolean {
        var ret: Boolean
        val sqliteDatabase: SQLiteDatabase = helper.writableDatabase

        try {
            ret = (sqliteDatabase.insert(
                    "address", null, getValues(address)) > 0)
        } catch (ex: Exception) {
            return false
        } finally {
            if (sqliteDatabase.isOpen) {
                sqliteDatabase.close()
            }
        }
        return ret
    }

    fun update(address: Address) : Boolean {
        var ret: Boolean
        val sqliteDatabase: SQLiteDatabase = helper.writableDatabase

        val parameters = arrayOf(address.customerId.toString())

        try {
            ret = (sqliteDatabase.update(
                    "address", getValues(address),
                    "customer_id = ?", parameters) > 0
                    )
        } catch (ex: Exception) {
            ret = false
        } finally {
            if (sqliteDatabase.isOpen) {
                sqliteDatabase.close()
            }
        }
        return ret
    }

    fun selectByCustomerId(customerId: Int) : Address? {
        var address: Address? = null
        val sqliteDatabase: SQLiteDatabase = helper.readableDatabase
        val columns = arrayOf("id", "zip_code", "street", "complement", "state", "city", "customer_id")
        val parameters = arrayOf(customerId.toString())

        val cursor = sqliteDatabase.query(
                "address", columns, "customer_id = ?", parameters, null, null, null)

        try {
            if (cursor.count > 0) {
                cursor.moveToNext()

                address = Address()
                address.id = cursor.getInt(0)
                address.zipCode = cursor.getString(1)
                address.street = cursor.getString(2)
                address.complement = cursor.getString(3)
                address.state = cursor.getString(4)
                address.city = cursor.getString(5)
                address.customerId = cursor.getInt(6)
            }
            cursor.close()
        } catch (ex: Exception) {
            return null
        } finally {
            if (sqliteDatabase.isOpen) {
                sqliteDatabase.close()
            }
        }
        return  address
    }
}