package com.agah.assigment_6.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.agah.assigment_6.R
import com.agah.assigment_6.user.Client
import com.agah.assigment_6.databinding.FragmentLoginBinding

import com.agah.assigment_6.model.UserAuth
import com.agah.assigment_6.networking.NetworkHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private var username: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        initListeners()
        isUserLoggedIn()
        binding.buttonLogin.setOnClickListener {
            login()
        }
        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        return binding.root
    }

    private fun login() {

        username = binding.editTextName.text.toString()
        password = binding.editTextPassword.text.toString()

        NetworkHelper().userService?.login(username, password)
            ?.enqueue(object : Callback<UserAuth> {
                override fun onResponse(call: Call<UserAuth>, response: Response<UserAuth>) {

                    val status = response.body()?.status.toString()
                    val errorMsg = response.body()?.message.toString()

                    if (status == "true") {
                        with(Client(requireContext())) {
                            this.setUserEmail(response.body()?.user!![0].email)
                            this.setUserGender(response.body()?.user!![0].gender)
                            this.setUserUsername(response.body()?.user!![0].username)
                            this.setRememberMe(true)
                        }
                        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                    } else
                        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<UserAuth>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }


            })





    }
    private fun initListeners() {
        binding.editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                checkFields()
            }
        })

        binding.editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                checkFields()
            }
        })
    }

    private fun checkFields() {
        if (!binding.editTextName.text.isNullOrEmpty() && !binding.editTextPassword.text.isNullOrEmpty()) {
            binding.buttonLogin.isEnabled = true
            binding.buttonLogin.alpha = 1F
        } else {
            binding.buttonLogin.isEnabled = false
            binding.buttonLogin.alpha = 0.2F
        }
    }
    private fun isUserLoggedIn() {
        if (Client(requireContext()).isRememberMe()) {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }


}




