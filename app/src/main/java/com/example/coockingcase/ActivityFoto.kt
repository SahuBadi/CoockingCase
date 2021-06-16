package com.example.coockingcase

import Utils.Utils
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.coockingcase.databinding.ActivityFotoBinding
import kotlinx.android.synthetic.main.activity_foto.*


class ActivityFoto : AppCompatActivity() {

    private lateinit var binding: ActivityFotoBinding
    private lateinit var filepath: Uri
    private var lastid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFotoBinding.inflate(layoutInflater)
        lastid = intent.getIntExtra("lastid", 0)
        setContentView(binding.root)


        //Solicitamos los permisos de cámara y acceso a galería al entrar en la activity por primera vez,
        // en las consecutivas ya no lo requerirá al haberse concedido ya
        pedirPermisos()

        binding.btnCamara.setOnClickListener {
            abrirCamara()
        }
        binding.btnGaleria.setOnClickListener {
            abrirGaleria()
        }
        binding.btnSiguientefoto.setOnClickListener {
            var intent = Intent(this, ActivityTransicion::class.java)
            guardarFoto()
            val b = Bundle()
            b.putInt("lastid", lastid)
            intent.putExtras(b)
            startActivity(intent)
        }
    }
    private fun abrirGaleria(){
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)//creamos la conexión con la galería. Acción obtener contenido
        startActivityForResult(Intent.createChooser(i,"Escoge una imágen"),111)//código galería
    }
    private fun abrirCamara(){
        var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)//creamos la conexión con la cámara. Acción capturar foto
        startActivityForResult(i,101)//código cámara
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            var foto = data?.getParcelableExtra<Bitmap>("data")
            ivFoto.setImageBitmap(foto)
            btnSiguientefoto.isEnabled = true


        }else if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null){
            filepath = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filepath)
            ivFoto.setImageBitmap(bitmap)
            btnSiguientefoto.isEnabled = true

        }
    }
    private fun guardarFoto(){
        val bitmap = (ivFoto.drawable as BitmapDrawable).bitmap
        DBHelper(applicationContext)
                .insertFoto(lastid, Utils.obtenerBytes(bitmap))
    }
    private fun pedirPermisos(){
        ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA), PackageManager.PERMISSION_GRANTED)
    }

}