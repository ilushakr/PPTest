package com.example.pptest.startFragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.pptest.R
import com.example.pptest.mainFragment.MainFragmentViewModel
import com.example.pptest.mainFragment.MainFragmentViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.net.NetworkInfo

import android.net.ConnectivityManager


@AndroidEntryPoint
class StartFragment: Fragment(R.layout.start_fragment_layout){

    @Inject
    lateinit var factory : MainFragmentViewModelFactory
    private lateinit var viewModel: MainFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(MainFragmentViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argument = arguments?.getString("ALERT")
        if(argument == null || argument == "RETRY") start()
        if(argument == "SAVED_DATA") {
            findNavController().navigate(R.id.toMainFragment, Bundle().also { bundle ->
                bundle.putString("cardNumber", "")
            })
        }
        if(argument == "CLOSE") activity?.finishAffinity()
    }


    private fun start(){
        if(isNetworkAvailable()){

            viewModel.clearData()

            viewModel.savingState.asLiveData().observe(viewLifecycleOwner, {cardNumber ->
                if(cardNumber != null) {
                    val bundle = Bundle().also { bundle ->
                        bundle.putString("cardNumber", cardNumber)
                    }
                    findNavController().navigate(R.id.toMainFragment, bundle)
                }
            })
        }
        else{
            findNavController().navigate(R.id.toAlertFragment)
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}