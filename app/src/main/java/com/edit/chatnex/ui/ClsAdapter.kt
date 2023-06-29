package com.edit.chatnex.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.marginStart
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.edit.chatnex.ArticleDetailActivity
import com.edit.chatnex.R
import com.edit.chatnex.model.ArticleDataModel
import com.edit.chatnex.model.NewsDataModel
import com.edit.chatnex.utils.ParameterUtils


class ClsAdapter(private val context: Context, diffCallback: DiffUtil.ItemCallback<ArticleDataModel>) :
    PagingDataAdapter<ArticleDataModel, ClsAdapter.ClsViewHolder>(diffCallback) {

    private lateinit var onItemClickListener: OnItemClickListener


    class ClsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var logo = itemView.findViewById<ImageView>(R.id.article_img)
        var title = itemView.findViewById<TextView>(R.id.cls_title)
        var content = itemView.findViewById<TextView>(R.id.cls_content)
        var tag = itemView.findViewById<TextView>(R.id.cls_tag)
        var time = itemView.findViewById<TextView>(R.id.cls_upload_date)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cls_item, parent, false)
        view.layoutParams = AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT)
        return ClsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClsViewHolder, position: Int) {
        val cls = getItem(position)

        if (cls != null) {
            holder.logo.load(cls.img) {
                transformations(RoundedCornersTransformation(20f))
                scale(Scale.FILL)
            }
            holder.title.text = cls.title
            holder.content.text = cls.brief
            if (!cls.tags.isEmpty()){
//                val colorString = "#DE0723"
                holder.tag.text = cls.tags.get(0).name
                val radius = context.resources.getDimension(R.dimen.corner_radius)
                val color = Color.parseColor(cls.tags.get(0).color)
                val drawable = GradientDrawable().apply {
                    cornerRadius = radius
                    setColor(color)
                }
                holder.tag.background = drawable
            }else{
                val params = holder.time.layoutParams as RelativeLayout.LayoutParams
                params.addRule(RelativeLayout.BELOW, R.id.cls_content)
                params.addRule(RelativeLayout.ALIGN_LEFT, R.id.cls_content)
                params.marginStart = 0
                holder.time.layoutParams = params
            }
            holder.time.text = ParameterUtils().getArticleTime(cls.ctime)

            if (this::onItemClickListener.isInitialized)/*判断lateinit var是已经初始化*/
                onItemClickListener.let {
                    holder.itemView.setOnClickListener(View.OnClickListener {
                        onItemClickListener.onItemClick(it,position,cls)
                    })
                }

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ArticleDetailActivity::class.java)
                intent.putExtra("articleId", cls.articleId)
                intent.putExtra("title", cls.title)
                intent.putExtra("brief", cls.brief)
                intent.putExtra("uploadDate", ParameterUtils().getArticleTime(cls.ctime))
                holder.itemView.context.startActivity(intent)
            }

        }


    }


    interface OnItemClickListener {
        fun onClick(view: View, position: Int,url:String)
        fun onItemClick(view: View,position: Int,cls: ArticleDataModel)
        fun onLongClick(view: View, position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

}

object ClsComparator : DiffUtil.ItemCallback<ArticleDataModel>() {
    override fun areItemsTheSame(oldItem: ArticleDataModel, newItem: ArticleDataModel): Boolean {
        // Id is unique.
        return oldItem.articleId == newItem.articleId

    }

    override fun areContentsTheSame(
        oldItem: ArticleDataModel,
        newItem: ArticleDataModel
    ): Boolean {
        return oldItem == newItem
    }
}



