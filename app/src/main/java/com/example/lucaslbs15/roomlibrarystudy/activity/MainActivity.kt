package com.example.lucaslbs15.roomlibrarystudy.activity

import android.app.Activity
import android.arch.persistence.room.Room
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.lucaslbs15.roomlibrarystudy.R
import com.example.lucaslbs15.roomlibrarystudy.database.AppDatabase
import com.example.lucaslbs15.roomlibrarystudy.database.migration.RoomDatabaseBuilder
import com.example.lucaslbs15.roomlibrarystudy.database.repository.CustomerRepository
import com.example.lucaslbs15.roomlibrarystudy.databinding.ActivityMainBinding
import com.example.lucaslbs15.roomlibrarystudy.model.Customer

class MainActivity : AppCompatActivity() {

    private val LOG_TAG: String? = "MainActivity"
    private val LIST_REQUEST_CODE = 15
    private var binding: ActivityMainBinding? = null
    private var customerId = -1
    private var appDatabase: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initListeners()

        try {
            initDatabase()
            initMigration()
        } catch (ex: Exception) {
            Log.e(LOG_TAG, String.format("initDatabase() exception: %s", ex.message))
        }
    }

    private fun initDatabase() {
        appDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, getString(R.string.database_name)).build()
    }

    private fun initMigration() {
        RoomDatabaseBuilder.doMigration(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        setButtonText()
    }

    private fun setButtonText() {
        if (customerId > 0) {
            binding?.activityMainSaveFormButton?.text = getString(R.string.activity_main_update_form)
        } else {
            binding?.activityMainSaveFormButton?.text = getString(R.string.activity_main_save_form)
        }
    }

    private fun initListeners() {
        initSaveFormListener()
        initListUsersListener()
    }

    private fun initSaveFormListener() {
        binding?.activityMainSaveFormButton?.setOnClickListener(
                View.OnClickListener {
                    Thread(Runnable {
                        saveForm()
                    }).start()
                }
        )
    }

    private fun initListUsersListener() {
        binding?.activityMainListButton?.setOnClickListener(
                View.OnClickListener {
                    startListActivity()
                }
        )
    }

    private fun saveForm() {
        Log.i(LOG_TAG, "saveForm() called")
        val customer = populateCustomer()
        val repository = CustomerRepository(this)
        val result: Boolean

        if (customer.id > 0) {
            result = repository.update(customer)
        } else {
            result = repository.save(customer)
        }

        if (result) {
            Log.i(LOG_TAG, "customer saved")
            runOnUiThread {
                clearForm()
                setButtonText()
                Toast.makeText(this, getString(R.string.activity_main_saved), Toast.LENGTH_SHORT).show()
            }
        } else {
            runOnUiThread {
                Toast.makeText(this, getString(R.string.activity_main_save_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun populateCustomer() : Customer {
        val nickname = binding?.activityMainNicknameEditText?.text.toString()
        val name = binding?.activityMainNameEditText?.text.toString()
        val middleName = binding?.activityMainMiddleEditText?.text.toString()
        val lastName = binding?.activityMainLastEditText?.text.toString()
        val identity = binding?.activityMainIdentityEditText?.text.toString()

        val customer = Customer(name, middleName, lastName, identity, nickname)
        customer.id = customerId

        customer.contact.idCustomer = customerId
        customer.contact.phoneNumber = binding?.activityMainPhoneEditText?.text.toString()
        customer.contact.email = binding?.activityMainEmailEditText?.text.toString()

        customer.address.customerId = customerId
        customer.address.street = binding?.activityMainAddressEditText?.text.toString()
        customer.address.complement = binding?.activityMainComplementEditText?.text.toString()
        customer.address.zipCode = binding?.activityMainZipCodeEditText?.text.toString()
        customer.address.city = binding?.activityMainCityEditText?.text.toString()
        customer.address.state = binding?.activityMainStateEditText?.text.toString()
        return customer
    }

    private fun clearForm() {
        customerId = -1
        binding?.activityMainNameEditText?.text?.clear()
        binding?.activityMainMiddleEditText?.text?.clear()
        binding?.activityMainLastEditText?.text?.clear()
        binding?.activityMainIdentityEditText?.text?.clear()

        binding?.activityMainPhoneEditText?.text?.clear()
        binding?.activityMainEmailEditText?.text?.clear()

        binding?.activityMainAddressEditText?.text?.clear()
        binding?.activityMainComplementEditText?.text?.clear()
        binding?.activityMainZipCodeEditText?.text?.clear()
        binding?.activityMainCityEditText?.text?.clear()
        binding?.activityMainStateEditText?.text?.clear()
    }

    private fun startListActivity() {
        val intent = Intent(this, ListActivity::class.java)
        startActivityForResult(intent, LIST_REQUEST_CODE)
    }

    override protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == LIST_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val customer = getCustomer(data)
            if (customer != null) {
                fillForm(customer)
            }
        } else {
            clearForm()
        }
        setButtonText()
    }

    private fun getCustomer(data: Intent) : Customer? {
        val extras: Bundle? = data.extras

        when(extras != null) {
            true -> return extras?.get("customer") as Customer?
            false -> return null
        }
    }

    private fun fillForm(customer: Customer) {
        customerId = customer.id
        binding?.activityMainNameEditText?.text?.append(customer.name)
        binding?.activityMainMiddleEditText?.text?.append(customer.middleName)
        binding?.activityMainLastEditText?.text?.append(customer.lastName)
        binding?.activityMainIdentityEditText?.text?.append(customer.identity)

        binding?.activityMainPhoneEditText?.text?.append(customer.contact.phoneNumber)
        binding?.activityMainEmailEditText?.text?.append(customer.contact.email)

        binding?.activityMainAddressEditText?.text?.append(customer.address.street)
        binding?.activityMainComplementEditText?.text?.append(customer.address.complement)
        binding?.activityMainZipCodeEditText?.text?.append(customer.address.zipCode)
        binding?.activityMainCityEditText?.text?.append(customer.address.city)
        binding?.activityMainStateEditText?.text?.append(customer.address.state)
    }
}
