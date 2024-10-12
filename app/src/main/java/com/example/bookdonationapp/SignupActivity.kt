package com.example.bookdonationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.bookdonationapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var togglePwdVisibility : ImageView
    private lateinit var toggleCnfmPwdVisibility : ImageView
    private var isPwdVisible = false
    private lateinit var pwd : EditText
    private lateinit var cnfmPwd : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pwd = findViewById(R.id.inputPwd)
        cnfmPwd = findViewById(R.id.cnfmPwd)
        togglePwdVisibility = findViewById(R.id.visibleIcon01)
        toggleCnfmPwdVisibility = findViewById(R.id.visibleIcon02)

        togglePwdVisibility.setOnClickListener{
            togglePwdVisibility()
        }

        toggleCnfmPwdVisibility.setOnClickListener{
            toggleCnfmPwdVisibility()
        }

        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.signupBtn.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val pass = binding.inputPwd.text.toString()
            val confirmPass = binding.cnfmPwd.text.toString()

            if(validateEmail(email)) {
                if (pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                    if (pass == confirmPass) {
                        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val intent = Intent(this, SuccessActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        this,
                                        it.exception.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "Password is not matching!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please create a password!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //Validate email format
    private fun validateEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email!", Toast.LENGTH_SHORT)
                .show()
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email!", Toast.LENGTH_SHORT)
                .show()
            false
        } else {
            true
        }
    }

    private fun togglePwdVisibility(){
        if(isPwdVisible){
            //Hide Pwd
            pwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            pwd.setSelection(pwd.text.length)
            togglePwdVisibility.setImageResource(R.drawable.baseline_visibility_off_24)//Set icon to hidden state
        }
        else{
            //Show Pwd
            pwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            pwd.setSelection(pwd.text.length)
            togglePwdVisibility.setImageResource(R.drawable.baseline_visibility_24)//Set icon to visible state
        }

        isPwdVisible = !isPwdVisible
    }

    private fun toggleCnfmPwdVisibility(){
        if(isPwdVisible){
            //Hide Pwd
            cnfmPwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            cnfmPwd.setSelection(cnfmPwd.text.length)
            toggleCnfmPwdVisibility.setImageResource(R.drawable.baseline_visibility_off_24)//Set icon to hidden state
        }
        else{
            //Show Pwd
            cnfmPwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            cnfmPwd.setSelection(cnfmPwd.text.length)
            toggleCnfmPwdVisibility.setImageResource(R.drawable.baseline_visibility_24)//Set icon to visible state
        }

        isPwdVisible = !isPwdVisible
    }
}