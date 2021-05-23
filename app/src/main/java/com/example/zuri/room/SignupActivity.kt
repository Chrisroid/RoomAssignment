package com.example.zuri.room

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zuri.room.databinding.ActivitySignupBinding
import com.example.zuri.room.db.UserEntity
import com.example.zuri.room.db.UserRepository

class SignupActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setup()
    }

    private fun setup() {
        val binding = ActivitySignupBinding.inflate(layoutInflater)
        val repository = UserRepository(this)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            val username = binding.tilUsername.editText?.text
            val email = binding.tilEmail.editText?.text
            val password = binding.tilPassword.editText?.text
            if(username?.isBlank() == false && email?.isBlank() == false && password?.isBlank() == false){
                repository.insertUser(UserEntity(username.toString(), email.toString(), password.toString()))
                startActivity(Intent(this, LoginActivity::class.java))
            }else Toast.makeText(this, "One or more fields are blank", Toast.LENGTH_SHORT).show()
        }
        val login_txt = findViewById(R.id.login_txt) as TextView

        login_txt.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}