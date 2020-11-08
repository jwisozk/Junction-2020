package com.example.sovest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {

    val CHANNEL_ID = "channel"
    lateinit var notificationManager: NotificationManagerCompat

    val phrases = listOf(
        "I have to tell you that you've already spent more than 80% of your daily limit. Be careful and try not to exceed the limit.",
        "You had better not to exceed the limit, you know that. The daily limit is now lower than it was :-/",
        "I've noticed you haven't exceeded the daily limit today. By my calculations you've managed to save about X €. Thanks to that you daily limit has increased.",
        "It seems you have manage to save a little bit today, which is really good. If you keep daily spendings at that level till the end of month you will get about X € in savings by the end of it.",
        "That's the first day you haven't exceeded the daily limit. That's not bad but what about keeping it going the whole week. I bet you cannot do that! :)",
        "Well done! That's the second day in a row! Remember—the less you overspend today the larger the daily limit you have tomorrow."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        notificationManager = NotificationManagerCompat.from(this)
    }

    fun sendOnChannel(index: Int) {
//        val title: String = editTextTitle.getText().toString()
//        val message: String = editTextMessage.getText().toString()
        val notification: Notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_account_balance_wallet_24px)
//                .setContentTitle(title)
//                .setContentText("Description")
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText(phrases[index]))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build()
        notificationManager.notify(1, notification)
        addBadge("")
    }

    private fun addBadge(count : String) {
        val badge: BadgeDrawable = nav_view.getOrCreateBadge(
            R.id.navigation_notifications)
        badge.number += 1
        badge.isVisible = true
    }

}