package com.example.lucaslbs15.roomlibrarystudy.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.lucaslbs15.roomlibrarystudy.model.Contact

class ContactDAO : BasicDAO {

    constructor(context: Context) : super(context)

    private fun getValues(contact: Contact): ContentValues {
        val values = ContentValues()
        values.put("e_mail", contact.email)
        values.put("phone_number", contact.phoneNumber)
        values.put("customer_id", contact.idCustomer)
        return values
    }

    fun insert(contact: Contact) : Boolean {
        var ret: Boolean
        val sqliteDatabase: SQLiteDatabase = helper.writableDatabase

        try {
            ret = (sqliteDatabase.insert(
                    "contact", null, getValues(contact)) > 0)
        } catch (ex: Exception) {
            return false
        } finally {
            if (sqliteDatabase.isOpen) {
                sqliteDatabase.close()
            }
        }
        return ret
    }

    fun update(contact: Contact) : Boolean {
        var ret: Boolean
        val sqliteDatabase: SQLiteDatabase = helper.writableDatabase

        val parameters = arrayOf(contact.idCustomer.toString())

        try {
            ret = (sqliteDatabase.update(
                    "contact", getValues(contact),
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

    fun selectByCustomerId(customerId: Int) : Contact? {
        var contact: Contact? = null
        val sqliteDatabase: SQLiteDatabase = helper.readableDatabase
        val columns = arrayOf("id", "e_mail", "phone_number", "customer_id")
        val parameters = arrayOf(customerId.toString())

        val cursor = sqliteDatabase.query("contact", columns, "customer_id = ?", parameters, null, null, null)

        try {
            if (cursor.count > 0) {
                cursor.moveToNext()

                contact = Contact()
                contact.id = cursor.getInt(0)
                contact.email = cursor.getString(1)
                contact.phoneNumber = cursor.getString(2)
                contact.idCustomer = cursor.getInt(3)
            }
            cursor.close()
        } catch (ex: Exception) {
            return null
        } finally {
            if (sqliteDatabase.isOpen) {
                sqliteDatabase.close()
            }
        }
        return contact
    }
}