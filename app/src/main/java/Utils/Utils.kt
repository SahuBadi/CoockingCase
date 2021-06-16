package Utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import java.io.ByteArrayOutputStream

object Utils {
    fun obtenerBytes (bitmap: Bitmap) :ByteArray{
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
    fun obtenerImagen (foto:ByteArray) :Bitmap{
        return BitmapFactory.decodeByteArray(foto, 0, foto.size)
    }
}