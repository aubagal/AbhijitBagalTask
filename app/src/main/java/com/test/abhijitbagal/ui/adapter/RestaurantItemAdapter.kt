package com.test.abhijitbagal.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.test.abhijitbagal.R
import com.test.abhijitbagal.data.RestaurantData

class RestaurantItemAdapter(private val mActivity: Activity,
                            private val menuItems: ArrayList<RestaurantData>, mListener: ItemClick, private val isCartActivity:Boolean
) : RecyclerView.Adapter<RestaurantItemAdapter.DataViewHolder>() {
    var mListener: ItemClick? = null

    init {
        this.mListener = mListener
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemData: RestaurantData, mListener: ItemClick?,position: Int,isCartActivity:Boolean) {
            var txtItemName: TextView? = itemView.findViewById(R.id.txtItemName)
            var txtItemDescription: TextView? = itemView.findViewById(R.id.txtItemDescription)
            var txtPrice: TextView? = itemView.findViewById(R.id.txtPrice)
            var txtItemCount: TextView? = itemView.findViewById(R.id.txtItemCount)
            var btnPlus: ImageView? = itemView.findViewById(R.id.btnPlus)
            var btnMinus: ImageView? = itemView.findViewById(R.id.btnMinus)
            var btnAdd: TextView? = itemView.findViewById(R.id.btnAdd)
            var groupAll: Group? = itemView.findViewById(R.id.groupAll)

            txtItemName?.text = itemData.itemName
            txtItemDescription?.text = itemData.itemDescription
            txtPrice?.text = itemData.currency + itemData.itemPrice
            txtItemCount?.text = itemData.itemCount.toString()

            if (!isCartActivity) {
                if (itemData.itemCount == 0) {
                    groupAll?.visibility = View.GONE
                    btnAdd?.visibility = View.VISIBLE
                } else {
                    groupAll?.visibility = View.VISIBLE
                    btnAdd?.visibility = View.GONE
                }
            }

            btnPlus?.setOnClickListener {
                mListener?.onPlusButtonClick(itemData,position)
            }

            btnMinus?.setOnClickListener {
                mListener?.onMinusButtonClick(itemData,position)
            }

            btnAdd?.setOnClickListener {
                groupAll?.visibility=View.VISIBLE
                btnAdd?.visibility=View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = menuItems.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(menuItems[position], mListener,position,isCartActivity)

    fun addData(list: ArrayList<RestaurantData>) {
        menuItems.clear()
        if (isCartActivity) {
         var items=   list.filter { it.itemCount!=0 }
           if (items.isEmpty())mActivity.finish()
            items.reversed()
            menuItems.addAll(items)
        }else {
            menuItems.addAll(list)
        }
    }

    interface ItemClick {
        fun onPlusButtonClick(data:RestaurantData,position: Int)
        fun onMinusButtonClick(data:RestaurantData,position: Int)
    }

}