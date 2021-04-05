package com.example.pptest

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController

class AlertFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(requireContext().resources.getString(R.string.no_internet))
                .setMessage(requireContext().resources.getString(R.string.retry_message))
                .setPositiveButton(requireContext().resources.getString(R.string.retry)) {
                        dialog, id ->  findNavController().navigate(R.id.closeAlert)
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}