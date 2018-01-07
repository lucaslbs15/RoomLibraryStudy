package com.example.lucaslbs15.roomlibrarystudy.model

import java.io.Serializable

class Contact : Serializable {

    var id: Int = 0
    var email: String
    var phoneNumber: String
    var idCustomer: Int

    init {
        this.email = ""
        this.phoneNumber = ""
        this.idCustomer = 0
    }
}