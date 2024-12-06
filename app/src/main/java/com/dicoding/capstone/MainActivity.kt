package com.dicoding.capstone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.capstone.databinding.ActivityMainBinding
import com.dicoding.capstone.ui.login.LoginActivity
import com.dicoding.capstone.ui.profile.ProfileFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        // Set listener untuk Bottom Navigation
        val navView: BottomNavigationView = binding.bottomNavigationView
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Arahkan ke Home (di sini, tetap berada di MainActivity)
                    Log.d("MainActivity", "Home clicked")
                    true
                }
                R.id.navigation_history -> {
                    // Dashboard logic
                    Log.d("MainActivity", "Dashboard clicked")
                    true
                }
                R.id.navigation_profile -> {
                    // Notifications logic
                    Log.d("MainActivity", "Notifications clicked")
                    val profileFragment = ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, profileFragment) // Pastikan ID fragment_container sesuai dengan ID yang Anda gunakan di XML
                        .commit()
                    true
                }
                else -> false
            }
        }

        // Default fragment (menampilkan MainActivity di sini)
        if (savedInstanceState == null) {
            navView.selectedItemId = R.id.navigation_home
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = Firebase.auth.currentUser
        Log.d("MainActivity", "Current User: ${currentUser?.email ?: "No user"}")
    }
}
