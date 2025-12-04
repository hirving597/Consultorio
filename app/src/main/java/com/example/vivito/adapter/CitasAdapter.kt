package com.example.vivito.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vivito.R
import com.example.vivito.model.Cita

class CitasAdapter(
    private var citas: List<Cita>,
    private val onCameraClick: (Cita) -> Unit // Callback para abrir cámara
) : RecyclerView.Adapter<CitasAdapter.CitaViewHolder>() {

    inner class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPaciente: TextView = itemView.findViewById(R.id.tvPaciente)
        val tvEspecialidad: TextView = itemView.findViewById(R.id.tvEspecialidad)
        val tvFechaHora: TextView = itemView.findViewById(R.id.tvFechaHora)
        val btnCamera: Button = itemView.findViewById(R.id.btnCamera)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun getItemCount(): Int = citas.size

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]
        holder.tvPaciente.text = "Paciente: ${cita.paciente}"
        holder.tvEspecialidad.text = "Especialidad: ${cita.especialidad}"
        holder.tvFechaHora.text = "Fecha: ${cita.fecha} - Hora: ${cita.hora}"

        holder.btnCamera.setOnClickListener {
            onCameraClick(cita)
        }
    }

    fun updateData(newCitas: List<Cita>) {
        citas = newCitas
        notifyDataSetChanged()
    }
}
