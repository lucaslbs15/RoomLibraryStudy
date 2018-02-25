package com.example.lucaslbs15.roomlibrarystudy.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "address")
@ForeignKey(entity = Customer::class, parentColumns = arrayOf("id"), childColumns = arrayOf("customer_id"))
class Address : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
    @ColumnInfo(name = "zip_code")
    var zipCode: String
    @ColumnInfo(name = "street")
    var street: String
    @ColumnInfo(name = "complement")
    var complement: String
    @ColumnInfo(name = "state")
    var state: String
    @ColumnInfo(name = "city")
    var city: String
    @ColumnInfo(name = "customer_id")
    var customerId: Int

    init {
        this.zipCode = ""
        this.street = ""
        this.complement = ""
        this.state = ""
        this.city = ""
        this.customerId = 0
    }

}