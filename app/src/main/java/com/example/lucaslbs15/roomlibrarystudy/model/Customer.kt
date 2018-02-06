package com.example.lucaslbs15.roomlibrarystudy.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "customer")
class Customer(name: String, middleName: String, lastName: String, identity: String) : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
    @ColumnInfo(name = "name")
    var name: String
    @ColumnInfo(name = "middle_name")
    var middleName: String
    @ColumnInfo(name = "last_name")
    var lastName: String
    @ColumnInfo(name = "identity")
    var identity: String
    @Ignore
    var address: Address
    @Ignore
    var contact: Contact

    init {
        this.name = name
        this.middleName = middleName
        this.lastName = lastName
        this.identity = identity
        this.address = Address()
        this.contact = Contact()
    }
}