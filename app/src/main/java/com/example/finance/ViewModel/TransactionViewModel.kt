package com.example.finance.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.finance.Database.TransactionDatabase
import com.example.finance.Model.Transaction
import com.example.finance.Repository.TransactionRepository


class TransactionViewModel(application: Application): AndroidViewModel(application) {

    val repository: TransactionRepository

    init{
        val dao = TransactionDatabase.getDatabaseInstance(application).myTransactionDao()
        repository= TransactionRepository(dao)
    }

    fun addTransaction(transaction: Transaction){
        repository.insertTransaction(transaction)
    }

    fun getTransaction(): LiveData<List<Transaction>> = repository.getAllTransaction()

    fun getMonthlyTransaction(month:Int,Year:Int): LiveData<List<Transaction>> = repository.getMonthlyTransaction(month,Year)

    fun getYearlyTransaction(year: Int): LiveData<List<Transaction>> = repository.getYearlyTransaction(year)

    fun deleteTransaction(id:Int){
        repository.deleteTransaction(id)
    }

    fun updateTransaction(transaction: Transaction){
        repository.updateTransaction(transaction)
    }




}