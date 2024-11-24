package com.fetch_assesment

import android.animation.Animator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fetch_assesment.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.util.Log
import android.widget.ImageView
import android.widget.Toast



class MainActivity :  AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppViewModel by viewModels()
    private val adapter = ItemViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbar
        toolbar.setBackgroundColor(Color.rgb(251,168,25))
        val logo = findViewById<ImageView>(R.id.logoImage)

        // Check for network connectivity before attempting to fetch data
        if (isNetworkAvailable()) {
            setupRecyclerView()
            loadData()
        } else {
            // If there's no network connection, navigate to NoConnectionActivity
            startActivity(Intent(this, NoConnectionActivity::class.java))
            finish() // Close MainActivity to prevent the user from staying on the current screen
        }

    }
    private fun playPopOutAnimation(logo: ImageView, onComplete: () -> Unit) {
        // Scale up for the pop out effect
        val scaleUpX = ObjectAnimator.ofFloat(logo, "scaleX", 1f, 1.5f)
        val scaleUpY = ObjectAnimator.ofFloat(logo, "scaleY", 1f, 1.5f)

        // Scale down animation for the pop in effect
        val scaleDownX = ObjectAnimator.ofFloat(logo, "scaleX", 1.5f, 1f)
        val scaleDownY = ObjectAnimator.ofFloat(logo, "scaleY", 1.5f, 1f)

        // Set durations in ms for each animation
        scaleUpX.duration = 300
        scaleUpY.duration = 300
        scaleDownX.duration = 300
        scaleDownY.duration = 300

        // Create the sequence
        val animatorSet = AnimatorSet()
        animatorSet.play(scaleUpX).with(scaleUpY)
        animatorSet.play(scaleDownX).with(scaleDownY).after(scaleUpX)

        animatorSet.start() // Start the animation

        // Call onComplete after animation finishes leave other methods default
        animatorSet.addListener(object : android.animation.Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                TODO("Not yet implemented")
            }

            override fun onAnimationEnd(animation: Animator) {
                onComplete()
            }

            override fun onAnimationCancel(animation: Animator) {
                TODO("Not yet implemented")
            }

            override fun onAnimationRepeat(animation: Animator) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun setupRecyclerView() {
        binding.recyclerView.adapter = adapter
    }
    private fun loadData() {
        // Show the loading screen with logo initially
        switchScreen(isLoading = true)

        lifecycleScope.launch {
            try {
                // Fetch data
                val items = viewModel.fetchItems()

                // After data is fetched, play the pop-out animation and switch screens
                android.os.Handler().postDelayed({
                    playPopOutAnimation(binding.logoImage) {
                        switchScreen(isLoading = false) // Switch to the main screen after animation
                        adapter.setData(items) // Update RecyclerView with fetched data
                    }
                }, 1000) // 1-second delay for a cooler animation simulation since the data loads pretty fast
            } catch (e: Exception) {
                    // if API request fails, navigate to NoConnectionActivity
                    val intent = Intent(this@MainActivity, NoConnectionActivity::class.java).apply {
                        putExtra("error_message", "Failed to load data.")
                    }
                    startActivity(intent)
                    finish()
            }
        }
    }




    private fun switchScreen(isLoading: Boolean) {
        if (isLoading) {
            // Show the loading screen
            binding.logoImage.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            binding.toolbar.visibility = View.GONE
            binding.toolbarTitle.visibility = View.GONE
        } else {
            // Show the main screen
            binding.logoImage.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            binding.toolbar.visibility = View.VISIBLE
            binding.toolbarTitle.visibility = View.VISIBLE
        }
    }
    // Function to check if the device is connected to the internet
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}