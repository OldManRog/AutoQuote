package com.revature.autoquote

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.NumberFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.revature.autoquote.database.*
import java.util.*
import kotlin.collections.ArrayList

//TODO get rid of these dummies
val dummyVehicle = Vehicle(2018, "Nissan", "Altima", 22000, 500, 2.5, "gas")
val dummyClient = Client("Alec", "Ramirez", "12345678", "(210) 123-4567", "alec@rev.net", "12345 Alec St", "San Antonio", "TX", 4, true, true, false, false)

class QuoteListAdapter(private val dataset : List<AQuote> = Companies.STATE_FARM.getQuote(dummyClient, dummyVehicle, arrayListOf(250,500,1000)),
                       var context:Context) : RecyclerView.Adapter<QuoteListAdapter.QuoteViewHolder>(), Filterable
{
    private var lowPremium=125.0
    private var highPremium=2800.0
    private var lowDeductible=100.0
    private var highDeductible=1000.0
    private var filteredQuotes = dataset

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.quote_list_item,
       parent, false)

        return QuoteViewHolder(itemView, context)
    }

    override fun getItemCount(): Int {
       return filteredQuotes.size
    }

    override fun onBindViewHolder(holder:QuoteViewHolder, position: Int) {


        //This binds the items to their positions
        when(holder){
            else -> {
               if(filteredQuotes!= null && filteredQuotes.size> position &&filteredQuotes[position]!=null){
                   holder.bind(filteredQuotes[position])
               }
                if(holder.itemView !=null) {
                    holder.itemView.setOnClickListener {
                        //This is for clicking on a quote, as the function could not be implemented, all that shows up is a toast
                        Toast.makeText(this.context, "Quote Sent", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    fun setFilter(premium_low:Double=this.lowPremium, premium_high:Double=this.highPremium,
                  deductible_low:Double=this.lowDeductible, deductible_high:Double=this.highDeductible)
    {

        //This sets the low and high values for the permium and deductibles
        //this is used later to filter items when the item's premium or deductible is too high/low
        lowPremium=premium_low
        highPremium=premium_high
        lowDeductible=deductible_low
        highDeductible=deductible_high

    }

    //helper function to get the first quote
    fun getFirst():AQuote?
    {
        if(filteredQuotes.isEmpty())
            return null
        return filteredQuotes[0]
    }
   override fun getFilter():Filter
   {
       //This is Jacob's code, I just copied it over
       //This creates and returns a filter object
       return object: Filter(){
           override fun performFiltering(constraint: CharSequence?): FilterResults {

               //Go through the list, if the value is within the bounds, add it to the temporary list
                    val filterList = ArrayList<AQuote>()
                    for (row in dataset) {
                        if (row.premium in lowPremium..highPremium
                            && row.deductible in lowDeductible..highDeductible) {
                            filterList.add(row)
                        }
                    filteredQuotes = filterList
                }
               //return the filtered results
               val filterResults = FilterResults()
               filterResults.values = filteredQuotes
               return filterResults
           }
           override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
               filteredQuotes = results?.values as ArrayList<AQuote>
               notifyDataSetChanged()
           }
       }
   }


    inner class QuoteViewHolder (itemView: View, context:Context): RecyclerView.ViewHolder(itemView){
        private val insuranceName : TextView = itemView.findViewById(R.id.insurance_name_text)
        private val premium : TextView = itemView.findViewById(R.id.premium_text_view)
        private val image : ImageView = itemView.findViewById(R.id.insurance_image)

//This class defines an item for the recycler view

        @SuppressLint("DefaultLocale")
        fun bind(quote:AQuote){
            if(quote!=null) {
                insuranceName.text = quote.company.name.toLowerCase().split("_").map{it.capitalize()}.joinToString(" ")
                premium.text =
                    "\$%,.2f".format(quote.premium)//.substring(0, (6+quote.premium.toString().indexOfFirst {it=='.' }))
                image.apply { setImageResource(quote.company.imageResourceId) }
            }
        }
    }

}