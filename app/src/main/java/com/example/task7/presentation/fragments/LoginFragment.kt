package com.example.task7.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.task7.R
import com.example.task7.databinding.FragmentLoginBinding
import com.example.task7.viewModels.LoginFragmentViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val vm: LoginFragmentViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private var userName = ""
    private var password = ""
    private val USERNAME_TAG = "userName"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)

        auth = Firebase.auth

        binding.signInBtn.setOnClickListener {

            userName = binding.userNameText.text.toString()
            password = binding.passwordText.text.toString()
            auth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val bundle = bundleOf(binding.userNameText.text.toString() to USERNAME_TAG)
                        findNavController().navigate(
                            R.id.action_loginFragment_to_mapFragment,
                            bundle
                        )
                    } else {
                        Toast.makeText(
                            context, getString(R.string.authentication_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
        binding.signUpTxt.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
        binding.userNameText.addTextChangedListener {
            userName = it.toString()
            vm.signInIsEnable.value = userName.isNotEmpty() && password.isNotEmpty()
        }
        binding.passwordText.addTextChangedListener {
            password = it.toString()
            vm.signInIsEnable.value = userName.isNotEmpty() && password.isNotEmpty()
        }
        vm.signInIsEnable.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.signInBtn.isEnabled = true
                binding.signInBtn.background = ResourcesCompat.getDrawable(
                    requireActivity().resources,
                    R.drawable.btn_sign_enable,
                    null
                )
            } else {
                binding.signInBtn.isEnabled = false
                binding.signInBtn.background = ResourcesCompat.getDrawable(
                    requireActivity().resources,
                    R.drawable.btn_sign_disable,
                    null
                )
            }
        })
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
