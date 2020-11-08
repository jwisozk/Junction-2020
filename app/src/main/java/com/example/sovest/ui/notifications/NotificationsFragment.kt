package com.example.sovest.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sovest.MainActivity
import com.example.sovest.R
import kotlinx.android.synthetic.main.fragment_notifications.view.*

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

//    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as MainActivity
        view.recyclerView.adapter = NotificationAdapter((activity as MainActivity).phrases, layoutInflater)
        view.recyclerView.layoutManager = LinearLayoutManager(activity)
    }
}