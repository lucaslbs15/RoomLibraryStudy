package com.example.lucaslbs15.roomlibrarystudy.database.migration

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

class MigrationItem(startVersion: Int,
                    endVersion: Int,
                    sql: String) : Migration(startVersion, endVersion) {

    var sql: String

    init {
        this.sql = sql
    }

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(sql)
    }
}