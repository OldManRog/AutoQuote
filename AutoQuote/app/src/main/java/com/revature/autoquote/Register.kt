package com.revature.autoquote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.revature.autoquote.database.AgentDatabase
import com.revature.autoquote.databinding.ActivityMainBinding
import com.revature.autoquote.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.random.Random


class Register : AppCompatActivity() {

     val MIN_PASSWORD=5
    var randomValue= Random.nextInt(10000,99999)
    var randomValueString=randomValue.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //Inflate the layout for this Activity for DataBinding
        val binding=ActivityRegisterBinding.inflate(layoutInflater)
        // create 5 digit random value for one time password

        setContentView(binding.root)


        btnRegRegister.setOnClickListener {
           var eT_RegUsername=binding.etRegPhoneNumber.text.toString()
            var eT_RegPassword=binding.etRegPassword.text.toString()
            var eT_OTP=binding.etRegOneTimePass.text.toString()
                validInput()
            var validate=validInput()
            if( !binding.etRegPhoneNumber.text!!.trim().isNullOrBlank()
                &&!binding.etRegPassword.text.trim().isNullOrBlank()
                &&!binding.etRegOneTimePass.text.trim().isNullOrBlank()
                &&eT_OTP== randomValueString){
                  //var validate=validInput()
                // function called to check if input is valid or not
               if(validate){
                   AgentDatabase.addAgent(eT_RegUsername,eT_RegPassword)
                   finish()
               }


            }else{

            }


        }

        // one time Password  button
        btnRegOTP.setOnClickListener {
            // Toast to show the random number when the one-time password is clicked
            Toast.makeText(this,"  One-time  password is $randomValueString", Toast.LENGTH_LONG).show()
        }
        
    }


    // validating the input

    private fun validInput():Boolean{
        // check to see if phone number is empty
        if(etRegPhoneNumber.text.toString()==""){
            etRegPhoneNumber.error="Please Enter PhoneNumber"
            return false
        }
        // check the length of the phone number
        if(etRegPhoneNumber.text.toString().length<10){
            etRegPhoneNumber.error="Please Enter valid Phone Number"
            return false
        }
        // check to see if phone number is already taken
        if(AgentDatabase.isPhoneNumberInUse(etRegPhoneNumber.text.toString()))
        {
            etRegPhoneNumber.error="Phone number already in use"
            return false
        }
        // check to see if password is empty
        if(etRegPassword.text.toString()==""){
            etRegPassword.error="Please Enter Password"
            return false
        }
        // check the length of password
        if(etRegPassword.text.toString().length<MIN_PASSWORD){
            etRegPassword.error="Password must be minimum $MIN_PASSWORD characters."
            return false
        }
        if(etRegOneTimePass.text.toString()==""||etRegOneTimePass.text.toString()!=randomValueString){
           etRegOneTimePass.error="Please enter One_Time password "
            return false
        }else
            return true


    }

}