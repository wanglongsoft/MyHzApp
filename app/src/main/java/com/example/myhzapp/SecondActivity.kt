package com.example.myhzapp

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ScrollView
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel


class SecondActivity : BaseFragment() {
    private var rootView: ScrollView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        transparentStatusBar(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        rootView = findViewById(R.id.rootView)
    }

    private fun transparentStatusBar(activity: Activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //需要设置这个flag contentView才能延伸到状态栏
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        //状态栏覆盖在contentView上面，设置透明使contentView的背景透出来
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT)
    }

    fun setStatusbarWhiteOrBlack(activity: Activity, isBlack: Boolean) {
        if (isBlack) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            )
        }
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        Log.d("SecondActivity", "onMethodCall: ${call.method} arguments: ${call.arguments}")
        if(call.method == "disallowIntercept") {
            if(call.arguments == true) {
                Log.d("SecondActivity", "rootView != null : ${rootView != null}")
                rootView?.requestDisallowInterceptTouchEvent(true)
            } else {
                rootView?.requestDisallowInterceptTouchEvent(false)
            }
        }
        result.success(true)
    }
}