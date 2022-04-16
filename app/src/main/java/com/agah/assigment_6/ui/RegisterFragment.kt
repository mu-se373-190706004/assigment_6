package com.agah.assigment_6.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.agah.assigment_6.R
import com.agah.assigment_6.user.Client
import com.agah.assigment_6.databinding.FragmentRegisterBinding
import com.agah.assigment_6.model.UserAuth
import com.agah.assigment_6.networking.NetworkHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterFragment : Fragment() {
    private lateinit var binding : FragmentRegisterBinding
    private var username = ""
    private var email = ""
    private var password = ""
    private var gender = "male"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)

        binding.btnSignUp.setOnClickListener {
            validate()
        }
        return binding.root
    }

    private fun register(username: String, email: String, password: String, gender: String){
        NetworkHelper().userService?.register(username,email,password,gender)
            ?.enqueue(object : Callback<UserAuth> {
                override fun onResponse(
                    call: Call<UserAuth>,
                    response: Response<UserAuth>
                ) {
                    val status = response.body()?.status.toString()
                    val errorMsg = response.body()?.message.toString()

                    if (status == "true") {
                        with(Client(requireContext())) {
                            this.setUserEmail(response.body()?.user!![0].email)
                            this.setUserGender(response.body()?.user!![0].gender)
                            this.setUserUsername(response.body()?.user!![0].username)
                            this.setRememberMe(true)
                        }
                        findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                    } else
                        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT)
                            .show()
                }

                override fun onFailure(call: Call<UserAuth>, t: Throwable) {
                    Toast.makeText(requireContext(),t.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })

    }
    private fun validate(){
        username = binding.etUsername.text.toString()
        email = binding.etEmail.text.toString()
        password = binding.etPassword.text.toString()

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, id ->
            val radioButton: RadioButton = radioGroup.findViewById(id)
            gender = radioButton.text.toString()
        }
        if(TextUtils.isEmpty(username)){
            binding.etUsername.error = "Enter name"
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            binding.etPassword.error = "Enter password"
        } else {
            register(username,email,password,gender)
        }


    }

}



