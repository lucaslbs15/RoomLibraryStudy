package com.example.lucaslbs15.roomlibrarystudy.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.lucaslbs15.roomlibrarystudy.database.roomdao.AddressRoomDAO
import com.example.lucaslbs15.roomlibrarystudy.database.roomdao.ContactRoomDAO
import com.example.lucaslbs15.roomlibrarystudy.database.roomdao.CustomerRoomDAO
import com.example.lucaslbs15.roomlibrarystudy.model.Address
import com.example.lucaslbs15.roomlibrarystudy.model.Contact
import com.example.lucaslbs15.roomlibrarystudy.model.Customer

@Database(entities = arrayOf(Customer::class, Address::class, Contact::class), version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun customerDAO() : CustomerRoomDAO

    abstract fun addressDAO() : AddressRoomDAO

    abstract fun contactDAO() : ContactRoomDAO
}