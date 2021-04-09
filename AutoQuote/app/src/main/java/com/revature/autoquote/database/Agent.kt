package com.revature.autoquote.database

import kotlinx.serialization.Serializable

@Serializable
data class Agent(
     val phone: String="",
     val password:String="",
     val companies:ArrayList<Companies> = ArrayList()
)