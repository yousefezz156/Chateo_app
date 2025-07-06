package com.example.chateo_app.DataNumbers

open class NumbersList {
    open fun numList():List<Numbers>{
        val NumberLists = mutableListOf<Numbers>()

        NumberLists.add(Numbers("\uD83C\uDDF5\uD83C\uDDF8", "+970"))
        NumberLists.add(Numbers("\uD83C\uDDEA\uD83C\uDDEC", "+20"))
        NumberLists.add(Numbers("\uD83C\uDDFA\uD83C\uDDF8", "+1"))
        NumberLists.add(Numbers("\uD83C\uDDEC\uD83C\uDDE7", "+44"))

        return NumberLists
    }
}