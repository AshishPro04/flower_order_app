package com.example.flowera

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.flowera.databinding.FragmentFlowerCountBinding


class FlowerCountFragment : Fragment() {
    private var binding: FragmentFlowerCountBinding? = null
    val sharedViewModel: FlowerViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TESTX$", "View created")
        // Inflate the layout for this fragment
        binding = FragmentFlowerCountBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            flowerCountFragment = this@FlowerCountFragment
        }
        sharedViewModel.setUpdateName { currentFlower() }

    }



    override fun onDestroy() {
        super.onDestroy()
        sharedViewModel.prevFlower()
        Log.d("TESTX", "Destroy is called")
    }

    fun cancelOrder(){
        sharedViewModel.resetOrder()
        val action =  FlowerCountFragmentDirections.actionFlowerCountFragmentToStartFragment()
        findNavController().navigate(action)
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

    fun nextClick() {
        if (sharedViewModel.nextFlower()){
            val action = FlowerCountFragmentDirections.actionFlowerCountFragmentSelf()
            findNavController().navigate(action)
        } else {
            val action = FlowerCountFragmentDirections.actionFlowerCountFragmentToFlowerOrderFragment()
            findNavController().navigate(action)
        }

    }
}