package com.edit.chatnex.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.edit.chatnex.R
import com.edit.chatnex.databinding.FragmentNewsBinding
import com.edit.chatnex.view.NewsListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val viewModel by viewModels<NewsListViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagingAdapter = NewsAdapter(NewsComparator)

        //设置下拉刷新布局的进度圆圈颜色
        binding.swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN)

        //下拉刷新
        binding.swipeRefreshLayout.setOnRefreshListener {
            pagingAdapter.refresh()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        //设置分割线，系统默认灰色
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pagingAdapter
        }

        // 启动一个协程，在协程中调用collectLatest函数观察ViewModel中的数据变化
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }

        //监听加载状态
        pagingAdapter.addLoadStateListener {
            when(it.refresh){
                is LoadState.NotLoading ->{
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                is LoadState.Loading -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                    binding.recyclerView.visibility = View.VISIBLE
                }
                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(requireContext(),"Load Error: ${state.error.message}", Toast.LENGTH_SHORT)
                }
            }
        }
    }
}