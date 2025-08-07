package com.example.myhzapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel


class FlutterViewActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.d("FlutterActivity", "onCreate")
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        super.onMethodCall(call, result)
        Log.d("FlutterActivity", "onMethodCall: ${call.method}")
        val flutterClass = FlutterViewActivity::class.java
        val flutterIntent = Intent(this, flutterClass)
        startActivity(flutterIntent)
        result.success(null)
    }

    override fun onMessageCall(
        message: String?,
        reply: BasicMessageChannel.Reply<String>
    ) {
        Log.d("FlutterActivity", "onMessageCall: $message")
        reply.reply("Native reply success")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FlutterActivity", "onDestroy")
    }
}