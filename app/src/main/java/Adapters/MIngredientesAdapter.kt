package Adapters

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coockingcase.R
import com.example.coockingcase.databinding.ItemIngredienteBinding

class MIngredientesAdapter : RecyclerView.Adapter<MIngredientesAdapter.ViewHolder>() {
    lateinit var context: Context
    lateinit var cursor : Cursor

    fun MIngredientesAdapter(context: Context, cursor: Cursor){
        this.context = context
        this.cursor = cursor
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(
            R.layout.item_ingrediente,
                parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)

        holder.tvIngrediente.text = cursor.getString(2)
        holder.tvCantidad.text = cursor.getString(3)
    }

    override fun getItemCount(): Int {
        if (cursor == null)
            return 0
        else
            return cursor.count
    }
    inner class ViewHolder: RecyclerView.ViewHolder{

        val tvIngrediente: TextView
        val tvCantidad: TextView

        constructor(view: View) : super(view){
            val bindingItemsRV = ItemIngredienteBinding.bind(view)
            tvIngrediente = bindingItemsRV.tvIngredienteR
            tvCantidad = bindingItemsRV.tvCantidadR

            view.setOnClickListener {

            }
        }
    }


}
