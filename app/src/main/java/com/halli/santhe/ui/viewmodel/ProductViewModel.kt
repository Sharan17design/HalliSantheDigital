package com.halli.santhe.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.halli.santhe.data.AppDatabase
import com.halli.santhe.data.Product
import com.halli.santhe.data.ProductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = ProductRepository(AppDatabase.getInstance(app).productDao())

    val products: LiveData<List<Product>> =
        repo.products.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList()).asLiveData()

    fun addProduct(p: Product) = viewModelScope.launch { repo.add(p) }
    fun deleteProduct(p: Product) = viewModelScope.launch { repo.delete(p) }
}
