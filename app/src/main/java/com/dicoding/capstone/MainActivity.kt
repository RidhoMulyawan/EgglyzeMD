package com.dicoding.capstone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.capstone.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cek user login
        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            // Lakukan sesuatu jika user terautentikasi
            Log.d("MainActivity", "User logged in: ${currentUser.email}")
        } else {
            // Redirect ke LoginActivity jika tidak ada user terautentikasi
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Set listener untuk tombol logout
        binding.logoutButton.setOnClickListener {
            // Logout dari Firebase dan arahkan ke LoginActivity
            Firebase.auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = Firebase.auth.currentUser
        Log.d("MainActivity", "Current User: ${currentUser?.email ?: "No user"}")
    }
}
