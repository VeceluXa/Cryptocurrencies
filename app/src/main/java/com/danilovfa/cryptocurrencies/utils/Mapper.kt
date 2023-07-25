package com.danilovfa.cryptocurrencies.utils

interface Mapper<Entity, Domain> {
    fun fromEntity(entity: Entity): Domain
    fun fromDomain(domain: Domain): Entity
}