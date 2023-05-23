package com.example.belajardataenksripsi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import com.example.belajardataenksripsi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        // Membuat tempat simpanan
        val tempatData = getPreferences(Context.MODE_PRIVATE)
        // Membuat fungsi simpan

        binding.btnSimpan.setOnClickListener {
            // Menyimpan nilai dari XML ke kotlin
            var nama = binding.etNama.text.toString()
            // Menguji nilai nama kosong
            if (nama.isEmpty()){
                // Membuat pesan dan menampilkan
                Toast.makeText(
                    this,
                    "Masukkan Nama Anda",
                    Toast.LENGTH_SHORT
                ).show()
                // Kembali ke fungsi simpan
                return@setOnClickListener
                // Membuat tempat simpanan unutk nilai variable yang dienkripsi
                val simpanNama = enkripsiData(nama)
                // Merubah nilai yang disimpan
                val simpanData = tempatData.edit()
                // Memindahkan nilai ke variable preference
                simpanData.putString("nama", simpanNama)
                // Simpan nilai preference
                simpanData.apply()
                Toast.makeText(
                    this,
                    "Nama anda tersimpan",
                    Toast.LENGTH_SHORT
                ).show()
                // Membuat fungsi baru
                kosongkanText()
            }

            binding.btnPanggil.setOnClickListener {
                var panggilNama = tempatData.getString("nama", null)
                val panggilHasil = deskripsiData (panggilNama.toString())
                binding.tvHasil.text = "$panggilHasil \n $panggilNama"
            }
        }
    }
    private fun enkripsiData (namaEnkripsi: String): String{
        val enkripsi = Base64.encode(
            namaEnkripsi.toByteArray(),
            Base64.DEFAULT
        )
        return String (enkripsi)
    }

    private fun kosongkanText(){
        binding.etNama.setText("")
        binding.tvHasil.setText("")
    }

    private fun deskripsiData(namaEnksripsi: String):String{
        val enkripsi = Base64.decode(
            namaEnksripsi.toByteArray(),
            Base64.DEFAULT
        )
        return String(enkripsi)
    }
}
