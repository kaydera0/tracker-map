package com.example.task7.presentation.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.task7.databinding.TimeDialogBinding

class TimeDialogFragment(private val dialogListener: (Int) -> Unit) : DialogFragment() {

    lateinit var binding: TimeDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = TimeDialogBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.oneDay.setOnClickListener {
            dialogListener.invoke(1)
            dismiss()
        }
        binding.threeDays.setOnClickListener {
            dialogListener.invoke(3)
            dismiss()
        }
        binding.week.setOnClickListener {
            dialogListener.invoke(7)
            dismiss()
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

//    override fun onStart() {
//        super.onStart()
//        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
//        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//    }

}