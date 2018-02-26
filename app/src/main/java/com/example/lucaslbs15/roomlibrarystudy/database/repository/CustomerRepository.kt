package com.example.lucaslbs15.roomlibrarystudy.database.repository

import android.content.Context
import com.example.lucaslbs15.roomlibrarystudy.database.AppDatabase
import com.example.lucaslbs15.roomlibrarystudy.database.migration.RoomDatabaseBuilder
import com.example.lucaslbs15.roomlibrarystudy.model.Address
import com.example.lucaslbs15.roomlibrarystudy.model.Contact
import com.example.lucaslbs15.roomlibrarystudy.model.Customer

class CustomerRepository(context: Context) {

    private var context: Context
    private var appDatabase: AppDatabase? = null

    init {
        this.context = context
        appDatabase = RoomDatabaseBuilder.doMigration(context)
    }

    fun save(customer: Customer) : Boolean {
        var ret = true
        customer.id = getNextId()
        val customerId = appDatabase?.customerDAO()?.insert(customer)
        if (customerId == null || customerId < 0) {
            return false
        }

        customer.contact.idCustomer = customerId.toInt()
        appDatabase?.contactDAO()?.insert(customer.contact!!)

        customer.address.customerId = customerId.toInt()
        appDatabase?.addressDAO()?.insert(customer.address!!)
        return ret
    }

    private fun getNextId() : Int {
        val customers = appDatabase?.customerDAO()?.listAll()
        if (customers != null && customers.isNotEmpty()) {
            val customer = customers.last()
            return customer.id + 1
        }
        return 1
    }

    fun update(customer: Customer) : Boolean {
        val rowCustomerUpdated = appDatabase?.customerDAO()?.update(customer)
        val rowContactUpdated = appDatabase?.contactDAO()?.update(customer.contact)
        val rowAddressUpdated = appDatabase?.addressDAO()?.update(customer.address)
        return (rowCustomerUpdated != null && rowCustomerUpdated > 0
                && rowContactUpdated != null && rowContactUpdated > 0
                && rowAddressUpdated != null && rowAddressUpdated > 0)
    }

    fun select(customerId: Int) : Customer? {
        val customer: Customer? = appDatabase?.customerDAO()?.selectById(customerId)
        customer ?: return null

        customer.contact = appDatabase?.contactDAO()?.selectByCustomerId(customerId) ?: Contact()
        customer.address = appDatabase?.addressDAO()?.selectByCustomerId(customerId) ?: Address()

        return customer
    }

    fun listAll() : List<Customer>? {
        val customers = appDatabase?.customerDAO()?.listAll()
        customers ?: return null

        for (index in customers.indices) {
            val contact = appDatabase?.contactDAO()?.selectByCustomerId(customers[index].id)
            val address = appDatabase?.addressDAO()?.selectByCustomerId(customers[index].id)
            customers[index].contact = contact ?: Contact()
            customers[index].address = address ?: Address()
        }
        return customers
    }
}