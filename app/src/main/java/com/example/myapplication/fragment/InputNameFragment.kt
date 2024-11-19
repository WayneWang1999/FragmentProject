package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentInputNameBinding
import com.example.myapplication.viewmodels.InputNameViewModel

class InputNameFragment : Fragment() {

    private lateinit var binding: FragmentInputNameBinding
    private val viewModel: InputNameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input_name, container, false)
        observeFirstRunState()
        setupFirstRunUI()
        return binding.root
    }

    private fun observeFirstRunState() {
        viewModel.isFirstRun.observe(viewLifecycleOwner) { isFirstRun ->
            if (isFirstRun == false) {
                navigateToWelcomeBack()
            }
        }
    }

    private fun setupFirstRunUI() {
        binding.btnSignup.setOnClickListener {
            val userName = binding.etName.text.toString().trim()
            if (userName.isNotBlank()) {
                // Save the user name and update first run state
                viewModel.saveUserName(userName)
                viewModel.saveIsFirstRun(false)
            } else {
                Toast.makeText(requireContext(), "Please input the username", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToWelcomeBack() {
        val userName = viewModel.userName.value.orEmpty()
        val action = InputNameFragmentDirections.actionInputNameFragmentToWelcomeBack(userName)
        findNavController().navigate(action)
    }
}
