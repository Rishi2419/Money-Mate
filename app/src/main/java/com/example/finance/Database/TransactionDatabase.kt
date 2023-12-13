package com.example.finance.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.finance.Dao.TransactionDao
import com.example.finance.Model.Transaction

@Database(entities = [Transaction::class], version = 1, exportSchema = false)
abstract class TransactionDatabase : RoomDatabase() {

    abstract fun myTransactionDao(): TransactionDao

    companion object
    {
        private var INSTANCE: TransactionDatabase?=null
        fun getDatabaseInstance(context: Context): TransactionDatabase {
            val tempInstance= INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this)
            {
                val roomDatabaseInstance = Room.databaseBuilder(context,
                    TransactionDatabase::class.java,"Transaction").allowMainThreadQueries().build()
                INSTANCE =roomDatabaseInstance
                return roomDatabaseInstance
            }
        }
    }
}