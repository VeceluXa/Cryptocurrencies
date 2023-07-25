package com.danilovfa.cryptocurrencies.data.local.mapper

import com.danilovfa.cryptocurrencies.data.local.model.UserEntity
import com.danilovfa.cryptocurrencies.domain.model.User
import com.danilovfa.cryptocurrencies.utils.Mapper

class UserEntityMapper : Mapper<UserEntity, User> {
    override fun fromEntity(entity: UserEntity): User {
        return User(
            firstName = entity.firstName,
            lastName = entity.lastName,
            dateOfBirth = entity.dateOfBirth
        )
    }

    override fun fromDomain(domain: User): UserEntity {
        return UserEntity(
            firstName = domain.firstName,
            lastName = domain.lastName,
            dateOfBirth = domain.dateOfBirth
        )
    }
}