package com.halli.santhe.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.halli.santhe.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var b: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityChatBinding.inflate(layoutInflater)
        setContentView(b.root)

        val sellerName = intent.getStringExtra("sellerName") ?: "Seller"
        b.tvChat.text = "Chat with $sellerName\n"

        b.btnSend.setOnClickListener {
            val msg = b.etMessage.text.toString()
            if (msg.isNotEmpty()) {
                b.tvChat.append("\nYou: $msg")
                b.etMessage.text.clear()
            }
        }
    }
}