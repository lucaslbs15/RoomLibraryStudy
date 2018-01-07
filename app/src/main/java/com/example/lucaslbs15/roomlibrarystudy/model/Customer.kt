package com.example.lucaslbs15.roomlibrarystudy.model

import java.io.Serializable

class Customer(name: String, middleName: String, lastName: String, identity: String) : Serializable {

    var id: Int = 0
    var name: String
    var middleName: String
    var lastName: String
    var identity: String
    var address: Address
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