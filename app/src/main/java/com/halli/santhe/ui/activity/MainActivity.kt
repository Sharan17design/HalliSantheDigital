package com.halli.santhe.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.halli.santhe.R
import com.halli.santhe.databinding.ActivityMainBinding
import com.halli.santhe.ui.adapter.ProductAdapter
import com.halli.santhe.ui.viewmodel.ProductViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding
    private val vm: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        // ✅ IMPORTANT: switch from splash theme to normal theme
        setTheme(R.style.Theme_HalliSanthe)

        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        setSupportActionBar(b.toolbar)

        val adapter = ProductAdapter { p ->
            startActivity(
                Intent(this, ProductDetailActivity::class.java)
                    .putExtra(ProductDetailActivity.EXTRA_ID, p.id)
            )
        }

        b.rvProducts.layoutManager = GridLayoutManager(this, 2)
        b.rvProducts.adapter = adapter

        vm.products.observe(this) { list ->
            adapter.submitList(list)
            b.emptyView.visibility =
                if (list.isEmpty()) android.view.View.VISIBLE
                else android.view.View.GONE
        }

        b.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }
    }
}