package com.example.chat.mappers

interface Mapper<I, O> {
    fun map(input: I): O
}