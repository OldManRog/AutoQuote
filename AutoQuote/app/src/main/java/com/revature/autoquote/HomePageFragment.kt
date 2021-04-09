package com.revature.autoquote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.revature.autoquote.database.AgentDatabase
import com.revature.autoquote.databinding.FragmentHomePageBinding

/**
 * This is the Homepage fragment, it is the home destination of our nav_graph.
 *
 * @author jjginn
 * @version 1.0
 */
class HomePageFragment : Fragment() {

    /** This is the view binding that is made to reference the views of the Fragment Home Page */
    private var _binding  : FragmentHomePageBinding? = null

    /** This is the binding that retrieves the binding to reference the views */
    private val binding :FragmentHomePageBinding
        get() = _binding!!

    /**
     * This is the method that inflates the fragment and sets up the binding to reference
     * the views that are contained in the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        /** The binding that directly references the views in a fragment */
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)

        binding.clientListBtn.setOnClickListener { findNavController().navigate(R.id.action_homePage_to_clientListFragment) }

        binding.logOutBtn.setOnClickListener {
            AgentDatabase.currentAgent = null
            this.activity?.finish()
        }

        binding.newClientBtn.setOnClickListener { findNavController().navigate(R.id.action_homePage_to_newClient) }
        return binding.root
    }
}