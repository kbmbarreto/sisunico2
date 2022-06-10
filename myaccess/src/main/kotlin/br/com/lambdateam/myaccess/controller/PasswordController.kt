package br.com.lambdateam.myaccess.controller

import br.com.lambdateam.myaccess.extension.toModel
import br.com.lambdateam.myaccess.model.PasswordModel
import br.com.lambdateam.myaccess.model.PatchPassword
import br.com.lambdateam.myaccess.model.PostPassword
import br.com.lambdateam.myaccess.repository.PasswordRepository
import br.com.lambdateam.myaccess.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@Service
@RestController
@RequestMapping("passwords", produces = [MediaType.APPLICATION_JSON_VALUE])
class PasswordControllerImpl(val passwordRepository: PasswordRepository,
                             val userRepository: UserRepository) : PasswordController {

    @GetMapping
    override fun listPasswords() = passwordRepository.findAll()

    @GetMapping("/{id}")
    override fun findPassword(@PathVariable("id") id: Long) =
        passwordRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    override fun createPassword(@RequestBody password: PostPassword) {
        val user = userRepository.findById(password.idUser).get()
        passwordRepository.save(password.toModel(user))
    }
//    @PostMapping
//    fun savePassword(@RequestBody password: PasswordModel) {
//        passwordService.savePasssword(password)
//    }

//    @PutMapping("/{id}")
//    override fun fullUpdatePassword(@PathVariable("id") id:Long, @RequestBody password:PasswordModel) : PasswordModel {
//        val foundPassword = findPassword(id)
//        val copyPassword = foundPassword.copy(
//            description = password.description,
//            url = password.url,
//            userName = password.userName,
//            password = password.password,
//            notes = password.notes,
//            iduser = password.iduser
//        )
//        return passwordRepository.save(copyPassword)
//    }

    @PatchMapping("/{id}")
    override fun incrementalUpdatePassword(@PathVariable("id") id: Long, @RequestBody password: PatchPassword): PasswordModel {
        val foundPassword = findPassword(id)
        val copyPassword = foundPassword.copy(
            description = password.description ?: foundPassword.description,
            url = password.url ?: foundPassword.url,
            userName = password.userName ?: foundPassword.userName,
            password = password.password ?: foundPassword.password,
            notes = password.notes ?: foundPassword.notes,
            user = foundPassword.user
        )
        return passwordRepository.save(copyPassword)
    }

    @DeleteMapping("/{id}")
    override fun deletePassword(@PathVariable("id") id: Long) = passwordRepository.delete(findPassword(id))
}

interface PasswordController {

    fun listPasswords() : List<PasswordModel>

    fun findPassword(id: Long) : PasswordModel

    fun createPassword(password: PostPassword)

//    fun fullUpdatePassword(id: Long, user: PasswordModel) : PasswordModel

    fun incrementalUpdatePassword(id: Long, password: PatchPassword) : PasswordModel

    fun deletePassword(@PathVariable("id") id: Long)
}