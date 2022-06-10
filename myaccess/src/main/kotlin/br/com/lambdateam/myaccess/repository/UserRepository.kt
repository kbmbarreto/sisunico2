package br.com.lambdateam.myaccess.repository

import br.com.lambdateam.myaccess.model.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<UserModel, Long> {

    override fun findById(id: Long): Optional<UserModel>
    fun existsByUserName(userName: String): Boolean
    fun existsByEmail(email: String): Boolean
}