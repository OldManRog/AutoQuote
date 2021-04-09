package com.revature.autoquote.database

import com.revature.autoquote.R.mipmap.*
import kotlin.collections.ArrayList
import kotlin.math.*

//this is a container for the parts of each company's algorithm that are unique to them
enum class Companies(
    val imageResourceId: Int,
    val kDeductible: Int,
    val kArea: Double,
    val kEngine: Double,
    val kRecord: Double,
    val discountRate: Double,
    val baseLiability: Int = 80
) {
    STATE_FARM(state_farm,4823, 0.016, 0.029, 0.020, 0.022, 95),
    PROGRESSIVE(progressive, 2577, 0.012, 0.018, 0.015, 0.034, 85),
    ALLSTATE_CORP(allstate, 2305, 0.012, 0.022, 0.020, 0.042, 91),
    USAA(usaa, 3080, 0.017, 0.026, 0.002, 0.009, 86),
    LIBERTY_MUTUAL(liberty_mutual, 2314, 0.019, 0.025, 0.014, 0.009, 125),
    FARMERS_INSURANCE_GROUP(farmers, 3243, 0.009, 0.008, 0.013, 0.021, 122),
    NATIONWIDE(nationwide,1207, 0.011, 0.024, 0.006, 0.024, 87),
    AMERICAN_FAMILY_INSURANCE_GROUP(american_family, 3003, 0.012, 0.022, 0.004, 0.023, 95),
    TRAVELERS(travelers, 2906, 0.021, 0.008, 0.002, 0.045, 127),
    AUTO_CLUB_EXCHANGE(auto_club, 4338, 0.012, 0.016, 0.024, 0.048, 81),
    ERIE_INSURANCE(erie, 3452, 0.013, 0.030, 0.024, 0.034, 95),
    KEMPER(kemper, 3308, 0.004, 0.014, 0.004, 0.037, 111),
    NATIONAL_GENERAL_HOLDINGS_CORP(national_general, 4609, 0.013, 0.001, 0.022, 0.015, 96),
    CSAA_INSURANCE_EXCHANGE(csaa, 2318, 0.015, 0.033, 0.021, 0.001, 88),
    AUTO_OWNERS_INSURANCE(auto_owners, 1706, 0.019, 0.025, 0.028, 0.047, 90),
    MERCURY_INSURANCE(mercury, 1702, 0.012, 0.017, 0.030, 0.027, 107),
    METLIFE(metlife, 1547, 0.014, 0.022, 0.021, 0.043, 121),
    THE_HARTFORD(hartford, 3541, 0.002, 0.004, 0.006, 0.040, 106),
    AMICA(amica,3722, 0.027, 0.017, 0.025, 0.034, 84),
    THE_HANOVER_INSURANCE_GROUP(hanover,1628, 0.013, 0.004, 0.004, 0.005, 115),
    COUNTRY_FINANCIAL(country_financial, 4530, 0.006, 0.021, 0.021, 0.050, 108),
    NJM_INSURANCE(njm, 3510, 0.001, 0.030, 0.000, 0.006, 107),
    SOUTHERN_FARM_BUREAU_CASUALTY(southern_farm_bureau, 3211, 0.029, 0.025, 0.028, 0.005, 124),
    SENTRY(sentry, 3959, 0.030, 0.021, 0.012, 0.044, 129),
    SHELTER_INSURANCE(shelter, 4941, 0.015, 0.023, 0.002, 0.044, 128),
    ALFA_MUTUAL_GROUP(alfa_mutual, 4011, 0.015, 0.024, 0.027, 0.043, 97),
    AMERIPRISE_FINANCIAL(ameriprise, 1275, 0.016, 0.016, 0.009, 0.040, 111),
    CHUBB(chubb, 2423, 0.023, 0.032, 0.006, 0.002, 104),
    TEXAS_FARM_BUREAU(texas_farm_bureau, 3070, 0.022, 0.009, 0.002, 0.026, 101),
    TENNESSEE_FARM_BUREAU(tennessee_farm_bureau, 4261, 0.022, 0.002, 0.030, 0.018, 92),
    PLYMOUTH_ROCK_OF_NEW_JERSEY(plymouth_rock_nj, 3239, 0.016, 0.001, 0.027, 0.013, 100),
    THE_CINCINNATI_INSURANCE(cincinnati, 2323, 0.026, 0.020, 0.014, 0.029, 104),
    NORTH_CAROLINA_FARM_BUREAU(north_carolina_farm_bureau, 4449, 0.023, 0.030, 0.019, 0.048, 121),
    KENTUCKY_FARM_BUREAU(kentucky_farm_bureau, 3233, 0.006, 0.025, 0.005, 0.029, 88),
    STATE_AUTO(state_auto, 4338, 0.021, 0.005, 0.013, 0.046, 102),
    GRANGE_INSURANCE(grange, 2977, 0.029, 0.007, 0.001, 0.012, 87),
    AMERICAN_NATIONAL(american_national, 1815, 0.016, 0.012, 0.011, 0.040, 87),
    LOYA_INSURANCE(loya, 4086, 0.020, 0.009, 0.001, 0.013, 113),
    FARM_BUREAU_FINANCIAL(farm_bureau_financial_services, 5085, 0.009, 0.026, 0.020, 0.040, 89),
    SAFETY_INSURANCE(safety_insurance, 1954, 0.007, 0.018, 0.002, 0.042, 108),
    WAWANESA_GENERAL(wawanesa, 3624, 0.026, 0.023, 0.027, 0.036, 88),
    HORACE_MANN(horace_mann, 4382, 0.021, 0.018, 0.021, 0.020, 109),
    MARKEL(markel, 1421, 0.030, 0.000, 0.022, 0.014, 127),
    MICHIGAN_FARM_BUREAU(michigan_farm, 5018, 0.020, 0.012, 0.021, 0.001, 93),
    PLYMOUTH_ROCK(plymouth_rock_nj, 4812, 0.016, 0.010, 0.017, 0.010, 94),
    HOME_STATE_INSURANCE_GROUP(home_state, 5097, 0.023, 0.026, 0.027, 0.032, 82),
    AMERICAN_ACCESS_CASUALTY(american_access, 5187, 0.002, 0.019, 0.025, 0.045, 113);


    fun getQuote(
        client: Client,
        car: Vehicle,
        deductible: Int
    ): AQuote {           //TODO we can actually set comprehensive (full-coverage) vs liability only in-line here
        car.run {
            client.run {                                      //TODO we may change input param to phone
                return AQuote(this@Companies,
              (1.1 * ACV.pow(.7) + baseLiability)*
                        bigCityMultiplier(Pair(city, state))*
                        historyMultiplier(numClaims)*
                        (1 - discount(this))*
                        (500 + kDeductible)/(deductible + kDeductible)/6,
                        deductible.toDouble())
            }
        }
    }


    fun getQuote(client: Client,
                 vehicle: Vehicle,
                 deductible: List<Int>
    ): List<AQuote> {

        return ArrayList<AQuote>().apply { deductible.forEach { add(getQuote(client, vehicle, it)) } }
    }

    /** returns company-designated multiplier if Client lives in a big city or 1 otherwise */
    fun bigCityMultiplier(city: Pair<String, String>): Double =
        if (city in bigCities) 1 + kArea else 1.0

    /** generates a multiplier to reflect the number of claims in the Client's driving
     * history based on the coefficient designated by the Company
     */
    fun historyMultiplier(numClaims: Int) = exp(kRecord * numClaims)

    /** generates a nominal discount percentage based on participation in drive tracking program
     *  as well as status as active or retired military, as a student, or as a senior
     */
    fun discount(client: Client): Double {
        fun inc(x: Double) =
            1 - (1 - x) * (1 - discountRate)

        var disc = 0.0

        client.run {
            if (trackerParticipant) disc = inc(disc)
            if (military) disc = inc(disc)
            if (student) disc = inc(disc)
            if (senior) disc = inc(disc)
        }

        return disc
    }

    /** returns the multiplier set by the company based on the vehicle's engine displacement */
    fun engineMultiplier(displacement: Double): Double = exp(kEngine * (displacement - 1))
}

// this is a list of the 100 largest US cities -- a Client pays more if they live here
val bigCities = arrayListOf(
    Pair("New York City", "New York"),
    Pair("Los Angeles", "California"),
    Pair("Chicago", "Illinois"),
    Pair("Houston", "Texas"),
    Pair("Phoenix", "Arizona"),
    Pair("Philadelphia", "Pennsylvania"),
    Pair("San Antonio", "Texas"),
    Pair("San Diego", "California"),
    Pair("Dallas", "Texas"),
    Pair("Austin", "Texas"),
    Pair("San Jose", "California"),
    Pair("Fort Worth", "Texas"),
    Pair("Jacksonville", "Florida"),
    Pair("Columbus", "Ohio"),
    Pair("Charlotte", "North Carolina"),
    Pair("Indianapolis", "Indiana"),
    Pair("San Francisco", "California"),
    Pair("Seattle", "Washington"),
    Pair("Denver", "Colorado"),
    Pair("Washington", "District of Columbia"),
    Pair("Boston", "Massachusetts"),
    Pair("El Paso", "Texas"),
    Pair("Nashville", "Tennessee"),
    Pair("Oklahoma City", "Oklahoma"),
    Pair("Las Vegas", "Nevada"),
    Pair("Detroit", "Michigan"),
    Pair("Portland", "Oregon"),
    Pair("Memphis", "Tennessee"),
    Pair("Louisville", "Kentucky"),
    Pair("Milwaukee", "Wisconsin"),
    Pair("Baltimore", "Maryland"),
    Pair("Albuquerque", "New Mexico"),
    Pair("Tucson", "Arizona"),
    Pair("Mesa", "Arizona"),
    Pair("Fresno", "California"),
    Pair("Sacramento", "California"),
    Pair("Atlanta", "Georgia"),
    Pair("Kansas City", "Missouri"),
    Pair("Colorado Springs", "Colorado"),
    Pair("Raleigh", "North Carolina"),
    Pair("Omaha", "Nebraska"),
    Pair("Miami", "Florida"),
    Pair("Long Beach", "California"),
    Pair("Virginia Beach", "Virginia"),
    Pair("Oakland", "California"),
    Pair("Minneapolis", "Minnesota"),
    Pair("Tampa", "Florida"),
    Pair("Tulsa", "Oklahoma"),
    Pair("Arlington", "Texas"),
    Pair("Wichita", "Kansas"),
    Pair("Bakersfield", "California"),
    Pair("Aurora", "Colorado"),
    Pair("New Orleans", "Louisiana"),
    Pair("Cleveland", "Ohio"),
    Pair("Anaheim", "California"),
    Pair("Henderson", "Nevada"),
    Pair("Honolulu", "Hawaii"),
    Pair("Riverside", "California"),
    Pair("Santa Ana", "California"),
    Pair("Corpus Christi", "Texas"),
    Pair("Lexington", "Kentucky"),
    Pair("San Juan", "Puerto Rico"),
    Pair("Stockton", "California"),
    Pair("St. Paul", "Minnesota"),
    Pair("Cincinnati", "Ohio"),
    Pair("Greensboro", "North Carolina"),
    Pair("Pittsburgh", "Pennsylvania"),
    Pair("Irvine", "California"),
    Pair("St. Louis", "Missouri"),
    Pair("Lincoln", "Nebraska"),
    Pair("Orlando", "Florida"),
    Pair("Durham", "North Carolina"),
    Pair("Plano", "Texas"),
    Pair("Anchorage", "Alaska"),
    Pair("Newark", "New Jersey"),
    Pair("Chula Vista", "California"),
    Pair("Fort Wayne", "Indiana"),
    Pair("Chandler", "Arizona"),
    Pair("Toledo", "Ohio"),
    Pair("St. Petersburg", "Florida"),
    Pair("Reno", "Nevada"),
    Pair("Laredo", "Texas"),
    Pair("Scottsdale", "Arizona"),
    Pair("North Las Vegas", "Nevada"),
    Pair("Lubbock", "Texas"),
    Pair("Madison", "Wisconsin"),
    Pair("Gilbert", "Arizona"),
    Pair("Jersey City", "New Jersey"),
    Pair("Glendale", "Arizona"),
    Pair("Buffalo", "New York"),
    Pair("Winston-Salem", "North Carolina"),
    Pair("Chesapeake", "Virginia"),
    Pair("Fremont", "California"),
    Pair("Norfolk", "Virginia"),
    Pair("Irving", "Texas"),
    Pair("Garland", "Texas"),
    Pair("Paradise", "Nevada"),
    Pair("Arlington", "Virginia"),
    Pair("Richmond", "Virginia"),
    Pair("Hialeah", "Florida")
)           //TODO do I want this to just go in the enum? Or maybe in its own file?


