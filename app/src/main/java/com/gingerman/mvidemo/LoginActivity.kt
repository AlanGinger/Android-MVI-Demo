package com.gingerman.mvidemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gingerman.mvidemo.data.mvi.LoginIntent
import com.gingerman.mvidemo.data.mvi.LoginViewState
import com.gingerman.mvidemo.data.viewmodel.MVIViewModel
import com.gingerman.mvidemo.databinding.ActivityMainBinding
import com.google.android.material.internal.TextWatcherAdapter

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MVIViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MVIViewModel::class.java)
        setListeners()
        subscribeUI()
    }

    @SuppressLint("SetTextI18n")
    private fun subscribeUI() {
        viewModel.loginViewState.observe(this) { loginViewState ->
            when (loginViewState) {
                is LoginViewState.LoginSuccess -> {
                    loginViewState.userInfo.let {
                        binding.tvUserName.text = "用户昵称：${it.nickname}"
                        binding.tvUserEmail.text = "用户邮箱：${it.email}"
                        binding.tvUserName.visibility = VISIBLE
                        binding.tvUserEmail.visibility = VISIBLE
                        binding.progressIndicator.visibility = GONE
                        binding.inputLayoutUsername.visibility = GONE
                        binding.inputLayoutPassword.visibility = GONE
                    }
                }
                is LoginViewState.LoginFail -> {
                    Toast.makeText(this, loginViewState.error, Toast.LENGTH_SHORT).show()
                    binding.progressIndicator.visibility = GONE
                }
                is LoginViewState.Loading -> {
                    binding.progressIndicator.visibility = VISIBLE
                }
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setListeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.action(LoginIntent.Login())
        }

        binding.inputLayoutUsername.setEndIconOnClickListener {
            viewModel.action(LoginIntent.ClearAccount())
        }

        binding.inputLayoutPassword.setEndIconOnClickListener {
            viewModel.action(LoginIntent.ClearPassword())
        }

        binding.etUsername.addTextChangedListener(object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.action(LoginIntent.UpdateAccount(s.toString()))
            }
        })

        binding.etPassword.addTextChangedListener(object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.action(LoginIntent.UpdatePassword(s.toString()))
            }
        })
    }

}