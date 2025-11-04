package ky.paba.recyclerview

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class detWayang : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_det_wayang)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val _detFotoWayang = findViewById<ImageView>(R.id.detFotoWayang)
        val _detNamaWayang = findViewById<TextView>(R.id.detNamaWayang)
        val _detDetailWayang = findViewById<TextView>(R.id.detDetailWayang)

        val dataIntent = intent.getParcelableExtra<dcWayang>(
            "kirimData", dcWayang::class.java
        )

        if (dataIntent != null) {
            Picasso.get()
                .load(dataIntent.foto)
                .into(_detFotoWayang)

            _detNamaWayang.setText(dataIntent.nama)
            _detDetailWayang.setText(dataIntent.deskripsi)
        }

    }
}