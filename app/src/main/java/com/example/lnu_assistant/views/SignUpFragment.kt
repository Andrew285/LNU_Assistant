package com.example.lnu_assistant.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lnu_assistant.R
import com.example.lnu_assistant.databinding.FragmentSignInBinding
import com.example.lnu_assistant.databinding.FragmentSignUpBinding
import com.example.lnu_assistant.views.interfaces.IFragmentNavigator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment(), IFragmentNavigator {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = Firebase.auth

        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener {
            val userName: String = binding.editTextUserName.text.trim().toString()
            val groupName: String = binding.editTextGroupName.text.trim().toString()
            val userEmail: String = binding.editTextEmail.text.trim().toString()
            val userPassword: String = binding.editTextPassword.text.trim().toString()
            createUser(userName, groupName, userEmail, userPassword)
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment, null)
        }
    }

    private fun createUser(name: String, group: String, email: String, password: String) {
        activity?.let {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) {task ->
                    if (task.isSuccessful) {
                        Log.d("TAGY", "createUserWithEmail:success")
                        val user = firebaseAuth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAGY", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            context,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        updateUI(null)
                    }
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    private fun reload() {

    }

    override fun title(): String {
        return getString(R.string.toolbar_sign_up_fragment)
    }
}