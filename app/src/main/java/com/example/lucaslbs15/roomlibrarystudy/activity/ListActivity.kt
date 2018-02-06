package com.example.lucaslbs15.roomlibrarystudy.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.lucaslbs15.roomlibrarystudy.R
import com.example.lucaslbs15.roomlibrarystudy.database.repository.CustomerRepository
import com.example.lucaslbs15.roomlibrarystudy.databinding.ActivityListBinding
import com.example.lucaslbs15.roomlibrarystudy.model.Customer

class ListActivity : AppCompatActivity() {

    private val LOG_TAG = "ListActivity"

    private var customers: List<Customer> = ArrayList<Customer>()
    private var binding: ActivityListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)

        init()
    }

    private fun init() {
        Thread(Runnable {
            getAllCustomers()
            runOnUiThread {
                iniAdapter()
                initOnItemClickListener()
            }
        }).start()
    }

    private fun getAllCustomers() {
        val repository = CustomerRepository(this)
        customers = repository.listAll() ?: ArrayList<Customer>()
    }

    private fun iniAdapter() {
        val names = ArrayList<String>()
        for (index in customers?.indices) {
            names.add(String.format("%s %s %s",
                    customers[index].name, customers[index].middleName, customers[index].lastName))
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names)
        binding?.activityListCustomers?.adapter = adapter
    }

    private fun initOnItemClickListener() {
        binding?.activityListCustomers?.setOnItemClickListener { adapterView, view, i, l ->
            fillFormWithCustomer(customers[i])
        }
    }

    private fun fillFormWithCustomer(customer: Customer) {
        val bundle = Bundle()
        bundle.putSerializable("customer", customer)
        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}
