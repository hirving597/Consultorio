package com.example.vivito

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DatosPCitaActivity : AppCompatActivity() {

    private lateinit var tvEspecialidad: TextView
    private lateinit var btnSeleccionarFecha: Button
    private lateinit var timePicker: TimePicker
    private var fechaSeleccionada: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_pcita)

        val btnVolver = findViewById<Button>(R.id.btnVolver)
        val btnConfirmar = findViewById<Button>(R.id.btnConfirmar)

        tvEspecialidad = findViewById(R.id.tvEspecialidad)
        btnSeleccionarFecha = findViewById(R.id.btnSeleccionarFecha)
        timePicker = findViewById(R.id.timePicker)

        // Recibir especialidad
        val especialidadRecibida = intent.getStringExtra("especialidad")
        tvEspecialidad.text = "Especialidad: $especialidadRecibida"

        // 24h view
        timePicker.setIs24HourView(true)

        // Botón para seleccionar fecha
        btnSeleccionarFecha.setOnClickListener { mostrarDatePicker() }

        // Restringir TimePicker entre 9:00 y 18:00
        timePicker.setOnTimeChangedListener { _, hour, minute ->
            var h = hour
            var m = minute
            var error = false

            if (h < 9) { h = 9; m = 0; error = true }
            if (h > 18 || (h == 18 && m > 0)) { h = 18; m = 0; error = true }

            if (error) {
                timePicker.hour = h
                timePicker.minute = m
                mostrarError("El horario permitido es de 9:00 a 18:00.")
            }
        }

        // Botones Volver y Confirmar
        btnVolver.setOnClickListener { goVolver() }
        btnConfirmar.setOnClickListener { validarYConfirmar() }
    }

    private fun mostrarDatePicker() {
        val constraints = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now()) // Solo fechas futuras
            .build()

        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Seleccionar fecha")
            .setCalendarConstraints(constraints)
            .build()

        picker.show(supportFragmentManager, "DATE_PICKER")

        picker.addOnPositiveButtonClickListener { seleccion ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = seleccion

            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                mostrarError("Los domingos no están disponibles. Selecciona otra fecha.")
                fechaSeleccionada = null
                btnSeleccionarFecha.text = "Seleccionar fecha"
            } else {
                fechaSeleccionada = seleccion
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                btnSeleccionarFecha.text = sdf.format(seleccion)
            }
        }
    }

    private fun validarYConfirmar() {
        if (fechaSeleccionada == null) {
            mostrarError("Debes seleccionar una fecha.")
            return
        }

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = fechaSeleccionada!!
        val diaSemana = calendar.get(Calendar.DAY_OF_WEEK)

        if (diaSemana == Calendar.SUNDAY) {
            mostrarError("Los domingos no se pueden agendar citas.")
            return
        }

        val hora = timePicker.hour
        val minuto = timePicker.minute
        if (hora < 9 || hora > 18 || (hora == 18 && minuto > 0)) {
            mostrarError("El horario permitido es de 9:00 a 18:00.")
            return
        }

        goConfirmar()
    }

    private fun mostrarError(mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(mensaje)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun goVolver() {
        startActivity(Intent(this, CitasActivity::class.java))
    }

    private fun goConfirmar() {
        startActivity(Intent(this, CitaHechaActivity::class.java))
    }
}
