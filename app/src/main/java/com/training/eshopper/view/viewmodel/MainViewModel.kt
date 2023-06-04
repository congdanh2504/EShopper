package com.training.eshopper.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.eshopper.common.Result
import com.training.eshopper.data.model.DataState
import com.training.eshopper.data.model.Product
import com.training.eshopper.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private val _productsState = MutableLiveData<DataState<List<Product>>>()
    val productsState: LiveData<DataState<List<Product>>>
        get() = _productsState

    fun getAllProducts() = viewModelScope.launch {
        _productsState.value = DataState.Loading
        val result = productRepository.getAllProducts()
        handleProductsResult(result)
    }


    private fun handleProductsResult(result: Result<List<Product>>) {
        when (result) {
            is Result.Success -> _productsState.value = DataState.Success(result.data)
            is Result.Error -> _productsState.value = DataState.Error(result.exception.message)
        }
    }
}