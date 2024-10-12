package com.example.bookdonationapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Input
import android.text.InputType
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.bookdonationapp.databinding.ActivityLoginBinding
import com.example.bookdonationapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class DonationAgentLogin : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var togglePwdVisibility : ImageView
    private var isPwdVisible = false
    private lateinit var pwd : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pwd = findViewById(R.id.editPwd)
        togglePwdVisibility = findViewById(R.id.visibleIcon)

        togglePwdVisibility.setOnClickListener{
            togglePwdVisibility()
        }

        firebaseAuth = FirebaseAuth.getInstance()
        binding.signupLink.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val pass = binding.editPwd.text.toString()

            if (validateEmail(email)) {
                if (pass.isNotEmpty()) {
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this,WelcomeToCollectionCenter::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Credentials are not matched!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Password is required!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    // Function to validate email format
    private fun validateEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            Toast.makeText(this, "Email is required!", Toast.LENGTH_SHORT)
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

    /*override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null){
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }*/
}