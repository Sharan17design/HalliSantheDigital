package com.halli.santhe.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.halli.santhe.R
import com.halli.santhe.data.AppDatabase
import com.halli.santhe.databinding.ActivityProductDetailBinding
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var b: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(b.root)
        setSupportActionBar(b.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        b.toolbar.setNavigationOnClickListener { finish() }

        val id = intent.getLongExtra(EXTRA_ID, -1L)
        if (id < 0) { finish(); return }

        val dao = AppDatabase.getInstance(this).productDao()
        lifecycleScope.launch {
            val p = dao.getById(id) ?: run { finish(); return@launch }

            b.tvName.text = p.name
            b.tvPrice.text = getString(R.string.price_format, p.price)
            b.tvCategory.text = p.category
            b.tvDescription.text =
                if (p.description.isBlank()) getString(R.string.no_description) else p.description
            b.tvPhone.text = p.sellerPhone

            if (!p.imageUri.isNullOrEmpty()) {
                Glide.with(b.ivImage)
                    .load(Uri.parse(p.imageUri))
                    .placeholder(R.drawable.ic_placeholder)
                    .into(b.ivImage)
            } else {
                b.ivImage.setImageResource(R.drawable.ic_placeholder)
            }

            // ✅ UPDATED CONTACT SELLER BUTTON
            b.btnCall.setOnClickListener {

                val options = arrayOf("Call", "Chat")

                AlertDialog.Builder(this@ProductDetailActivity)
                    .setTitle("Contact Seller")
                    .setItems(options) { _, which ->
                        when (which) {

                            // 📞 CALL OPTION
                            0 -> {
                                startActivity(
                                    Intent(
                                        Intent.ACTION_DIAL,
                                        Uri.parse("tel:${p.sellerPhone}")
                                    )
                                )
                            }

                            // 💬 CHAT OPTION
                            1 -> {
                                val intent = Intent(this@ProductDetailActivity, ChatActivity::class.java)
                                intent.putExtra("sellerName", p.name)
                                startActivity(intent)
                            }
                        }
                    }
                    .show()
            }
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}