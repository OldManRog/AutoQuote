package com.revature.autoquote.database

/**
 * This is the Client Database that holds a Map of an agents phone number to a list of clients that
 * the agent has registered. We can add clients and delete clients from the agents list of customers.
 *
 * @author Jacob Ginn, Liam heaney, Alec Ramirez
 */
object ClientDatabase {

    /** The map that relates an agents phone number to a list of clients */
    var clientList: MutableMap<String, ArrayList<Client>> = mutableMapOf()
    init {


//        clientList[AgentDatabase.currentAgent!!.phone] = arrayListOf<Client>().apply{
//            addAll(dummyClients)}
    }

    /**
     * This adds a client to the database.
     *
     * @param client - the client that is being registered to the current agent
     */
    fun addClient(client: Client):Boolean {
        if (clientList[AgentDatabase.currentAgent!!.phone] == null)
            clientList[AgentDatabase.currentAgent!!.phone] = arrayListOf<Client>()
        clientList[AgentDatabase.currentAgent!!.phone]?.let{
            if(!it.contains(client)){
                it.add(client)
                return true
            }
        }
        return false
    }

    /**
     * This deletes a client based on the phone number that the client holds
     *
     * @param clientPhoneNumber -  the clients phone number that needs to be deleted
     */
    fun deleteClient(clientPhoneNumber : String){
        var client : Client? = null
        clientList[AgentDatabase.currentAgent!!.phone].let { list -> //if the agent is not null
            list?.forEach {     //go through the agents list of clients and find the client that has the phonenumber
                if (it.phone.equals(clientPhoneNumber)){
                    client = it
                }
            }
            if (client != null)//if we did find the client then we need to remove it from the list
                list?.remove(client!!)
        }
    }
}

//Creates a dummy list of clients
val dummyClients:List<Client> = arrayListOf(
    Client(
        "Alec",
        "Ramirez",
        "13545653",
        "(210) 867-5309",
        "alec.ramirez@revature.net",
        "22338 N St Alec St",
        "San Antonio",
        "TX",
        2,
        true
    ),

    Client(
        "Liam",
        "Heaney",
        "11856769",
        "(386) 520-9343",
        "liam.heaney@revature.net",
        "1384 Liam Pkwy",
        "Liamville",
        "CA"
    ),

    Client(
        "Roya",
        "Askari",
        "87493823",
        "(897) 624-0176",
        "roya.askari@revature.net",
        "72549 Champion Blvd",
        "Portland",
        "OR",
        4
    ),

    Client(
        "Roger",
        "Oliva",
        "45981034",
        "(249) 578-1347",
        "roger.oliva@revature.net",
        "22645 Elm Grv",
        "Denton",
        "MD",
        1
    ),

    Client(
        "Matt",
        "Woods",
        "87223864",
        "(719) 238-4556",
        "matt.woods@revature.net",
        "6714 Kings Ct",
        "Charles Bay",
        "NC",
        3,
        true
    ),

    Client(
        "Elizabeth",
        "Camila",
        "85855660",
        "(934) 355-1007",
        "elizabeth.camila@proton.com",
        "16852 Wilderness Cove",
        "Billings",
        "CT",
        3,
        true,
        false,
        false,
        true),

    Client(
        "Logan",
        "Daniel",
        "45233893",
        "(652) 472-7966",
        "logan.daniel@ymail.com",
        "25523 Brieley Ln",
        "Norwalk",
        "MI",
        5,
        true,
        false,
        false,
        true),

    Client(
        "Madison",
        "Liam",
        "16520908",
        "(609) 587-2999",
        "madison.liam@revature.com",
        "27353 Sun Trail St",
        "Plano",
        "VT",
        3,
        true,
        true,
        false,
        false),

    Client(
        "Jacob",
        "Zoey",
        "89198798",
        "(809) 278-2551",
        "jacob.zoey@proton.com",
        "44554 Sandy Ct",
        "Waterloo",
        "OK",
        4,
        true,
        true,
        false,
        false),

    Client(
        "Benjamin",
        "Leo",
        "29062702",
        "(609) 741-8916",
        "benjamin.leo@ymail.com",
        "14628 Highland Pond",
        "Worcester",
        "IN",
        7,
        false,
        true,
        true,
        false),

    Client(
        "Charlotte",
        "Laurell",
        "42600075",
        "(616) 921-3518",
        "charlotte.laurell@gmail.com",
        "3232 Calmar Ct",
        "Duluth",
        "MD",
        6,
        false,
        false,
        false,
        true),

    Client(
        "Isaiah",
        "Isla",
        "99608841",
        "(242) 629-6384",
        "isaiah.isla@aol.com",
        "20161 Red Rock Crossing",
        "Tyler",
        "NH",
        1,
        false,
        false,
        false,
        true),

    Client(
        "Daniel",
        "Mateo",
        "75092193",
        "(622) 821-9797",
        "daniel.mateo@yahoo.com",
        "58219 Navajo Peace",
        "Dallas",
        "NC",
        2,
        true,
        false,
        true,
        true),

    Client(
        "Charlotte",
        "Michael",
        "14740131",
        "(485) 169-623",
        "charlotte.michael@proton.com",
        "80936 Ranchero Dr",
        "Brownsville",
        "NV",
        3,
        false,
        true,
        true,
        false),

    Client(
        "Penelope",
        "Laurell",
        "45687281",
        "(501) 201-7911",
        "penelope.laurell@zoho.com",
        "56301 Barbuda Dr",
        "Baltimore",
        "NC",
        7,
        true,
        false,
        true,
        true),

    Client(
        "Laurell",
        "Mia",
        "83105650",
        "(752) 231-6511",
        "laurell.mia@yahoo.com",
        "62779 Trail Village Dr",
        "Elk Grove",
        "VT",
        5,
        false,
        false,
        true,
        false),

    Client(
        "Ethan",
        "Mila",
        "51048373",
        "(833) 708-2924",
        "ethan.mila@ymail.com",
        "4407 Lofty Heights",
        "Murrieta",
        "GA",
        5,
        true,
        false,
        true,
        true),

    Client(
        "Bella",
        "Willow",
        "97591502",
        "(742) 536-8509",
        "bella.willow@revature.com",
        "86202 Dapple Dr",
        "Punta Gorda",
        "DE",
        6,
        false,
        false,
        true,
        false),

    Client(
        "Matthew",
        "Gianna",
        "71366553",
        "(483) 333-6619",
        "matthew.gianna@ymail.com",
        "6339 Kenton Croft",
        "Newark",
        "KY",
        7,
        true,
        false,
        false,
        false),

    Client(
        "Eero",
        "Abigail",
        "66567152",
        "(295) 803-5718",
        "eero.abigail@revature.com",
        "36304 Fisher's Hill Dr",
        "Clearwater",
        "NY",
        7,
        true,
        false,
        false,
        false),

    Client(
        "Amelia",
        "Kai",
        "44458272",
        "(405) 554-8008",
        "amelia.kai@proton.com",
        "3111 Broken Feather",
        "Lafayette",
        "MT",
        1,
        true,
        true,
        false,
        true),

    Client(
        "Bella",
        "Noah",
        "79706264",
        "(305) 854-6574",
        "bella.noah@revature.com",
        "74491 Willow Glen",
        "Phoenix",
        "NM",
        1,
        false,
        false,
        true,
        true),

    Client(
        "Wyatt",
        "Ezekiel",
        "60948204",
        "(752) 220-2653",
        "wyatt.ezekiel@yahoo.com",
        "50713 Frontier Sun Dr",
        "Canton",
        "IN",
        1,
        false,
        false,
        true,
        false),

    Client(
        "Ella",
        "Maverick",
        "76063961",
        "(383) 222-7122",
        "ella.maverick@yahoo.com",
        "88971 Minuteman Dr",
        "Pensacola",
        "ND",
        8,
        true,
        true,
        true,
        true),

    Client(
        "Camila",
        "Bella",
        "98301479",
        "(225) 269-4591",
        "camila.bella@gmail.com",
        "40434 Ceremony Cove",
        "Indianapolis",
        "NJ",
        3,
        false,
        true,
        false,
        false),

    Client(
        "Kai",
        "Grayson",
        "90445439",
        "(692) 882-6105",
        "kai.grayson@aol.com",
        "5097 Cumberland Rd",
        "Hollywood",
        "CA",
        3,
        false,
        true,
        false,
        false),

    Client(
        "Emily",
        "Scott",
        "83673479",
        "(710) 809-9850",
        "emily.scott@ymail.com",
        "64204 Autumn Waters",
        "Palm Bay",
        "SD",
        8,
        false,
        false,
        true,
        true),

    Client(
        "Amelia",
        "Sofia",
        "80879879",
        "(929) 516-6422",
        "amelia.sofia@proton.com",
        "20264 Elk Mountain",
        "Salinas",
        "KS",
        6,
        false,
        false,
        false,
        false),

    Client(
        "Lucas",
        "Stella",
        "81946536",
        "(284) 753-9114",
        "lucas.stella@proton.com",
        "61782 Mt Hood",
        "North Charleston",
        "WV",
        8,
        false,
        true,
        false,
        false),

    Client(
        "Joseph",
        "Liam",
        "12085426",
        "(860) 877-2151",
        "joseph.liam@revature.com",
        "51003 Squaw Creek Dr",
        "Salem",
        "IL",
        5,
        true,
        true,
        false,
        true),

    Client(
        "Carter",
        "Lincoln",
        "98466337",
        "(254) 162-7888",
        "carter.lincoln@gmail.com",
        "74937 La Cieniga St",
        "Norfolk",
        "MO",
        6,
        true,
        true,
        true,
        true),

    Client(
        "Henry",
        "Michael",
        "10917451",
        "(680) 224-9661",
        "henry.michael@aol.com",
        "67213 Prescott Dam",
        "Concord",
        "WY",
        3,
        false,
        false,
        false,
        false),

    Client(
        "Daniel",
        "Aria",
        "64283390",
        "(214) 488-11",
        "daniel.aria@proton.com",
        "3560 Sylhet View",
        "Santa Ana",
        "OK",
        6,
        true,
        false,
        true,
        false),

    Client(
        "Hannah",
        "Joseph",
        "95294340",
        "(734) 637-1408",
        "hannah.joseph@zoho.com",
        "38403 Chicago Blvd",
        "Pomona",
        "WV",
        3,
        true,
        false,
        false,
        true),

    Client(
        "Asher",
        "Eliana",
        "62924729",
        "(640) 795-5048",
        "asher.eliana@gmail.com",
        "23047 Saratoga Coach",
        "Honolulu",
        "KY",
        8,
        false,
        false,
        true,
        false),

    Client(
        "Jackson",
        "Zoey",
        "44361982",
        "(770) 394-4780",
        "jackson.zoey@icloud.com",
        "55919 Donella Dr",
        "Jacksonville",
        "IL",
        1,
        false,
        true,
        false,
        false)
)