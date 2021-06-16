package Adapters

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.coockingcase.ActivityMostrar
import com.example.coockingcase.DBHelper
import com.example.coockingcase.R
import com.example.coockingcase.databinding.ActivityRecetasBinding
import com.example.coockingcase.databinding.ItemRecetaBinding

class RecetasAdapter  : RecyclerView.Adapter<RecetasAdapter.RecetasHolder>() {

    lateinit var context: Context
    lateinit var cursor: Cursor
    lateinit var dbHelper: DBHelper

    fun RecetasAdapter(context: Context, cursor: Cursor){
        this.context = context
        this.cursor = cursor
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetasHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecetasHolder(inflater.inflate(R.layout.item_receta, parent, false))
    }

    override fun onBindViewHolder(holder: RecetasHolder, position: Int) {
        cursor.moveToPosition(position)
        holder.idReceta = cursor.getInt(0)
        holder.ivReceta.setImageBitmap(obtenerImagen(cursor.getBlob(8)))
        holder.tvNombre.text = cursor.getString(1)
        holder.tvDificultad.text = cursor.getString(2)
        holder.tvTiempo.text = cursor.getString(4)
    }

    override fun getItemCount(): Int {
        if (cursor == null)
            return 0
        else
            return cursor.count
    }
    inner class RecetasHolder : RecyclerView.ViewHolder {
        var idReceta: Int = 0
        val ivReceta: ImageView
        val tvNombre: TextView
        val tvDificultad: TextView
        val tvTiempo: TextView

        constructor(view: View) : super (view){
            dbHelper = DBHelper(context)
            val bindingItemrecRV = ItemRecetaBinding.bind(view)
            ivReceta = bindingItemrecRV.ivFotoRV
            tvNombre = bindingItemrecRV.tvNombreRV
            tvDificultad = bindingItemrecRV.tvDificultadRV
            tvTiempo = bindingItemrecRV.tvTiempoRV

            ivReceta.setOnClickListener {
                popupMenus(it)
                Toast.makeText(context, "¿Borramos esta receta?",Toast.LENGTH_SHORT).show()
            }

            view.setOnClickListener {
                val  intent = Intent(context, ActivityMostrar::class.java)
                //Toast.makeText(context, idReceta.toString(), Toast.LENGTH_SHORT).show()
                intent.putExtra("lastid",idReceta)
                context.startActivity(intent)
            }

        }

        private fun popupMenus(view: View) {
            val popupMenus = PopupMenu(context, view)
            popupMenus.inflate(R.menu.show_delete)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.delete->{
                        AlertDialog.Builder(context)
                            .setTitle("Borrar Receta")
                            .setIcon(R.drawable.ic__warning)
                            .setMessage("¿Estás segur@?")
                            .setPositiveButton("Sí"){
                                dialog,_->
                                borrarRecetaYRefrescar(idReceta)
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancelar"){
                                    dialog,_->
                                dialog.dismiss()
                            }.show()
                        true

                    }
                    else->true
                }
            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu, true)
        }
    }

    fun borrarRecetaYRefrescar(idReceta:Int) {
        dbHelper.deleteRecetaCompleta(idReceta)
        cursor = dbHelper.getRecetas()
        notifyDataSetChanged()
    }

    fun RefrescarRecetas() {
        cursor = dbHelper.getRecetas()
        notifyDataSetChanged()
    }

    fun obtenerImagen (foto:ByteArray) :Bitmap{
        return BitmapFactory.decodeByteArray(foto, 0, foto.size)
    }

}