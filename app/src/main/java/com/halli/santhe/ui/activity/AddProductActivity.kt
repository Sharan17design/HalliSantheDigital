package com.halli.santhe.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.halli.santhe.R
import com.halli.santhe.data.Product
import com.halli.santhe.databinding.ActivityAddProductBinding
import com.halli.santhe.ui.viewmodel.ProductViewModel

class AddProductActivity : AppCompatActivity() {
    private lateinit var b: ActivityAddProductBinding
    private val vm: ProductViewModel by viewModels()
    private var pickedUri: Uri? = null

    private val pickImage = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            try {
                contentResolver.takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: Exception) {}
            pickedUri = uri
            b.ivPreview.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(b.root)
        setSupportActionBar(b.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        b.toolbar.setNavigationOnClickListener { finish() }

        val cats = resources.getStringArray(R.array.categories)
        b.spCategory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cats)

        b.btnPickImage.setOnClickListener { pickImage.launch(arrayOf("image/*")) }

        b.btnSave.setOnClickListener { save() }
    }

    private fun save() {
        val name = b.etName.text.toString().trim()
        val priceStr = b.etPrice.text.toString().trim()
        val desc = b.etDescription.text.toString().trim()
        val phone = b.etPhone.text.toString().trim()
        val category = b.spCategory.selectedItem?.toString() ?: ""

        if (name.isEmpty() || priceStr.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, R.string.fill_required, Toast.LENGTH_SHORT).show(); return
        }
        val price = priceStr.toDoubleOrNull()
        if (price == null || price < 0) {
            Toast.makeText(this, R.string.invalid_price, Toast.LENGTH_SHORT).show(); return
        }

        vm.addProduct(
            Product(
                name = name, price = price, category = category,
                description = desc, sellerPhone = phone,
                imageUri = pickedUri?.toString()
            )
        )
        Toast.makeText(this, R.string.product_saved, Toast.LENGTH_SHORT).show()
        finish()
    }
}
