package com.rezapour.carstask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rezapour.carstask.R
import com.rezapour.carstask.business.model.CarModel
import com.rezapour.carstask.utils.OnclickRecyclerviewListener


class CarsLIstAdapter constructor(val onclickListener: OnclickRecyclerviewListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<CarModel> = ArrayList()

    fun addItems(carsList: List<CarModel>) {
        items = carsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_car_info, parent, false)
        return CarInfoViewHolder(view, onclickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CarInfoViewHolder -> holder.bind(items.get(position))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class CarInfoViewHolder(
        private val view: View,
        private val onclickListener: OnclickRecyclerviewListener
    ) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        private val ivCarImage: ImageView = itemView.findViewById(R.id.iv_car)
        private val txtDriverName: TextView = itemView.findViewById(R.id.txt_driverName)
        private val txtCarInfo: TextView = itemView.findViewById(R.id.txt_carInfo)
        private val txtLicensePlate: TextView = itemView.findViewById(R.id.txt_licensePlate)

        init {
            view.setOnClickListener(this)
        }

        fun bind(car: CarModel) {

            txtDriverName.text = car.name
            txtLicensePlate.text = car.licensePlate
            val model = car.modelName
            txtCarInfo.text = model

            Glide.with(view)
                .load(car.carImageUrl)
                .placeholder(R.drawable.ic_car)
                .error(R.drawable.ic_car)
                .into(ivCarImage)
        }
        override fun onClick(p0: View?) {
            onclickListener.onRowListener(adapterPosition)
        }

    }

}