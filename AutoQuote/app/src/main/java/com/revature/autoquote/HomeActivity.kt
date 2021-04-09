package com.revature.autoquote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.revature.autoquote.database.Agent
import com.revature.autoquote.database.AgentDatabase
import com.revature.autoquote.database.Client
import com.revature.autoquote.database.ClientDatabase
import com.revature.autoquote.databinding.ActivityHomeBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import java.io.*

/**
 * This is the home activity. It hosts the fragment that all of our fragments are hosted on.
 * This contains a drawer layout that you are able to see our information about us.
 *
 * @author Jacob Ginn
 * @version 1.0
 */
class HomeActivity : AppCompatActivity() {

    /** The file name of the JSON file that holds all of the data */
    private val FILE_NAME = "autoQuoteData.json"

    /** this is the drawer layout of for the home activity */
    private lateinit var drawerLayout : DrawerLayout

    /**
     * This is the onCreate that is called when the Home Activity is made
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)

        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)

        drawerLayout = binding.drawerLayout

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        binding.drawerLogOutBtn.setOnClickListener { finish() }
    }


    /**
     * When the back button is pressed it will reference the onSupportNavigateUp
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController,drawerLayout)
    }


    /**
     * When the application is paused it will take some time to save the clients and the agents to
     * a JSON file.
     */
    override fun onPause() {
        super.onPause()

        val jsonFile = File(applicationContext.filesDir, FILE_NAME)
        val fileWriter = FileWriter(jsonFile)
        val bufferedWriter = BufferedWriter(fileWriter)

        val clientList = Json.encodeToString(ClientDatabase.clientList)
        val agentList = Json.encodeToString(AgentDatabase.agentList)
        bufferedWriter.write(clientList + "\n"+ agentList)
        bufferedWriter.close()
    }


}