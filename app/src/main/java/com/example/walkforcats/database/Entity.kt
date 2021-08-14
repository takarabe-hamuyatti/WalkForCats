package com.example.walkforcats.database


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Entity(
     @PrimaryKey
    var stepcount :Int
)





