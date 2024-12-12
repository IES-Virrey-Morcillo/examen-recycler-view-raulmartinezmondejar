package com.example.reciclerviewmontes

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reciclerviewmontes.databinding.ActivityMonteAddBinding

class MonteAdd : AppCompatActivity() {
    private lateinit var binding: ActivityMonteAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonteAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnAddMonte.setOnClickListener {
            val monteName = binding.etNombre.text.toString()
            val monteUrl = binding.etURL.text.toString()

            if (monteName.isNotEmpty() && monteUrl.isNotEmpty()) {
                val bundle = Bundle().apply {
                    putString("monte", monteName)
                    putString("urlmonte", monteUrl)
                }
                val intent = Intent(this, ReciclerViewMontes::class.java).apply {
                    putExtras(bundle)
                }
                startActivity(intent)
            } else {
            }
        }
    }
}