package ky.paba.recyclerview

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    // Recycle View
//    private lateinit var _nama: MutableList<String>
//    private lateinit var _karakter: MutableList<String>
//    private lateinit var _deskripsi: MutableList<String>
//    private lateinit var _gambar: MutableList<String>

    // Shared Preferences
    private var _nama: MutableList<String> = emptyList<String>().toMutableList()
    private var _karakter: MutableList<String> = emptyList<String>().toMutableList()
    private var _deskripsi: MutableList<String> = emptyList<String>().toMutableList()
    private var _gambar: MutableList<String> = emptyList<String>().toMutableList()
    lateinit var sp: SharedPreferences

    private var arWayang = arrayListOf<dcWayang>()

    private lateinit var _rvWayang: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Shared Preferences
        sp = getSharedPreferences("dataSP", MODE_PRIVATE)
        val gson = Gson()
        val isiSP = sp.getString("spWayang", null)
        val type = object : TypeToken<ArrayList<dcWayang>>() {}.type
        if (isiSP != null) {
            arWayang = gson.fromJson(isiSP, type)
        }

        // Shared Preferences
        if (arWayang.size == 0) {
            SiapkanData()
        } else {
            arWayang.forEach {
                _nama.add(it.nama)
                _karakter.add(it.karakter)
                _deskripsi.add(it.deskripsi)
                _gambar.add(it.foto)
            }
            arWayang.clear()
        }

        _rvWayang = findViewById<RecyclerView>(R.id.rvWayang)
        SiapkanData()
        TambahData()
        TampilkanData()
    }

    fun SiapkanData() {
//        _nama = resources.getStringArray(R.array.namaWayang).toMutableList()
//        _karakter = resources.getStringArray(R.array.karakterUtamaWayang).toMutableList()
//        _deskripsi = resources.getStringArray(R.array.deskripsiWayang).toMutableList()
//        _gambar = resources.getStringArray(R.array.gambarWayang).toMutableList()

        _nama = resources.getStringArray(R.array.namaWayang).toMutableList()
        _karakter = resources.getStringArray(R.array.karakterUtamaWayang).toMutableList()
        _deskripsi = resources.getStringArray(R.array.deskripsiWayang).toMutableList()
        _gambar = resources.getStringArray(R.array.gambarWayang).toMutableList()

    }

    fun TambahData() {
//            arWayang.clear()
//
//            for (position in _nama.indices) {
//                val data = dcWayang(
//                    _gambar[position],
//                    _nama[position],
//                    _karakter[position],
//                    _deskripsi[position]
//                )
//                arWayang.add(data)
//            }

        // Shared Preferences
        val gson = Gson()
        sp.edit {
            arWayang.clear()

            for (position in _nama.indices) {
                val data = dcWayang(
                    _gambar[position],
                    _nama[position],
                    _karakter[position],
                    _deskripsi[position]
                )
                arWayang.add(data)
            }
            val json = gson.toJson(arWayang)
            putString("spWayang", json)
            apply()
        }
    }

    fun TampilkanData() {
        // 1 colum
        _rvWayang.layoutManager = LinearLayoutManager(this)

        // 2 colum
//         _rvWayang.layoutManager = GridLayoutManager(this,2)

        //
//        _rvWayang.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        val adapterWayang = adapterRecView(arWayang)
        _rvWayang.adapter = adapterWayang

        adapterWayang.setOnItemClickCallback(object : adapterRecView.OnItemClickCallback {
            override fun onItemClicked(data: dcWayang) {

                val intent = Intent(this@MainActivity, detWayang::class.java)
                intent.putExtra("kirimData", data)
                startActivity(intent)

//                Toast.makeText(this@MainActivity, data.nama, Toast.LENGTH_SHORT)
//                    .show()
            }

            override fun delData(pos: Int) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("HAPUS DATA")
                    .setMessage("Apakah Benar Data " + _nama[pos] + " akan dihapus ?")
                    .setPositiveButton(
                        "HAPUS",
                        DialogInterface.OnClickListener { dialog, which ->
                            _gambar.removeAt(pos)
                            _nama.removeAt(pos)
                            _deskripsi.removeAt(pos)
                            _karakter.removeAt(pos)
                            TambahData()
                            TampilkanData()
                        }
                    )
                    .setNegativeButton(
                        "BATAL",
                        DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(
                                this@MainActivity,
                                "Data Batal Dihapus",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    ).show()
            }
        })
    }
}


