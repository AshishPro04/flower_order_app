package com.example.flowera

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.flowera.databinding.FragmentChooseBinding

class ChooseFragment : Fragment() {
    private var binding: FragmentChooseBinding? = null
    private val sharedViewModel: FlowerViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChooseBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            chooseFragment = this@ChooseFragment
        }
        Log.d("TEST12", "reset current Flower")
        sharedViewModel.setUpdateName { currentFlower() }

    }


    private fun currentFlower()  {
        when (sharedViewModel.currentFlower.value){
            1 -> sharedViewModel.setCurrentName(resources.getString(R.string.tulips))
            2 -> sharedViewModel.setCurrentName(resources.getString(R.string.roses))
            3 -> sharedViewModel.setCurrentName(resources.getString(R.string.daisies))
            4 -> sharedViewModel.setCurrentName(resources.getString(R.string.lavender) )
            else -> ""
        }
    }
    fun cancelOrder(){
        sharedViewModel.resetOrder()
        val action = ChooseFragmentDirections.actionChooseFragmentToStartFragment()
        findNavController().navigate(action)
    }
    fun nextKey(){
        sharedViewModel.resetCurrentFlower()
        if (sharedViewModel.nextFlower()){
            val action = ChooseFragmentDirections.actionChooseFragmentToFlowerCountFragment()
            findNavController().navigate(action)
        }
    }
}