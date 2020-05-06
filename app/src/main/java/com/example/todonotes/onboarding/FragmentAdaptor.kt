package com.example.todonotes.onboarding

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.Fragment

class FragmentAdaptor(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            1 -> return OnBoardingTwoFragment()
            else -> return OnBoardingOneFragment()
        }
    }


    override fun getCount(): Int {
        return 2
    }
}