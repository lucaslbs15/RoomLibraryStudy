package com.example.lucaslbs15.roomlibrarystudy.model

import java.io.Serializable

class Address : Serializable {

    var id: Int = 0
    var zipCode: String
    var street: String
    var complement: String
    var state: String
    var city: String
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