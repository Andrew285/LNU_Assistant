package com.example.lnu_assistant.views

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lnu_assistant.R
import com.example.lnu_assistant.databinding.FragmentSignInBinding
import com.example.lnu_assistant.viewmodel.AppViewModel
import com.example.lnu_assistant.views.interfaces.DrawerLocker
import com.example.lnu_assistant.views.interfaces.IFragmentNavigator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignInFragment : Fragment(), IFragmentNavigator {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = Firebase.auth
        viewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]
        (requireActivity() as MainActivity).setDrawerEnabled(false)

        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            noAccountText.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment, null)
            }

            loginButton.setOnClickListener{
                val email: String = binding.emailEditText.text.trim().toString()
                val password: String = binding.passwordEditText.text.trim().toString()
                val result = viewModel.singInWithEmailAndPassword(requireActivity(), email, password)
                if (result) findNavController().navigate(R.id.action_signInFragment_to_home_dest)
            }
        }
    }

    override fun title(): String {
        return getString(R.string.toolbar_sign_in_fragment)
    }


}