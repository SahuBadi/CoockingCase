package Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coockingcase.R
import com.example.coockingcase.databinding.ItemIngredienteBinding
import objetos.Ingrediente

class IngredientesAdapter(private val dataSet: MutableList<Ingrediente>) :
        RecyclerView.Adapter<IngredientesAdapter.ViewHolder>() {

    inner class ViewHolder: RecyclerView.ViewHolder{

        val tvIngrediente: TextView
        val tvCantidad: TextView

        constructor(view: View) : super(view){
            val bindingItemsRV = ItemIngredienteBinding.bind(view)
            tvIngrediente = bindingItemsRV.tvIngredienteR
            tvCantidad = bindingItemsRV.tvCantidadR
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_ingrediente, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvIngrediente.text = dataSet.get(position).ingrediente
        viewHolder.tvCantidad.text = dataSet.get(position).cantidad
    }

    override fun getItemCount() = dataSet.size



}
