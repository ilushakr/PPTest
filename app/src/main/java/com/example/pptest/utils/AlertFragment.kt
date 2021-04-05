package com.example.pptest.utils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pptest.R
import kotlinx.android.synthetic.main.alert_fragment.view.*

class AlertFragment: Fragment(R.layout.alert_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.retryButton.setOnClickListener {
            findNavController().navigate(R.id.fromAlertFragment, Bundle().also {
                it.putString("ALERT", "RETRY")
            })
        }

        view.savedData.setOnClickListener {
            findNavController().navigate(R.id.fromAlertFragment, Bundle().also {
                it.putString("ALERT", "SAVED_DATA")
            })
        }

        view.closeAppButton.setOnClickListener {
            findNavController().navigate(R.id.fromAlertFragment, Bundle().also {
                it.putString("ALERT", "CLOSE")
            })
        }
    }

}