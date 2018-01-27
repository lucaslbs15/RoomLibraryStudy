package com.example.lucaslbs15.roomlibrarystudy.database.roomdao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.example.lucaslbs15.roomlibrarystudy.model.Contact

@Dao
interface ContactRoomDAO {

    @Insert
    fun insert(address: Contact)

    @Update
    fun update(address: Contact) : Boolean

    @Query("SELECT * FROM contact WHERE customer_id = (:customerId)")
    fun selectByCustomerId(customerId: Int) : Contact?
}