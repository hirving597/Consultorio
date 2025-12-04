package com.example.vivito

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.vivito.database.AppDatabaseC
import com.example.vivito.model.Cita
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class DatosPCitaActivity : AppCompatActivity() {

    private lateinit var tvEspecialidad: TextView
    private lateinit var datePicker: DatePicker
    private lateinit var timePicker: TimePicker
    private lateinit var edtNombrePaciente: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_pcita)

        val btnVolver = findViewById<Button>(R.id.btnVolver)
        val btnConfirmar = findViewById<Button>(R.id.btnConfirmar)

        tvEspecialidad = findViewById(R.id.tvEspecialidad)
        datePicker = findViewById(R.id.datePicker)
        timePicker = findViewById(R.id.timePicker)
        edtNombrePaciente = findViewById(R.id.edtNombrePaciente)

        // Recibir especialidad
        val especialidadRecibida = intent.getStringExtra("especialidad")
        tvEspecialidad.text = "Especialidad: $especialidadRecibida"


        timePicker.setIs24HourView(true)


        val today = Calendar.getInstance()
        datePicker.minDate = today.timeInMillis


        datePicker.init(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, dayOfMonth ->
            val selected = Calendar.getInstance()
            selected.set(year, month, dayOfMonth)
            if (selected.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                mostrarError("Los domingos no están disponibles. Selecciona otra fecha.")
                datePicker.updateDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH))
            }
        }

        // Bloquear horarios fuera de 9:00–18:00 en TimePicker
        timePicker.setOnTimeChangedListener { _, hour, minute ->
            var h = hour
            var m = minute
            if (h < 9) { h = 9; m = 0 }
            if (h > 18 || (h == 18 && m > 0)) { h = 18; m = 0 }
            if (h != hour || m != minute) {
                timePicker.hour = h
                timePicker.minute = m
                mostrarError("El horario permitido es de 9:00 AM a 6:00 PM.")
            }
        }

        btnVolver.setOnClickListener { goVolver() }

        btnConfirmar.setOnClickListener {
            val nombrePaciente = edtNombrePaciente.text.toString()
            val selected = Calendar.getInstance()
            selected.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            val diaSemana = selected.get(Calendar.DAY_OF_WEEK)
            val hora = timePicker.hour
            val minuto = timePicker.minute

            // Validaciones
            if (nombrePaciente.isEmpty()) {
                mostrarError("Ingresa el nombre del paciente.")
                return@setOnClickListener
            }

            if (diaSemana == Calendar.SUNDAY) {
                mostrarError("Los domingos no se pueden agendar citas.")
                return@setOnClickListener
            }

            if (hora < 9 || hora > 18 || (hora == 18 && minuto > 0)) {
                mostrarError("El horario permitido es de 9:00 AM a 6:00 PM.")
                return@setOnClickListener
            }

            // Convertir fecha y hora a String
            val fecha = "${datePicker.year}-${datePicker.month + 1}-${datePicker.dayOfMonth}"
            val horaStr = String.format("%02d:%02d", hora, minuto)

            // Crear objeto Cita
            val cita = Cita(
                paciente = nombrePaciente,
                especialidad = especialidadRecibida ?: "",
                fecha = fecha,
                hora = horaStr
            )

            // Guardar cita en la base de datos
            val db = AppDatabaseC.getDatabase(this)
            val citaDao = db.citaDao()

            CoroutineScope(Dispatchers.IO).launch {
                citaDao.insertCita(cita)
                withContext(Dispatchers.Main) {
                    mostrarExito("Cita registrada correctamente")
                    goConfirmar()
                }
            }
        }
    }

    private fun mostrarError(mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(mensaje)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun mostrarExito(mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle("Éxito")
            .setMessage(mensaje)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun goVolver() {
        val i = Intent(this, CitasActivity::class.java)
        startActivity(i)
    }

    private fun goConfirmar() {
        val i = Intent(this, CitaHechaActivity::class.java)
        startActivity(i)
    }
}
