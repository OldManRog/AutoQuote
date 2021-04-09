package com.revature.autoquote.database

object AgentDatabase {

    var currentAgent: Agent? = null
    var agentList: MutableMap<String, Agent> = mutableMapOf()

    init {
//        Agent("(111) 111-1111", "11111").apply {
//            while (companies.size < 10) {
//                Companies.values().random().let { if (it !in companies) companies += it }
//            }
//            agentList[this.phone] = this
//        }
    }

    fun createAgentList(): MutableMap<String, Agent> {
        // Agent("(111) 111-1111", "11111").apply { agentList[this.phone] = this }
        //Agent("(222) 222-2222","22222").apply { agentList[this.phone] = this }
        return agentList
    }

    // check username's length and if it is empty, also it will check if phone number is already registered or not
    fun checkLogin(
        phone_Num: String,
        password: String
    ): Boolean {      // check to see if phone number is empty or less than 10
        if (phone_Num.isEmpty() || phone_Num.length < 10)
            return false
        //formatting number into (XXX) XXX-XXXX format
        var temp = phone_Num
        temp = "(" + temp.substring(0, 2) + ") " + temp.substring(3, 5) + "-" + temp.substring(6)
        //if username in database and password matches
        if (agentList.containsKey(phone_Num) && agentList[phone_Num]!!.password == password)     //TODO lets extract the return statements from the if block
            return true

        //otherwise, fail login
        return false
    }

    // when this function is called, new Agent info can be added to database  if the phone number is not already registered.
    fun addAgent(phone: String, password: String): Boolean {
        //if someone else already has that phone number
        if (agentList.containsKey(phone)) return false

        //add agent to the database
        agentList[phone] = Agent(phone, password)
        return true
    }

    //checks to see if the phone number is already registered
    fun isPhoneNumberInUse(phone: String): Boolean {
        if (agentList.containsKey(phone)) return true                 //TODO lets extract the return statements from the if block

        return false
    }

}