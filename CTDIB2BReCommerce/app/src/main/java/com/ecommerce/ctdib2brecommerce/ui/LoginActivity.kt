package com.ecommerce.ctdib2brecommerce.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog

import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ecommerce.ctdib2brecommerce.R
import com.ecommerce.ctdib2brecommerce.databinding.LoginBinding
import com.ecommerce.ctdib2brecommerce.delegates.SetContentView
import android.view.LayoutInflater
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.inputmethod.InputMethodManager
import android.app.Activity




class LoginActivity : BaseActivtiy() {

    private val binding: LoginBinding by SetContentView<AppCompatActivity,LoginBinding>(R.layout.login)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

     binding.newUser.setOnClickListener( { v -> navigatetoRegister(v) } )

     binding.forgotpassword.setOnClickListener( { forgotPassword() })
    }

    private fun navigatetoRegister(v: View?) {
        val intent = Intent(this@LoginActivity,RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun forgotPassword() {

            val builder = AlertDialog.Builder(this@LoginActivity)
            builder.setTitle(R.string.forgot_password)

            val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(this@LoginActivity),
                R.layout.forgot_password, null, false)

             builder.setView(binding.root)


            val positiveText = getString(android.R.string.ok)
            builder.setPositiveButton(positiveText,
                    object: DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {

                            closeKeyboard(binding.root)
                        }


                    })

            val negativeText = getString(android.R.string.cancel)
            builder.setNegativeButton(negativeText,
                    object: DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            closeKeyboard(binding.root)
                        }

                    })

            val dialog = builder.create()

            dialog.show()

    }

    private fun closeKeyboard(root: View) {
                        val inputMethodManager = this@LoginActivity.getSystemService(
                                Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(
                                root.windowToken, 0)

    }
}
