package unex.cum.reservasgo_dadm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificacionesWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val mensaje = inputData.getString("mensaje") ?: "Tienes una nueva notificación."

        // Mostrar la notificación
        mostrarNotificacion(mensaje)

        return Result.success()
    }

    private fun mostrarNotificacion(mensaje: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "notificaciones_canal",
                "Notificaciones",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val largeIconBitmap = BitmapFactory.decodeResource(applicationContext.resources,
            R.drawable.ic_logo
        )

        val notification = NotificationCompat.Builder(applicationContext, "notificaciones_canal")
            .setSmallIcon(R.drawable.ic_notificacion)
            .setLargeIcon(largeIconBitmap)
            .setContentTitle("ReservasGo")
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}

class RecordatorioWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val mensaje = inputData.getString("mensaje") ?: "Tienes una reserva pendiente."

        val largeIconBitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_logo)

        val notification = NotificationCompat.Builder(applicationContext, "notificaciones_canal")
            .setSmallIcon(R.drawable.ic_notificacion)
            .setLargeIcon(largeIconBitmap)
            .setContentTitle("Recordatorio de Reserva")
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)

        return Result.success()
    }
}
