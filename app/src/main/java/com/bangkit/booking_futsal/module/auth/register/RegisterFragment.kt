package com.bangkit.booking_futsal.module.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.bangkit.booking_futsal.R
import com.bangkit.booking_futsal.databinding.FragmentRegisterBinding
import com.bangkit.booking_futsal.module.auth.AuthViewmodels
import com.bangkit.booking_futsal.module.auth.login.LoginFragment
import com.bangkit.booking_futsal.utils.AuthCallbackString
import com.bangkit.booking_futsal.utils.SettingPreferences


class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: SettingPreferences
    private val viewModel: AuthViewmodels by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (name.isEmpty() or email.isEmpty() or password.isEmpty()) {
                Toast.makeText(context, "Form Tidak Boleh Ada Yang Kosong", Toast.LENGTH_SHORT)
                    .show()
            } else {

                viewModel.register(name, email, password, object : AuthCallbackString {
                    override fun onResponse(success: String, message: String) {
                        if (success == "true") {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            activity?.supportFragmentManager?.commit {
                                replace(R.id.fragment_container, LoginFragment())
                            }
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }


                    }

                })

            }

        }

    }
}