package com.training.eshopper.data.repository

import com.training.eshopper.common.Result
import com.training.eshopper.data.model.Product
import com.training.eshopper.data.remote.ApiService
import org.json.JSONObject
import javax.inject.Inject

class ProductRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllProducts(): Result<List<Product>> {
        return try {
            val response = apiService.getAllProducts()
            val jsonString = response.string()
            val jsonObject = JSONObject(jsonString)
            val data = jsonObject.getJSONArray("data")
            val products = mutableListOf<Product>()

            for (i in 0 until data.length()) {
                val product = data.getJSONObject(i)
                products.add(
                    Product(
                        product.getInt("id"),
                        product.getString("name"),
                        product.getString("id_user"),
                        product.getString("image"),
                        product.getLong("price"),
                        product.getString("detail")
                    )
                )
            }

            Result.Success(products)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}