package com.revature.autoquote

import android.content.Context
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.revature.autoquote.database.Client
import java.util.*
import kotlin.collections.ArrayList

/**
 * This is the Client Recycler adapter that is the adapter for the client List Fragment.
 *
 * @author jjginn
 * @version 1.0
 */
class ClientRecyclerAdapter(private val clientList : ArrayList<Client>, var context:Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable{

    /** This is the client list that the filtering is done on */
    private var clientListFiltered = clientList

    /**
     * This inflates the client list item and returns a viewholder that contains the view of the
     * client list item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_client_list_item,
        parent, false)
        return ClientViewHolder(itemView)
    }

    /**
     * This function updates the viewholder contents with the client info at that given location and
     * sets the onClick listener for each of the different viewHolders.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ClientViewHolder -> {
                holder.bind(clientListFiltered[position])
                val clientID = clientListFiltered[position].phone

                //sets the card views click listener
                holder.itemView.setOnClickListener {
                    //This is the navigation action that goes to the quote info screen
                    val action = ClientListFragmentDirections
                        .actionClientListFragmentToQuoteInformation(clientPhoneNumber = clientID)
                    holder.itemView.findNavController().navigate(action)
                }
            }
        }
    }

    /**
     * gets the size of the current list
     */
    override fun getItemCount(): Int {
        return  clientListFiltered.size
    }


    /**
     * This is the inner class Client View Holder that represents the Client List items that are
     * recycled in the recycler view.
     */
    inner class ClientViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        /** The Textview that holds the clients fuill name */
        private val clientName : TextView = itemView.findViewById(R.id.tvFullName)

        /** The Textview that holds the clients email */
        private val clientEmail : TextView = itemView.findViewById(R.id.tvEmailAddress)

        /** The Textview that holds the clients phone number*/
        private val clientPhoneNumber : TextView = itemView.findViewById(R.id.tvPhoneNumber)

        /**
         * This function binds all of the necessary client data to each of the respective
         * views that hold the information.
         *
         * EX: Client first and last name -> clientName (Textview)
         *
         * @param client - the current client that we are making the card for
         */
        fun bind(client: Client){
            clientName.text = itemView.context.getString(R.string.combnine_name,client.firstName, client.lastName)
            clientEmail.text = client.email
            clientPhoneNumber.text = PhoneNumberUtils.formatNumber(client.phone, "US") // This is the phone format for the display
        }
    }

    /**
     * Deletes the client at the specified position
     */
    fun deleteItem(position: Int){
        clientListFiltered.removeAt(position)
        notifyDataSetChanged()
    }

    /**
     * This is the implementation of the filter that is called whenever the user enters characters
     * into the search view.
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            /**
             * This is the filtering function that filters the client list based on the string that
             * is put into the search view.
             */
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchString = constraint.toString()
                if (searchString.isEmpty()){ //if the search menu is empty we need the whole list
                    clientListFiltered = clientList
                }else{
                    //the list that all of the items that match the filter will go in to
                    val filterList = ArrayList<Client>()
                    clientListFiltered.forEach{ row ->
                        if (row.firstName.toLowerCase(Locale.ROOT).trim()
                                .contains(searchString.toLowerCase(Locale.ROOT))//if the first name contains the string
                            || row.lastName.toLowerCase(Locale.ROOT).trim()
                                .contains(searchString.toLowerCase(Locale.ROOT))) {//if the last name contains the string
                            filterList.add(row)
                        }
                    }
                    //the filtered list becomes client filtered list
                    clientListFiltered = filterList
                }
                val filterResults = FilterResults()
                filterResults.values = clientListFiltered
                return filterResults
            }

            /**
             * This is called after the filter has sorted the data by the constraint
             * (which is the characters that are in the search view). It will then check
             * if the list that is returned is of the correct type and if it is it will set the new
             * list to be the data and notify the adapter that the data has been changed.
             */
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.values is ArrayList<*>) {
                    clientListFiltered = results.values as ArrayList<Client>
                    notifyDataSetChanged()
                }
            }
        }
    }
}


