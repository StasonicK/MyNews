package com.eburg_soft.mynews.core

interface BaseMapper<in A, out B> {

    fun map(type: A?): B
}