package com.edit.chatnex

import android.app.StatusBarManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.edit.chatnex.databinding.ActivityMainBinding
import com.edit.chatnex.repository.AppRepository
import com.edit.chatnex.repository.AppUrlPath
import com.edit.chatnex.ui.MyPagingAdapter
import com.edit.chatnex.ui.NewsAdapter
import com.edit.chatnex.ui.NewsComparator
import com.edit.chatnex.utils.ParameterUtils
import com.edit.chatnex.view.NewsListViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.net.ssl.SSLEngineResult.Status

//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 初始化ViewPager和TabLayout
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        // 设置ViewPager2
        viewPager.adapter = MyPagingAdapter(this)

        // 设置TabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "全网快讯"
                1 -> "热点财经"
                else -> throw
                IllegalArgumentException("Invalid position")
            }
        }.attach()
    }


//        //设置状态栏字体黑色
////        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
//        supportActionBar?.title = "全网快讯"
//        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FF000000")))
//        supportActionBar?.elevation = 0F
//        setTheme(R.style.CustomNavBarTheme)
//
//        mbinding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(mbinding?.root)
//
//        val pagingAdapter = NewsAdapter(NewsComparator)
//        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        //设置下拉刷新布局的进度圆圈颜色
//        refreshLayout?.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN)
//        //下拉刷新
//        refreshLayout?.setOnRefreshListener {
//            pagingAdapter.refresh()
//        }
//        //设置分割线，系统默认灰色
//        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
//        recyclerView.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = pagingAdapter
//        }
//
//        //Activity 用 lifecycleScope
//        //Fragments 用 viewLifecycleOwner.lifecycleScope
//        lifecycleScope.launch {
//            newsListViewModel.flow.collectLatest { pagingData ->
//                pagingAdapter.submitData(pagingData)
//            }
//
//            val commonApiDataModel = AppRepository.getApiService(AppUrlPath.CLS_HOST).getClsList(AppUrlPath.DEPTHS_PATH,
//                ParameterUtils().getCurrentTime())
//
//            Log.d("mainDataModel=","${commonApiDataModel.data}")
//        }
//
//        //监听加载状态
//        pagingAdapter.addLoadStateListener {
//            when(it.refresh){
//                is LoadState.NotLoading ->{
//                    recyclerView.visibility = View.VISIBLE
//                    refreshLayout.isRefreshing = false
//                }
//                is LoadState.Loading -> {
//                    refreshLayout.isRefreshing = true
//                    recyclerView.visibility = View.VISIBLE
//                }
//                is LoadState.Error -> {
//                    val state = it.refresh as LoadState.Error
//                    refreshLayout.isRefreshing = false
//                    Toast.makeText(this@MainActivity,"Load Error: ${state.error.message}",Toast.LENGTH_SHORT)
//                }
//            }
//        }
//    }
}