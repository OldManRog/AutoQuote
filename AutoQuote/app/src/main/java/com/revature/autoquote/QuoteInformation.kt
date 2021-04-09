package com.revature.autoquote

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.*
import com.revature.autoquote.database.*
import com.revature.autoquote.databinding.FragmentQuoteInformationBinding
import kotlinx.android.synthetic.main.fragment_quote_information.*

class QuoteInformation : Fragment() {

    //set ups initialization of client object type Client
    lateinit var client: Client

    //This creates a _binding that is null.
    private var _binding: FragmentQuoteInformationBinding? = null

    //This creates a binding that is FragmentQuoteInformationBinding which only gets if it is not null.
    private val binding: FragmentQuoteInformationBinding
        get() = _binding!!

    //This sets up the onCreateView and then will inflate the fragment.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuoteInformationBinding.inflate(inflater, container, false)

        //When the swYesorNo switch is toggled, it calls the pastClaim. The pastClaim makes the DDClaims dropdown visible/Invisible
        binding.swYesorNO.setOnClickListener { pastClaim() }

        //This retrieves the arguments through the nav direction
        val args: QuoteInformationArgs by navArgs()

        //This gives me the client in question based on the phone number of the client
        client = AgentDatabase.currentAgent?.let {
            it.phone.run {
                ClientDatabase.clientList[this]
            }
        }?.find { it.phone == args.clientPhoneNumber }!!


        //This creates the Spinner for Model & Make and handles the dependent behavior
        createMakeAndModelSpinner()

        //This btn when click will check for valid data, updateClientVehicleData, updateClientDiscounts
        binding.BtnViewQuotes.setOnClickListener {

            val validateInfo = validateInput()
            if (validateInfo) {
                updateClientVehicleData()
                updateClientDiscountData()

                //this is the action that sents the data to the Quote Generator
                val action = QuoteInformationDirections.actionQuoteInformationToGetQuoteScreen(client)
                findNavController().navigate(action);
            } else {
                Toast.makeText(requireContext(), "Please enter information", Toast.LENGTH_LONG)
                    .show()
            }
        }

        //This will auto-populate the data from the client including the vehicle data.
        autoPopulateClientData()

        return binding.root
    }

    //This functions updates the vehicle object of the client object
    private fun updateClientVehicleData() {
        client.vehicle = Vehicle(
            DDyear.selectedItem.toString().toInt(),
            DDmake.selectedItem.toString(),
            DDmodel.selectedItem.toString(),
            ETmsrp.text.toString().toInt(),
            ETaccessories.text.toString().toInt(),
            ETengineDisplacement.text.toString().toDouble(),
            DDfuelType.selectedItem.toString()
        )
    }
//this function updates the discount fields for the client
    private fun updateClientDiscountData() {
        client.apply {
            student = CBStudent.isChecked
            senior = CBSenior.isChecked
            military = CBmilitary.isChecked
            trackerParticipant = CBubi.isChecked
            numClaims = DDamtOfClaims.selectedItemPosition
        }
    }
//this function will auto-populate the fields in the vehicle info
    private fun autoPopulateClientData() {
        client.apply {
            binding.ETLocationName.text = SpannableStringBuilder("$city, $state")
            binding.ETnameQuoteInfo.text =
                SpannableStringBuilder("$firstName  $lastName")
            binding.ETphoneQuoteInfo.text = SpannableStringBuilder(phone)

            //This grabs the position of the specific make w/n the make_arr
            val makePosition = resources.getStringArray(R.array.make_arr).indexOf(vehicle.make)

            //This sets the selection to the position of the clients vehicle make
            binding.DDmake.setSelection(makePosition)

            //This grabs the index of the vehicle model based on the the model array that is retrieved from the model array
            binding.apply {
                DDmodel.setSelection(getModelArray(makePosition).indexOf(vehicle.model))
                CBStudent.isChecked = student
                CBSenior.isChecked = senior
                CBmilitary.isChecked = military
                CBubi.isChecked = trackerParticipant
                DDamtOfClaims.setSelection(numClaims)
                DDyear.setSelection(
                    resources.getStringArray(R.array.Year_arr).indexOf(vehicle.year.toString())
                )
                ETmsrp.text = SpannableStringBuilder(vehicle.msrp.toString())
                ETengineDisplacement.text = SpannableStringBuilder(vehicle.displacement.toString())
                ETaccessories.text = SpannableStringBuilder(vehicle.accessoryValue.toString())
                DDfuelType.setSelection(
                    resources.getStringArray(R.array.fuel_arr).indexOf(vehicle.fuelType)
                )
            }
        }
    }

    private fun createMakeAndModelSpinner() {

        binding.DDmake.adapter = context?.let {
            ArrayAdapter(
                it, R.layout.notselecteditemspinner, resources.getStringArray(R.array.make_arr)
            )
        }

        binding.DDmake.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //This assigns the models to based on the index of the parent
                val models = when (position) {
                    0 -> resources.getStringArray(R.array.model_Audi_arr)
                    1 -> resources.getStringArray(R.array.model_BMW_arr)
                    2 -> resources.getStringArray(R.array.model_dodge_arr)
                    3 -> resources.getStringArray(R.array.model_honda_arr)
                    4 -> resources.getStringArray(R.array.model_Jeep_arr)
                    5 -> resources.getStringArray(R.array.model_nissan_arr)
                    6 -> resources.getStringArray(R.array.model_mercedes_arr)
                    7 -> resources.getStringArray(R.array.model_Toyota_arr)
                    8 -> resources.getStringArray(R.array.model_Volkswagen_arr)
                    else -> arrayOf("")
                }
                //Model spinner is initialized based on the given model, based on the make.
                binding.DDmodel.adapter = context?.let {
                    ArrayAdapter(it, R.layout.notselecteditemspinner, models)
                }
                //If client.vehicle.make is equal to the position given from parent,
                // then model will set the selection based on on the index of the client.
                if (client.vehicle.make == parent?.getItemAtPosition(position).toString())
                    binding.DDmodel.setSelection(models.indexOf(client.vehicle.model), true)
            }
        }

        binding.DDmodel.adapter = context?.let {
            ArrayAdapter(
                it, R.layout.notselecteditemspinner,
                resources.getStringArray(R.array.model_mercedes_arr)
            )
        }
    }

    private fun validateInput(): Boolean {
        var isValid: Boolean = true


        if (ETengineDisplacement.text.toString()
                .toDoubleOrNull() == null || ETengineDisplacement.text.toString().toDouble() <= 0.0
        ) {
            ETengineDisplacement.error = "Please Enter Valid Engine Displacement"
            isValid = false
        }

        if (ETmsrp.text.toString().toIntOrNull() == null) {
            ETmsrp.error = "Please Enter Valid MSRP"
            isValid = false
        }

        if (ETaccessories.text.toString().toIntOrNull() == null) {
            ETaccessories.error = "Please Enter Valid Add On Cost"
            isValid = false
        }
        return isValid
    }

    private fun pastClaim() {
        _binding?.pastClaims?.visibility = if (binding.swYesorNO.isChecked)
            View.VISIBLE else View.INVISIBLE
    }


    //this function takes in a position of type Int, based on the position of the make, it will chose the model.
    private fun getModelArray(position: Int): Array<out String> {

        return when (position) {
            0 -> resources.getStringArray(R.array.model_Audi_arr)
            1 -> resources.getStringArray(R.array.model_BMW_arr)
            2 -> resources.getStringArray(R.array.model_dodge_arr)
            3 -> resources.getStringArray(R.array.model_honda_arr)
            4 -> resources.getStringArray(R.array.model_Jeep_arr)
            5 -> resources.getStringArray(R.array.model_nissan_arr)
            6 -> resources.getStringArray(R.array.model_mercedes_arr)
            7 -> resources.getStringArray(R.array.model_Toyota_arr)
            8 -> resources.getStringArray(R.array.model_Volkswagen_arr)
            else -> arrayOf("")

        }
    }
}