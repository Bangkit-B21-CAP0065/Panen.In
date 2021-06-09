package com.panenin.bangkit.b21.cap0065.ui.shopping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panenin.bangkit.b21.cap0065.data.CropEntity
import com.panenin.bangkit.b21.cap0065.databinding.ItemEcommerceBinding

class CropAdapter : RecyclerView.Adapter<CropAdapter.ListViewHolder>() {

    private val listCropEntity = ArrayList<CropEntity>()
    private var tempCount = 0;

    fun setData(cropEntity: List<CropEntity>?) {
        if (cropEntity == null) return
        listCropEntity.clear()
        listCropEntity.addAll(cropEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val viewBinding = ItemEcommerceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listCropEntity[position])
    }

    override fun getItemCount(): Int {
        return listCropEntity.size
    }

    inner class ListViewHolder (private val viewBinding: ItemEcommerceBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(cropEntity: CropEntity) {
            with(viewBinding) {
                tvTitle.text = cropEntity.title
                tvPrice.text = cropEntity.price
                Glide.with(itemView.context)
                    .load(cropEntity.imgPoster)
                    .into(imgItemPhoto)

                btnPlus.setOnClickListener {
                    tempCount = Integer.parseInt(tvItemCount.text as String)
                    tempCount += 1
                    tvItemCount.text = tempCount.toString()
                }

                btnMinus.setOnClickListener {
                    tempCount = Integer.parseInt(tvItemCount.text as String)
                    if (tempCount == 0) {
                        tempCount = 0
                    } else {
                        tempCount -= 1
                    }
                    tvItemCount.text = tempCount.toString()
                }
            }
        }
    }
}