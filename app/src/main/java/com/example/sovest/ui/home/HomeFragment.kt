package com.example.sovest.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sovest.MainActivity
import com.example.sovest.R
import com.example.sovest.data.Model
import com.example.sovest.data.getJsonDataFromAsset
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.math.round

class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var dLimitBase = 2000
    private var mLimit: Double = 0.0
    private var dLimit: Double = 0.0
    private var days = 30
    private var goodDaysCounter = 0
    private lateinit var spendings: List<Double>
    private var per = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as MainActivity
        homeViewModel = ViewModelProvider(activity).get(HomeViewModel::class.java)
        activity.touchOnArea.setOnClickListener(this)

        monthlyLimit.text = activity.intent?.getStringExtra("LIMIT") + "\u20AC"
        mLimit = activity.intent?.getStringExtra("LIMIT").toString().toDouble()

        dLimit = mLimit / 30
        dLimitBase = dLimit.toInt()
        dailyLimit.text = dLimit.toInt().toString() + "." + (0..99).random() + "\u20AC"

        fetchData()

        checkmarkView.addAnimatorUpdateListener { valueAnimator ->
            // Set animation progress
            val progress = (valueAnimator.animatedValue as Float * 100).toInt()
            if (progress == 99) {
                checkmarkView.alpha = 0f
                paymentCompleted.alpha = 0f
                if (per <= 0)
                    moneyLottieView.visibility = View.VISIBLE
            }
        }
    }

    private fun fetchData() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "model.json")!!
        Log.i("data", jsonFileString)

        val gson = Gson()
        val listPersonType = object : TypeToken<Model>() {}.type

        val transactions: Model = gson.fromJson(jsonFileString, listPersonType)
        spendings = transactions.map { it.amount }.filter { it < 0 }.toList()
    }

    override fun onStart() {
        super.onStart()
        activity?.touchOnArea?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v == null)
            return
        if (v.id == R.id.touchOnArea) {
           if (days > 0) {
                val spending = (spendings[days - 1] * (-1)).toInt()
                mLimit -= spending
                dLimit = (mLimit / days)
                days -= 1
                val decimals = (0..99).random()
                monthlyLimit.text = mLimit.toInt().toString() + "." + decimals + "\u20AC"
                dailyLimit.text = dLimit.toInt().toString() + "." + decimals + "\u20AC"
                if (mLimit < 0)
                    monthlyLimit.setTextColor(resources.getColor(R.color.colorRed))
                if (dLimit < 0)
                    dailyLimit.setTextColor(resources.getColor(R.color.colorRed))
                per = (dLimit.toFloat() / dLimitBase * 100).toInt()
                Log.d(this.toString(),  "dLimit: $dLimit || dLimitBase: $dLimitBase || $spending // " + per.toString())
                if (per in 0..20)
                    (activity as MainActivity).sendOnChannel(0)
                else if (per < 0)
                    (activity as MainActivity).sendOnChannel(1)
                if (moneyLottieView.visibility == View.GONE) {
                    checkmarkView.alpha = 1f
                    paymentCompleted.alpha = 1f
                }
                checkmarkView.playAnimation()
                manageGoodDays(spending)

            }
        }
    }

    private fun manageGoodDays(spending: Int) {
        if (per > 0) {
            goodDaysCounter += 1
        } else {
            goodDaysCounter = 0
        }
//        if (goodDaysCounter < 0) {
//            tv_days_in_a_row.setTextColor(resources.getColor(R.color.colorRed))
//        } else {
//            tv_days_in_a_row.setTextColor(resources.getColor(R.color.colorHoloGreen))
//        }
        tv_days_in_a_row.text = goodDaysCounter.toString()
    }
}