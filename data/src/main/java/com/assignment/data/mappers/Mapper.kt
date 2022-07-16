package com.assignment.data.mappers

/**
 * Interface method to convert
 * data model to Domain object
 * inorder to ensure loose coupling of domain data layer
 */
interface Mapper<out O, in I> {
    fun mapToDomainLayer(input: I): O
}