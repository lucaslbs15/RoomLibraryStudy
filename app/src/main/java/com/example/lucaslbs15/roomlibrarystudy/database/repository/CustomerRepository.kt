package com.example.lucaslbs15.roomlibrarystudy.database.repository

import android.content.Context
import com.example.lucaslbs15.roomlibrarystudy.database.dao.AddressDAO
import com.example.lucaslbs15.roomlibrarystudy.database.dao.ContactDAO
import com.example.lucaslbs15.roomlibrarystudy.database.dao.CustomerDAO
import com.example.lucaslbs15.roomlibrarystudy.model.Address
import com.example.lucaslbs15.roomlibrarystudy.model.Contact
import com.example.lucaslbs15.roomlibrarystudy.model.Customer

class CustomerRepository(context: Context) {

    private var context: Context
    private val customerDAO: CustomerDAO
    private val contactDAO: ContactDAO
    private val addressDAO: AddressDAO

    init {
        this.context = context
        this.customerDAO = CustomerDAO(context)
        this.contactDAO = ContactDAO(context)
        this.addressDAO = AddressDAO(context)
    }

    fun save(customer: Customer) : Boolean {
        var ret = true
        val customerId = customerDAO.insert(customer)
        if (customerId < 0) {
            return false
        }

        customer.contact.idCustomer = customerId.toInt()
        if (!contactDAO.insert(customer.contact!!)) {
            ret = false
        }

        customer.address.customerId = customerId.toInt()
        if (!addressDAO.insert(customer.address!!)) {
            ret = false
        }
        return ret
    }

    fun update(customer: Customer) : Boolean {
        var ret = true
        if (!customerDAO.update(customer)) {
            ret = false
        }

        if (!contactDAO.update(customer.contact)) {
            ret = false
        }

        if (!addressDAO.update(customer.address)) {
            ret = false
        }
        return ret
    }

    fun select(customerId: Int) : Customer? {
        val customer: Customer? = customerDAO.selectById(customerId)
        customer ?: return null

        customer.contact = contactDAO.selectByCustomerId(customerId) ?: Contact()
        customer.address = addressDAO.selectByCustomerId(customerId) ?: Address()

        return customer
    }

    fun listAll() : List<Customer>? {
        val customers = customerDAO.listAll()
        customers ?: return null

        for (index in customers.indices) {
            val contact = contactDAO.selectByCustomerId(customers[index].id)
            val address = addressDAO.selectByCustomerId(customers[index].id)
            customers[index].contact = contact ?: Contact()
            customers[index].address = address ?: Address()
        }
        return customers
    }
}