package com.revature.autoquote.database

data class AQuote (val company:Companies = Companies.USAA, var premium:Double = 0.0, var deductible:Double = 0.0)