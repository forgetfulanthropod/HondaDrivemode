package com.example.hondadrivemode

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hondadrivemode.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isLocked = true
    private var isWindowsOpen = false
    private var isFrontTrunkOpen = false
    private var isRearTrunkOpen = false
    private var messageJob: Job? = null

    private fun showMessage(message: String) {
        messageJob?.cancel()
        messageJob = lifecycleScope.launch {
            binding.messageOverlay.apply {
                text = message
                alpha = 0f
                visibility = View.VISIBLE
                animate()
                    .alpha(1f)
                    .setDuration(200)
                    .start()
            }
            delay(1500)
            binding.messageOverlay.animate()
                .alpha(0f)
                .setDuration(200)
                .withEndAction {
                    binding.messageOverlay.visibility = View.INVISIBLE
                }
                .start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            isLocked = !isLocked
            binding.fab.setImageResource(
                if (isLocked) R.drawable.ic_lock_dark
                else R.drawable.ic_unlock_dark
            )
            showMessage(if (isLocked) "Vehicle Locked" else "Vehicle Unlocked")
        }

        binding.btnWindows.setOnClickListener {
            isWindowsOpen = !isWindowsOpen
            binding.btnWindows.background = getDrawable(
                if (isWindowsOpen) R.drawable.btn_circle_background_open
                else R.drawable.btn_circle_background
            )
            showMessage(if (isWindowsOpen) "Windows Opened" else "Windows Closed")
        }

        binding.btnFrontTrunk.setOnClickListener {
            isFrontTrunkOpen = !isFrontTrunkOpen
            binding.btnFrontTrunk.background = getDrawable(
                if (isFrontTrunkOpen) R.drawable.btn_circle_background_open
                else R.drawable.btn_circle_background
            )
            showMessage(if (isFrontTrunkOpen) "Front Trunk Opened" else "Front Trunk Closed")
        }

        binding.btnRearTrunk.setOnClickListener {
            isRearTrunkOpen = !isRearTrunkOpen
            binding.btnRearTrunk.background = getDrawable(
                if (isRearTrunkOpen) R.drawable.btn_circle_background_open
                else R.drawable.btn_circle_background
            )
            showMessage(if (isRearTrunkOpen) "Rear Trunk Opened" else "Rear Trunk Closed")
        }

        binding.btnLocation.setOnClickListener {
            showMessage("Finding Vehicle Location...")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        messageJob?.cancel()
    }
}