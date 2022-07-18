package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityIntentHandlerBinding

class IntentHandlerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.send.setOnClickListener {
            val intent = Intent()
                .putExtra(Intent.EXTRA_TEXT, "Test message")
                .setAction(Intent.ACTION_SEND)
                .setType("text/plain")

            val createChooser = Intent.createChooser(intent, "Choose app")
            startActivity(createChooser)
        }

        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        } ?: run {
            Snackbar.make(
                binding.root,
                getString(R.string.empty_content_error),
                Snackbar.LENGTH_SHORT
            )
                .setAction(android.R.string.ok) {
                    finish()
                }
                .show()
        }
    }
}