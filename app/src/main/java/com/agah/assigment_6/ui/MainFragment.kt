package com.agah.assigment_6.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.agah.assigment_6.R
import com.agah.assigment_6.user.Client
import com.agah.assigment_6.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private lateinit var binding : FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater,container,false)

        fetchUserData()

        binding.btnLogout.setOnClickListener {
            logout()
        }
        return binding.root
    }

    private fun fetchUserData() {
        with(Client(requireContext())){
            binding.tvUsername.text = this.getUserUsername().toString()
            binding.tvUserEmail.text = this.getUserEmail().toString()
            binding.tvGender.text = this.getUserGender().toString()
        }
    }

    private fun logout() {
        Client(requireContext()).clearSharedPref()
        findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
    }
}