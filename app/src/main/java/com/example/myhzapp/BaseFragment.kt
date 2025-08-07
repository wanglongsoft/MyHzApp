package com.example.myhzapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.RenderMode
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

open class BaseFragment : AppCompatActivity(), EngineBindingsDelegate {
    private var flutterFragment: FlutterFragment? = null

    private val engineBindings: EngineBindings by lazy {
        EngineBindings(activity = this, delegate = this, entrypoint = getEntrypoint())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        engineBindings.attach()
        super.onCreate(savedInstanceState)
        FlutterEngineCache.getInstance().put(getEngineId(), engineBindings.engine)
        flutterFragment = supportFragmentManager
            .findFragmentByTag(getEngineId()) as FlutterFragment?

        if (flutterFragment == null) {
            flutterFragment = FlutterFragment.withCachedEngine(getEngineId()).renderMode(RenderMode.texture).build()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fl_fragment_id, flutterFragment!!, getEngineId())
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        engineBindings.detach()
    }

    override fun onMethodCall(
        call: MethodCall,
        result: MethodChannel.Result
    ) {
    }

    override fun onMessageCall(
        message: String?,
        reply: BasicMessageChannel.Reply<String>
    ) {
    }

    fun callFlutterMethod(
        method: String,
        arguments: Any,
        callback: MethodChannel.Result,
    ) {
        engineBindings.channel.invokeMethod(method, arguments, callback)
    }
    fun callFlutterMethod(
        method: String,
        arguments: Any
    ) {
        engineBindings.channel.invokeMethod(method, arguments)
    }

    fun callFlutterMessage(
        message: String,
    ) {
        engineBindings.message.send(message)
    }

    fun callFlutterMessage(
        message: String,
        reply: BasicMessageChannel.Reply<String>
    ) {
        engineBindings.message.send(message, reply)
    }

    override fun finish() {
        super.finish()
    }

    open fun getEntrypoint(): String {
        return "mainFragment"
    }

    open fun getEngineId(): String {
        return "base_fragment"
    }
}