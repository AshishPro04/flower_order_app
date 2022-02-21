package com.example.flowera

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.flowera.databinding.FragmentFlowerOrderBinding


class FlowerOrderFragment : Fragment() {
    private val sharedViewModel: FlowerViewModel by activityViewModels()
    private var binding: FragmentFlowerOrderBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFlowerOrderBinding.inflate(inflater, container, false)
        return binding?.root
    }
    fun sendOrder(){
        val orderSubject = resources.getString(R.string.order_flowers)
        val orderText =
            resources.getString(R.string.tulips) + ":" + sharedViewModel.tulipCount.value + "\n" +
                    resources.getString(R.string.roses) + ":" + sharedViewModel.roseCount.value + "\n" +
                    resources.getString(R.string.daisies) + ":" + sharedViewModel.daisyCount.value + "\n" +
                    resources.getString(R.string.lavenders) + ":" + sharedViewModel.lavenderCount.value + "\n" +
                    resources.getString(R.string.total, sharedViewModel.totalPrice.value)

        val intent = Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_SUBJECT, orderSubject)
            .putExtra(Intent.EXTRA_TEXT, orderText)
        if (activity?.packageManager?.resolveActivity(intent,0) != null) {
            startActivity(intent)
        }
    }

    fun cancelOrder(){
        sharedViewModel.resetOrder()
        val action = FlowerOrderFragmentDirections.actionFlowerOrderFragmentToStartFragment()
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            flowerOrderFragment = this@FlowerOrderFragment
        }
    }
}