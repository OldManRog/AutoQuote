package com.revature.autoquote.database

import org.joda.time.DateTime
import java.io.Serializable

@kotlinx.serialization.Serializable
class Vehicle(
    var year: Int,
    var make: String,
    var model: String,
    var msrp: Int,
    var accessoryValue: Int,
    var displacement: Double,
    var fuelType: String = "gas"
): Serializable {
    /** gets age of car by subtracting its year from the current year */
    val age: Int
        get() = DateTime().year - year

    /** returns depreciation percentage as a function of the car's age in years */
    val depreciation: Double
        get() = if (age in 0..9) .05 * age else .5

    /** returns vehicle's actual cash value based on it's age and its original value
     *  (msrp + accessories)
     */
    val ACV: Double
        get() = (msrp + accessoryValue) * (1 - depreciation)
}