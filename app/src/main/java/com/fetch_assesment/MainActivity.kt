package com.fetch_assesment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fetch_assesment.databinding.ActivityMainBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppViewModel by viewModels()
    private val adapter = ItemViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadData()


    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = adapter
    }

    private fun loadData() {
        lifecycleScope.launch {
            try {
                val items = viewModel.fetchItems()
                adapter.setData(items)
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error loading data: ${e.message}", Toast.LENGTH_LONG).show() // debug message
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}