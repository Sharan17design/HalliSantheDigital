package com.halli.santhe.data

class ProductRepository(private val dao: ProductDao) {
    val products = dao.getAll()
    suspend fun add(p: Product) = dao.insert(p)
    suspend fun get(id: Long) = dao.getById(id)
    suspend fun delete(p: Product) = dao.delete(p)
}
