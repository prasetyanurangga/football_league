package com.prasetyanurangga.footballleague.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager?) : FragmentPagerAdapter(fragmentManager!!) {
    var fragmentList = arrayListOf<Fragment>()
    var titleList = arrayListOf<String>()

    fun populateFragment(fragment: Fragment, title: String, context: Context) {
        fragmentList.add(fragment)
        titleList.add(title)
    }
    override fun getItem(position: Int) = fragmentList[position]
    override fun getCount() = fragmentList.size
    override fun getPageTitle(position: Int) = titleList[position]
}