package com.example.lucaslbs15.roomlibrarystudy.database.roomdao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.example.lucaslbs15.roomlibrarystudy.model.Customer

@Dao
interface CustomerRoomDAO {

    @Insert
    fun insert(customer: Customer) : Long

    @Update
    fun update(customer: Customer) : Int

    @Query("SELECT * FROM customer WHERE id = (:id)")
    fun selectById(id: Int) : Customer?

    @Query("SELECT * FROM customer WHERE id = (:name)")
    fun selectByName(name: String) : Customer?

    @Query("SELECT * FROM customer")
    fun listAll() : List<Customer>?
}