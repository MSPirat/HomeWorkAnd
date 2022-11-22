package ru.netology.nmedia.repository

import ru.netology.nmedia.di.DependencyContainer
import ru.netology.nmedia.dto.User
import ru.netology.nmedia.errors.ApiException
import ru.netology.nmedia.errors.NetworkException
import ru.netology.nmedia.errors.UnknownException
import java.io.IOException

class AuthRepository {

    suspend fun authUser(login: String, password: String): User {
        try {
            val response = DependencyContainer.getInstance().service.updateUser(login, password)
//            println(1)
            if (!response.isSuccessful) {
//                println("authorized")
                throw ApiException(response.code(), response.message())
            }
            return response.body() ?: throw Exception()
        } catch (e: IOException) {
            throw NetworkException
        } catch (e: Exception) {
            throw UnknownException
        }
    }

    suspend fun registrationUser(login: String, password: String, name: String): User {
        try {
            val response = DependencyContainer.getInstance().service.registrationUser(login, password, name)
//            println(2)
            if (!response.isSuccessful) {
//                println("register")
                throw ApiException(response.code(), response.message())
            }
            return response.body() ?: throw Exception()
        } catch (e: IOException) {
            throw NetworkException
        } catch (e: Exception) {
            throw UnknownException
        }
    }
}