package com.example.vivito

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vivito.adapter.CitasAdapter
import com.example.vivito.database.AppDatabaseC
import com.example.vivito.model.Cita
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MisCitasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CitasAdapter
    private var citaSeleccionada: Cita? = null

    // Lanzador de cámara
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            Toast.makeText(this, "Foto tomada con éxito", Toast.LENGTH_SHORT).show()
            // Aquí puedes guardar la imagen en tu base de datos o mostrarla
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_citas)

        recyclerView = findViewById(R.id.recyclerCitas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = CitasAdapter(emptyList()) { cita ->
            citaSeleccionada = cita
            abrirCamara()
        }
        recyclerView.adapter = adapter

        loadCitas()
    }

    private fun loadCitas() {
        val db = AppDatabaseC.getDatabase(this)
        val citaDao = db.citaDao()

        CoroutineScope(Dispatchers.IO).launch {
            val citas: List<Cita> = citaDao.getAllCitas()
            withContext(Dispatchers.Main) {
                adapter.updateData(citas)
            }
        }
    }

    private fun abrirCamara() {
        // Verificamos permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            cameraLauncher.launch(null)
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 1001)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            abrirCamara()
        } else {
            Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }
}

