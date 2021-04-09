package com.revature.autoquote

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.revature.autoquote.database.AgentDatabase
import com.revature.autoquote.database.Client
import com.revature.autoquote.database.ClientDatabase
import com.revature.autoquote.databinding.FragmentClientListBinding
import kotlinx.android.synthetic.main.layout_client_list_item.view.*


/**
 * This is the Client List fragment that lists the clients in the order that they are added to
 * the agents client list. This view also contains a SearchView that allows the agent
 * to search through the list and specify which client they are looking for.
 *
 * @author Jacob Ginn
 * @version 1.0
 */
class ClientListFragment : Fragment() , SearchView.OnQueryTextListener {

    /** This is the clientAdapter that connects to the Client List Recycler */
    private lateinit var clientAdapter : ClientRecyclerAdapter

    /** This is the binding that directly references the views that are in the fragments xml */
    private var _binding : FragmentClientListBinding? = null

    /** This is the binding that gets the _binding when it is called */
    private val binding  : FragmentClientListBinding
        get () = _binding!!

    /** This is the arrayList of clients that the agent has been employed by */
    private var clients: ArrayList<Client>? = null


    /**
     * This is the onCreateView for the Client List Fragment. This fragment will
     * list all of the clients that the agent has taken on. The agent is able to filter the results
     * with the search bar in the top right corner menu. The agent will be able to select any
     * client from the list.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        setHasOptionsMenu(true) //Setup the search view at the top of the screen

        _binding = FragmentClientListBinding.inflate(inflater,container,false)


        clients = AgentDatabase.currentAgent?.let { //The client list that the current agent has
            ClientDatabase.clientList[it.phone]
        }
        //if the client list is not null it will set up the client recycler adapter with the list
        clients?.let {
            it.reverse()
            clientAdapter = ClientRecyclerAdapter(it, this.requireContext())
            binding.recyclerView.adapter = clientAdapter
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        //This is the item touch helper that manages the swiping of right to delete the client cards
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        return binding.root
    }

    /**
     * This sets up the menu for the client list fragment. all the menu contains is a search
     * bar that the agent can use to search through clients
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(this)
    }

    /**
     * When the client hits the submit button on the search it will filter the list based on
     * the text that is in the search view.
     */
    override fun onQueryTextSubmit(query: String?): Boolean {
        clientAdapter.filter.filter(query)
        return false
    }


    /**
     * When the client is typing into the search view it will filter while they are typing
     */
    override fun onQueryTextChange(newText: String?): Boolean {
        clientAdapter.filter.filter(newText)
        return false
    }

    /**
     * sets the binding object that references the views of ClientListFragment to null
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * This is the swipehandler object that is used to detect when a client card is swiped to
     * the right. When the card is swiped it will delete the client from the clientDatabase.
     */
    val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
        /**
         * Dont need to handle this for my implementation
         */
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        /**
         * This is the function that is called when a client card is swiped to the right. This
         * method will delete the client from the database and notify the adapter that the
         *
         */
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val clientPhoneNumber = viewHolder.itemView.tvPhoneNumber.text.toString()
            clientAdapter.deleteItem(viewHolder.layoutPosition)
            ClientDatabase.deleteClient(clientPhoneNumber)
            clientAdapter.notifyDataSetChanged()
        }

    }
}