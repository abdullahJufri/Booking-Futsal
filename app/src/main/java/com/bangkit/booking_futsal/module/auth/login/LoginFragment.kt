package com.bangkit.booking_futsal.module.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.bangkit.booking_futsal.R
import com.bangkit.booking_futsal.databinding.FragmentLoginBinding
import com.bangkit.booking_futsal.module.auth.AuthViewmodels
import com.bangkit.booking_futsal.module.auth.register.RegisterFragment
import com.bangkit.booking_futsal.utils.AuthCallbackString
import com.bangkit.booking_futsal.utils.SettingPreferences


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: SettingPreferences
    private val viewModel: AuthViewmodels by viewModels()
    private lateinit var message: String

//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = activity?.let { SettingPreferences(it) }!!

//        if (pref.preference.getString("email", "") != "") {
//            if (pref.preference.getString("token", "") != "") {
//                val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                startActivity(intent)
//            }
//            finish()
//        }

        binding.tvRegister.setOnClickListener {
            activity?.supportFragmentManager?.commit {
                replace(R.id.fragment_container, RegisterFragment())
                addToBackStack(null)
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            viewModel.login(email, password, object : AuthCallbackString {
                override fun onResponse(success: String, message: String) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT)
                        .show()
                }
            })
            viewModel.user.observe(viewLifecycleOwner) { user ->
                user.data?.id?.let { it1 ->
                    user.data.roles?.let { it2 ->
                        pref.setUserLogin(
                            it1.toInt(), email,
                            it2
                        )
                    }
                }
//                Toast.makeText(context, "email : $email id: ${user.data?.id} roles: ${user.data?.roles}", Toast.LENGTH_SHORT)
//                    .show()
                println("email : $email id: ${user.data?.id} roles: ${user.data?.roles}")
//                val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                startActivity(intent)
            }
        }
    }


}