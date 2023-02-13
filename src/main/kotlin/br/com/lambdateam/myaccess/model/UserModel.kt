package br.com.lambdateam.myaccess.model

import br.com.lambdateam.myaccess.enums.Profile
import javax.persistence.*

@Entity
@Table(name = "users")
data class UserModel (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "username", length = 60)
    val userName: String,
    @Column(name = "email", length = 75)
    val email: String,
    @Column(name = "password", length = 256)
    val password: String,

    @Column(name = "roles")
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "userid")])
    @ElementCollection(targetClass = Profile::class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    var roles: Set<Profile> = setOf()
)