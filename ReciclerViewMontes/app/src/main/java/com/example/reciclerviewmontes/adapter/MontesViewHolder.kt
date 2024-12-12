package com.example.reciclerviewmontes.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.reciclerviewmontes.Monte
import com.example.reciclerviewmontes.R
import com.example.reciclerviewmontes.databinding.ItemMonteBinding

class MontesViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemMonteBinding.bind(view)

    fun render(monteModel: Monte, onClickDelete: (Int) -> Unit) {
        binding.tvMTNombre.text = monteModel.nombre
        binding.tvMTAltura.text = monteModel.altura
        binding.tvContinente.text = monteModel.continente

        Glide.with(binding.ivMT.context)
            .asBitmap()
            .load(monteModel.foto)
            .into(object : CustomTarget<Bitmap>(1280, 720) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.ivMT.scaleType = ImageView.ScaleType.CENTER_CROP
                    binding.ivMT.setImageBitmap(resource)
                }

                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    binding.ivMT.setImageResource(R.drawable.carga)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    binding.ivMT.setImageResource(R.drawable.error)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })

        binding.btnDelete.setOnClickListener {
            onClickDelete(adapterPosition)
        }
    }
}