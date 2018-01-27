package com.example.lucaslbs15.roomlibrarystudy.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "contact")
class Contact : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
    @ColumnInfo(name = "e_mail")
    var email: String
    @ColumnInfo(name = "phone_number")
    var phoneNumber: String
    @ColumnInfo(name = "customer_id")
    var idCustomer: Int

    init {
        this.email = ""
        this.phoneNumber = ""
        this.idCustomer = 0
    }
}