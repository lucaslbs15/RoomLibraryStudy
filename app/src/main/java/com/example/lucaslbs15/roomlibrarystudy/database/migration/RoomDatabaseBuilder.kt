package com.example.lucaslbs15.roomlibrarystudy.database.migration

import android.arch.persistence.room.Room
import android.content.Context
import com.example.lucaslbs15.roomlibrarystudy.R
import com.example.lucaslbs15.roomlibrarystudy.database.AppDatabase

object RoomDatabaseBuilder {

    fun doMigration(context: Context) : AppDatabase {
        val customerSql = "ALTER TABLE customer ADD COLUMN nickname TEXT"

        return Room.databaseBuilder(context,
                AppDatabase::class.java, context.getString(R.string.database_name))
                .addMigrations(MigrationItem(
                        1, 2, customerSql))
                .build()
    }
}