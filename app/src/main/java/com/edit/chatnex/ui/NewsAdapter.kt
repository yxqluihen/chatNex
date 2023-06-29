package com.edit.chatnex.ui

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.edit.chatnex.R
import com.edit.chatnex.model.NewsDataModel


class NewsAdapter(diffCallback: DiffUtil.ItemCallback<NewsDataModel>) :
    PagingDataAdapter<NewsDataModel, NewsAdapter.NewsViewHolder>(diffCallback) {

    private lateinit var onItemClickListener: OnItemClickListener


    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var logo = itemView.findViewById<ImageView>(R.id.app_logo)
        var name = itemView.findViewById<TextView>(R.id.app_name)
        var title = itemView.findViewById<TextView>(R.id.news_title)
        var time = itemView.findViewById<TextView>(R.id.upload_date)
        var content = itemView.findViewById<TextView>(R.id.news_content)
        var expand = itemView.findViewById<TextView>(R.id.cls_expand)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        view.layoutParams = AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        if (news != null) {
            holder.logo.load(news.appLogo) {
                transformations(RoundedCornersTransformation(20f))
                scale(Scale.FILL)
            }
            holder.name.text = news.appName
            holder.title.text = news.title
            holder.time.text = news.timeFat
            holder.content.text = news.content

            // 设置 cls_content 的最大行数为 4，超过部分以省略号结尾
//            holder.content.maxLines = 4
//            holder.content.ellipsize = TextUtils.TruncateAt.END
            // 使用 ViewTreeObserver 监听 content 的布局完成事件
            holder.content.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {

                    Log.d("lineCount=","${holder.content.lineCount}")
                    if (holder.content.lineCount == 4) {
                        holder.expand.visibility = View.GONE
                    }else if (holder.content.lineCount > 4){
                        holder.content.maxLines = 4
                        holder.content.ellipsize = TextUtils.TruncateAt.END
                        holder.expand.visibility = View.VISIBLE
                        holder.expand.text = "...展开"
                        holder.expand.setOnClickListener {
                            if (holder.content.maxLines == 4) {
                                holder.content.maxLines = Int.MAX_VALUE
                                holder.expand.text = "...收起"
                            } else {
                                holder.content.maxLines = 4
                                holder.expand.text = "...展开"
                            }
                        }
                    }else {
                        holder.expand.visibility = View.GONE
                    }
                    // 移除监听器，避免重复调用
                    holder.content.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })


            if (this::onItemClickListener.isInitialized)/*判断lateinit var是已经初始化*/
                onItemClickListener.let {
                    holder.itemView.setOnClickListener(View.OnClickListener {
                        onItemClickListener.onItemClick(it,position,news)
                    })
                }
        }
    }


    interface OnItemClickListener {
        fun onClick(view: View, position: Int,url:String)
        fun onItemClick(view: View,position: Int,packages: NewsDataModel)
        fun onLongClick(view: View, position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

}

object NewsComparator : DiffUtil.ItemCallback<NewsDataModel>() {
    override fun areItemsTheSame(oldItem: NewsDataModel, newItem: NewsDataModel): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(
        oldItem: NewsDataModel,
        newItem: NewsDataModel
    ): Boolean {
        return oldItem == newItem
    }
}



