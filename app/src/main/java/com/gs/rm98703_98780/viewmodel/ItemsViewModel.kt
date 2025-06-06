package com.gs.rm98703_98780.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.gs.rm98703_98780.database.ItemDao
import com.gs.rm98703_98780.database.ItemDatabase // ⬅️ importe a migração
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.gs.rm98703_98780.model.ItemModel

class ItemsViewModel(application: Application) : AndroidViewModel(application) {
    private val itemDao: ItemDao
    val itemsLiveData: LiveData<List<ItemModel>>

    init {
        val database = Room.databaseBuilder(
            getApplication(),
            ItemDatabase::class.java,
            "items_database"
        )
            .fallbackToDestructiveMigration() // ISSO VAI APAGAR TUDO E RECRIAR O BANCO
            .build()

        itemDao = database.itemDao()
        itemsLiveData = itemDao.getAll()
    }

    fun addItem(item: ItemModel) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.insert(item)
        }
    }

    fun removeItem(item: ItemModel) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.delete(item)
        }
    }
}
