package com.example.lucaslbs15.roomlibrarystudy.database.dao

import android.content.Context
import com.example.lucaslbs15.roomlibrarystudy.R
import com.example.lucaslbs15.roomlibrarystudy.database.DatabaseHelper

open class BasicDAO(context: Context) {

    protected val context: Context
    protected val helper: DatabaseHelper

    init {
        this.context = context
        helper = DatabaseHelper(context,
                context.getString(R.string.database_name),
                context.resources.getInteger(R.integer.database_version))
    }
}