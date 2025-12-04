# Consultorio
Aquí se sube el proyecto de la materia aplicaciones movil del equipo 7
📱 Consultorio Médico — App Android

Aplicación móvil desarrollada en Android Studio que permite gestionar citas médicas de manera sencilla, rápida y organizada.
El sistema incluye registro, inicio de sesión, agendamiento de citas, consulta de citas realizadas y almacenamiento de datos mediante SQLite.

🧾 Características principales
🔐 1. Autenticación de usuarios

Registro de nuevos usuarios.

Inicio de sesión con validación local.

Almacenamiento seguro en SQLite.

📅 2. Agendamiento de citas

Desde el menú principal, el usuario puede seleccionar “Hacer cita”.

Flujo de creación de citas:

Selección de especialidad médica.

Ingreso del nombre del paciente.

Elección de la fecha (excepto domingos, que no se trabaja).

Selección de un horario disponible (de 9:00 AM a 6:00 PM).

Confirmación final de la cita.

Al confirmar:

Se muestra una pantalla de “Cita realizada”.

La cita queda guardada automáticamente en la base de datos.

📋 3. Mis citas

Desde el menú principal se accede a la sección “Mis citas”.

El usuario puede visualizar:

Fecha de la cita

Hora

Especialidad

Nombre del paciente

Además, se puede tomar y guardar una foto del paciente asociada a la cita.

🗄️ Base de datos

El proyecto utiliza una base de datos SQLite para almacenar:

Usuarios (registro/login)

Citas agendadas

Información adicional como fotografías del paciente

Todo se maneja de forma local sin necesidad de conexión a internet.

🛠️ Tecnologías utilizadas

Kotlin / Java (según corresponda en tu proyecto)

Android Studio

SQLite (almacenamiento local)

Intents para navegación

Camera API / ACTION_IMAGE_CAPTURE (para tomar fotos)

RecyclerView para listar citas

🚀 Funcionalidades adicionales (opcionales para futuro)

Notificaciones de recordatorio de cita

Integración con Firestore o una API externa

Historial clínico del paciente

Cancelación o edición de citas

📁 Estructura del proyecto (descripción general)
/app
 ├── activities/
 ├── fragments/
 ├── database/
 │     └── SQLiteHelper
 ├── models/
 ├── adapters/
 └── utils/

📸 Capturas de pantalla (pendiente)

Puedes agregar imágenes aquí más adelante:

![Login](images/login.png)
![Registro](images/register.png)
![Menu](images/menu.png)
![Hacer cita](images/hacer_cita.png)
![Mis citas](images/mis_citas.png)

📄 Licencia

Este proyecto es de uso personal y educativo.
Puedes agregar una licencia si quieres compartirlo públicamente
