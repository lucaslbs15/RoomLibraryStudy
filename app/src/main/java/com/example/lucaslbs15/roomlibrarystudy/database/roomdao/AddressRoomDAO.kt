package com.example.lucaslbs15.roomlibrarystudy.database.roomdao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.example.lucaslbs15.roomlibrarystudy.model.Address

@Dao
interface AddressRoomDAO {

    @Insert
    fun insert(address: Address)

    @Update
    fun update(address: Address) : Boolean

    @Query("SELECT * FROM address WHERE customer_id = (:customerId)")
    fun selectByCustomerId(customerId: Int) : Address?
}