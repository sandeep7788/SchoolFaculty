package com.education.Faculty.Adapter

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.ButterKnife
import com.allattentionhere.autoplayvideos.AAH_CustomViewHolder
import com.allattentionhere.autoplayvideos.AAH_VideoImage
import com.allattentionhere.autoplayvideos.AAH_VideosAdapter
import com.education.Faculty.API.MyModel
import com.dating.schoolfaculty.R
import com.squareup.picasso.Picasso


class VideoAdapter(list_urls: List<MyModel>, p: Picasso) :
    AAH_VideosAdapter() {
    private val list: List<MyModel>
    var picasso: Picasso

    inner class MyViewHolder(x: View?) : AAH_CustomViewHolder(x) {
        val tv: TextView
        val img_vol: ImageView
        val img_playback: ImageView
        val pb: ProgressBar
        val vi:AAH_VideoImage
        var isMuted = false

        init {
            tv = ButterKnife.findById(x!!, R.id.tv)
            img_vol = ButterKnife.findById(x, R.id.img_vol)
            img_playback = ButterKnife.findById(x, R.id.img_playback)
            pb=ButterKnife.findById(x,R.id.pb)
            vi=ButterKnife.findById(x,R.id.vi)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AAH_CustomViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_video_adapter, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: AAH_CustomViewHolder, position: Int) {
        (holder as MyViewHolder).tv.setText(list[position].getName())

        holder.setImageUrl(list[position].getImage_url())
        holder.setVideoUrl(list[position].getVideo_url())
        //load image/thumbnail into imageview
        if (list[position].getImage_url() != null && !list[position].getImage_url().isEmpty())
            picasso.load(holder.getImageUrl()).config(Bitmap.Config.RGB_565)
            .into(holder.getAAH_ImageView())

        /*Log.e("@@l", holder.isLooping.toString())
        holder.tv.setOnFocusChangeListener { v, hasFocus ->
            Log.e("@@l1", hasFocus.toString())
        }*/

        holder.vi.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus)
            {
                holder.pb.visibility=View.VISIBLE
            }
            else
            {
                holder.pb.visibility=View.VISIBLE
            }
            holder.pb.visibility=View.VISIBLE
            Log.e("@@f",hasFocus.toString())
        }

    }
    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    init {
        list = list_urls
        picasso = p
    }
}