package com.revature.autoquote

import android.os.Bundle
import android.telephony.*
import android.util.Log
import android.util.Patterns.*
import android.widget.*
import android.view.*

import androidx.fragment.app.*
import androidx.core.view.*
import androidx.navigation.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_new_client.*

import com.revature.autoquote.database.*
import com.revature.autoquote.databinding.FragmentNewClientBinding

/**
 * The class NewClient takes the user input from the fields in the new_client page and populates
 * them into an instance of the Client class.  I then passes this Client object to the
 * DatabaseManager for further handling.
 */
class NewClientFragment : Fragment() {

    lateinit var binding: FragmentNewClientBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment for DataBinding
        binding = FragmentNewClientBinding.inflate(inflater, container, false)
//        binding.spnState.

        /**
         * Calls registerClient() when the Add New Client button is clicked and attempts to add
         * the new Client to the ClientDatabase
         */
        binding.btnNewClient.setOnClickListener { view ->
            // Create an instance of Client -- not sure where to send it yet
            if (AgentDatabase.currentAgent == null)
                return@setOnClickListener

            //registerClient() returns null if there are invalid new client fields
            //so the .let block only executes if all fields are valid
            registerClient()?.let { client ->
                // add the client to the database
                ClientDatabase.addClient(client)
                //navigate to Quote Settings page for the new Client
                view.findNavController().navigate(NewClientFragmentDirections.actionNewClientToClientListFragment())
                    //.navigate(NewClientFragmentDirections.actionNewClientToQuoteInformation(client.phone))
            }
        }

        return binding.root
    }

    /**
     * This function is called by clicking "ADD CLIENT".  It creates an inline instance of Client
     * using the user input from the New Client page or returns null if it finds any empty fields,
     * invalid fields, or a phone number that is already in the system.
     */
    fun registerClient(): Client? {

        // create an empty ArrayList to hold all the invalid fields
        val invalidFields: MutableList<EditText> = ArrayList()

        //Iterate through all the views in current layout and check whether they're blank
        flattenViews(requireView()).forEach { view ->
            if (view is EditText && view.text.toString().isBlank())
                invalidFields.add(view)
        }

        // check to confirm the phone number is valid
        if (etPhone.text!!.length < 14)
            invalidFields.add(etPhone)

        // check to confirm the email address is valid
        if (!EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches())
            invalidFields.add(etEmail)


        invalidFields.distinct().forEach { it.error = "Please enter valid ${it.hint}" }

        //Also check to make sure phone number is not already in use
        AgentDatabase.currentAgent?.let { ClientDatabase.clientList[it.phone]?.let { clients ->
                for (client in clients) {
                    if (!PhoneNumberUtils.compare(client.phone, etPhone.text.toString()))
                        continue
                    etPhone.error = "This phone number is already in use by another client"
                    return null
                }
        }}

        // If there are any invalid fields return null Client
        if (invalidFields.isNotEmpty())
            return null

        // Returns an instance of Client with all the information from the new client page
        return Client(
            etFirstName.text.toString(),
            etLastName.text.toString(),
            etLicense.text.toString(),
            etPhone.text.toString(),
            etEmail.text.toString(),
            etStreet.text.toString(),
            etCity.text.toString(),
            spnState.selectedItem.toString()
        )
    }
}

/**
 * This fun takes in a View and returns an exhaustive ArrayList of itself and all (if any) of
 * its nested views
 */
fun flattenViews(view: View): List<View> {
    if (view !is ViewGroup)
        return arrayListOf(view)

    return arrayListOf<View>(view).apply { view.forEach { addAll(flattenViews(it)) } }
}