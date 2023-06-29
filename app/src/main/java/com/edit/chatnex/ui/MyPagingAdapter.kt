package com.edit.chatnex.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyPagingAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity)  {

    // 返回ViewPager2中的Fragment
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewsFragment()
            1 -> ClsFragment()
            else -> Fragment()
        }
    }

    // 返回ViewPager2中的Fragment数量
    override fun getItemCount(): Int {
        return 2
    }
}