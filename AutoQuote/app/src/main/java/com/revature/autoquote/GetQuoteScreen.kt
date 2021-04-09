package com.revature.autoquote

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences.Editor
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.revature.autoquote.database.*
import com.revature.autoquote.databinding.FragmentGetQuoteScreenBinding
import kotlinx.android.synthetic.main.fragment_get_quote_screen.*
import java.lang.Exception
import kotlinx.android.synthetic.main.fragment_home_page.*
import java.text.NumberFormat
import java.util.*


class GetQuoteScreen : Fragment() {


    lateinit var binding: FragmentGetQuoteScreenBinding
    private lateinit var quoteAdapter: QuoteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGetQuoteScreenBinding.inflate(inflater, container, false)


        //Quote Gen is retrieving data from Quote Info
        val args: GetQuoteScreenArgs by navArgs()

        //This it a prototype of the send sms function
        //All this does is ask for the permissions, but the permissions still do not work
        binding.SendQuoteBtn.setOnClickListener {
            ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.SEND_SMS),1)
            val smsManager: SmsManager = SmsManager.getDefault()
           try{
                smsManager.sendTextMessage("+17038530779", null, "sms message", null, null)
                Toast.makeText(this.requireContext(), getString(R.string.sent_quote), Toast.LENGTH_LONG)
                .show()
            }catch (e:Exception)
            {
                e.printStackTrace()
                Toast.makeText(this.requireContext(), "Send Failed", Toast.LENGTH_LONG)
                    .show()
            }
        }



        //This loads a basic list based off the deductible of 500 (thats for the whole year)
        val myData = ArrayList<AQuote>().apply { Companies.values().forEach{
            add(it.getQuote(args.client, args.client.vehicle, 500))}}
        //these are default values for the highest per-month deductible. They are modified later
        var highDeductible:Float = 200.0f
        var highPremium:Float = 200.0f

        //runs through the quotes and records the highest deductible and premium
        for(x in myData)
        {
            if(x.premium>highPremium)highPremium = x.premium.toFloat()
            if(x.deductible>highDeductible)highDeductible= x.deductible.toFloat()
        }
        //This just setting the maximum sliders value to a little higher than the recorded maximum values
        highDeductible = ((highDeductible/50).toInt()*50+50).toFloat()
        highPremium = ((highPremium/25).toInt()*25+25).toFloat()
        //this is where we actually modify the properties of the sliders
        binding.quoteDeductibleRange.valueTo = highDeductible
        binding.quotePremiumRange.valueTo = highPremium
        binding.quoteDeductibleRange.value =  highDeductible
        binding.quotePremiumRange.values = mutableListOf<Float>(125.0f, highPremium)

        //This sets the "best" quote as the first option we get back, and sets the values of the button to it
        binding.BestQuoteButton.text =""
            //"Alec's Choice!\n" + myData[0]?.company + "\nDeductible: " + myData[0]?.deductible + "\t Premium: " + myData[0]?.premium

        val recyclerView = binding.quoteRecyclerView
        quoteAdapter = this.context?.let { QuoteListAdapter(myData, context = it) }!!
        recyclerView.adapter = quoteAdapter
        //recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = MyLinearLayoutManager(context)
        recyclerView.setHasFixedSize(false)

        binding.quotePremiumRange .addOnChangeListener{ rangeSlider: RangeSlider, _: Float, _: Boolean ->
            quoteAdapter.setFilter(
                //gets the values from the sliders, and sends them to the recycler so we can filter out quotes outside of the range
                premium_low = rangeSlider.values[0].toDouble(),
                premium_high = rangeSlider.values[1].toDouble()
            )
                //This is the actual filter call, it needed a constraint
            quoteAdapter.filter.filter("")
        }

        binding.quoteDeductibleRange .addOnChangeListener{ slider:Slider, _: Float, _: Boolean ->
            try {

                //when the deductible is changed, we have all the quotes update themselves in real time
                //Most of the code is identical to above, it is just set to be called again "onchange"
                var deductible = arrayListOf(slider.value.toInt())
                var temp = ArrayList<AQuote>()
                for (x in Companies.values())
                    temp.apply {
                        deductible.forEach {
                            add(
                                x.getQuote(
                                    args.client,
                                    args.client.vehicle,
                                    it
                                )
                            )
                        }
                    }
                val myData: ArrayList<AQuote> = temp
                var modifiedVal: Double = 0.0
                var modifiedCompany: String
                if (quoteAdapter.getFirst() != null) {
                    modifiedVal = quoteAdapter.getFirst()!!.premium ?: 0.0
                    modifiedCompany = quoteAdapter.getFirst()!!.company.name ?: ""
                }
                binding.BestQuoteButton.text =
                    "Alec's Choice!\n" + quoteAdapter.getFirst()?.company + "\nPremium: " + ((modifiedVal * 100) - (modifiedVal * 100) % 1) / 100


                quoteAdapter = this.context?.let { QuoteListAdapter(myData, context = it) }!!
                recyclerView.recycledViewPool.clear()
                recyclerView.adapter = quoteAdapter
                quoteAdapter.setFilter(
                    deductible_low = 100.0,
                    deductible_high = slider.value.toDouble(),
                    premium_low = binding.quotePremiumRange.values[0].toDouble(),
                    premium_high = binding.quotePremiumRange.values[1].toDouble()

                )
                binding.quotePremiumRange.callOnClick()
                Toast.makeText(
                    this.requireContext(),
                    "${slider.value.toDouble()}",
                    Toast.LENGTH_SHORT
                ).show()
                quoteAdapter.filter.filter("")
            }catch(e:Exception)
            {
                e.printStackTrace()
            }
        }

        //This just sets the format for how the values of the sliders appear.
        //We wanted to have them appear as currency, not flat values
        binding.quotePremiumRange.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("USD")
            format.format(value.toDouble())
        }
        binding.quoteDeductibleRange.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("USD")
            format.format(value.toDouble())
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}