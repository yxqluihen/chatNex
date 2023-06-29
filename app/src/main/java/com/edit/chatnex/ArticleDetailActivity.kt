package com.edit.chatnex

import android.R
import android.os.AsyncTask
import android.os.Bundle
import android.text.Spanned
import android.util.Log
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import com.edit.chatnex.databinding.ActivityArticleDetailBinding
import com.edit.chatnex.model.ArticleDetailModel
import com.edit.chatnex.repository.AppRepository
import com.edit.chatnex.repository.AppUrlPath
import com.edit.chatnex.repository.NewsApiResponse
import com.edit.chatnex.utils.HtmlImageGetter
import kotlinx.coroutines.launch


class ArticleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleDetailBinding
    var article: NewsApiResponse<ArticleDetailModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleId = intent.getIntExtra("articleId", -1)

        lifecycleScope.launch {
            // 根据articleId获取文章信息
            article = AppRepository.getApiService(AppUrlPath.CLS_HOST).getArticleDetail(AppUrlPath.DETAIL_PATH,articleId)
            Log.d("content=","${article?.data?.content}")
            binding.articleTitle.text = intent.getStringExtra("title").toString()
            binding.articleAuthor.text = article?.data?.author

//            if (binding.articleAuthor.text == ""){
//                val params = RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.WRAP_CONTENT,
//                    RelativeLayout.LayoutParams.WRAP_CONTENT
//                )
//                params.addRule(RelativeLayout.ALIGN_TOP, binding.articleAuthor)
//                params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.)
//                params.addRule(RelativeLayout.ALIGN_LEFT, R.id.textView1)
//                textView2.setLayoutParams(params)
//            }

            binding.articleUploadDate.text = intent.getStringExtra("uploadDate").toString()
            binding.articleBrief.text = intent.getStringExtra("brief").toString()
            //将content内容以HTML文本加载和显示图片。
//            binding.articleContent.text = article?.data?.content?.let {
//                HtmlCompat.fromHtml(
//                    it, HtmlCompat.FROM_HTML_MODE_LEGACY,
//                    HtmlImageGetter(binding.articleContent), null)
//            }

            LoadHtmlTask(binding.articleContent).execute(article?.data?.content)


        }

    }
    private inner class LoadHtmlTask(private val textView: TextView) : AsyncTask<String, Void, Spanned>() {

        override fun doInBackground(vararg params: String): Spanned {
            val html = params[0]
            return HtmlCompat.fromHtml(
                html,
                HtmlCompat.FROM_HTML_MODE_LEGACY,
                HtmlImageGetter(textView),
                null
            )
        }

        override fun onPostExecute(result: Spanned) {
            textView.text = result
        }
    }
}