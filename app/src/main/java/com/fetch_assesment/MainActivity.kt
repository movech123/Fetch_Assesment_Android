package com.fetch_assesment

import android.animation.Animator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fetch_assesment.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import androidx.appcompat.widget.Toolbar
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.widget.ImageView

class MainActivity :  AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppViewModel by viewModels()
    private val adapter = ItemViewAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setBackgroundColor(Color.rgb(251,168,25))
        val logo = findViewById<ImageView>(R.id.logoImage)
        setupRecyclerView()
        loadData()


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
                Toast.makeText(this@MainActivity, "Error loading data: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun switchScreen(isLoading: Boolean) {
        if (isLoading) {
            // Show the loading screen
            binding.logoImage.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            binding.toolbar.visibility = View.GONE
        } else {
            // Show the main screen
            binding.logoImage.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            binding.toolbar.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}