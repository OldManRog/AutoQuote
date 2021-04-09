package com.revature.autoquote.database

import java.io.Serializable
@kotlinx.serialization.Serializable
data class Client(
        var firstName: String,
        var lastName: String,
        var license: String,
        var phone: String,
        var email: String,
        var street: String,
        var city: String,
        var state: String,
        var numClaims: Int = 0,
        var trackerParticipant: Boolean = false,
        var student: Boolean = false,
        var military: Boolean = false,
        var senior: Boolean = false,
        var vehicle: Vehicle = Vehicle(
                2021,
                "Honda",
                "Civic",
                18000,
                0,
                2.5
        )
): Serializable
