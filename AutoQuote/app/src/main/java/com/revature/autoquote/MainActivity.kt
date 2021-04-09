package com.revature.autoquote

import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.SpannableStringBuilder
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.revature.autoquote.database.Agent
import com.revature.autoquote.database.AgentDatabase
import com.revature.autoquote.database.ClientDatabase
import com.revature.autoquote.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File


class MainActivity : AppCompatActivity() {

    private val FILE_NAME = "autoQuoteData.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonFile = File(applicationContext.filesDir, FILE_NAME)
        if (jsonFile.exists()) {
            val readJSONFile = jsonFile.bufferedReader().readLines()
            AgentDatabase.agentList = Json.decodeFromString(readJSONFile[1])
            ClientDatabase.clientList = Json.decodeFromString(readJSONFile[0])
        }

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //user receive error sign and message when this function called
        fun validateInput(): Boolean {
            if (etUsername.text.toString() == "") {
                etUsername.error = "Please Enter PhoneNumber"
                return false
            }
            if (etPassword.text.toString() == "") {
                etPassword.error = "Please Enter Password"
                return false
            }
            return true
        }
        // user could sign in by click on the login button,
        btnLogIn.setOnClickListener {
            val et_username = binding.etUsername.text.toString()
            val et_passsword = binding.etPassword.text.toString()

            // check to see if username or password is null or empty

            if (binding.etUsername.text != null && binding.etUsername.text!!.trim()
                    .isNotEmpty() && binding.etPassword.text.trim().isNotEmpty()
            ) {

                //check to see if user is already registered

                if (AgentDatabase.checkLogin(et_username, et_passsword)) {
                    getString(R.string.successful_login)

                    // AgentDatabase.createAgentList()
                    AgentDatabase.currentAgent = AgentDatabase.agentList[et_username]
                    val intent = Intent(this, HomeActivity::class.java)
                    binding.etPassword.text = SpannableStringBuilder("")
                    binding.etUsername.text = SpannableStringBuilder("")
                    startActivity(intent)
                } else {
                    getString(R.string.failed_login)
                    Toast.makeText(this, "Invalid PhoneNumber or Password", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                validateInput()
            }

        }

        btnLogInRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)

        }

    }



    fun isPhoneNumberValid(phoneNumber: PhoneNumberFormattingTextWatcher): Boolean {
        return Patterns.PHONE.matcher(phoneNumber.toString()).matches()
    }


}