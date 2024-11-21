package com.application.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.application.databinding.StateDetailsFragmentBinding
import com.application.viewmodel.StateListViewModel
import androidx.fragment.app.viewModels
import com.application.utils.states.GenericDataState


class StateDetailsFragment : Fragment(){

    lateinit var binding: StateDetailsFragmentBinding
    private val viewModel by activityViewModels<StateListViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StateDetailsFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerStateDetails()

    }

    private fun observerStateDetails(){

        viewModel.selectedStateDetail.observe(viewLifecycleOwner){
            if(it.size() != 0){
               val details =  it.get(0).asJsonObject
                if(details.has("name")){
                    binding.tvStateName.setText(details.get("name").asString)
                }
                if(details.has("capital")){
                    binding.tvStateCapital.setText(details.get("capital").asString)

                }
                if(details.has("population")){
                    binding.tvStatePopulation.setText(details.get("population").asString)

                }
                if(details.has("area_sq_miles")){
                    binding.tvStateArea.setText(details.get("area_sq_miles").asString)

                }
                if(details.has("region")){
                    binding.tvStateRegion.setText(details.get("region").asString)

                }
            }
        }
    }
}