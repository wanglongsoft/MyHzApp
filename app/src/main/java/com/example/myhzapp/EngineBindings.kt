package com.example.myhzapp

import android.app.Activity
import io.flutter.FlutterInjector
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StringCodec

/**
 * This interface represents the notifications an EngineBindings may be receiving from the Flutter
 * instance.
 *
 * What methods this interface has depends on the messages that are sent over the EngineBinding's
 * channel in `main.dart`.  Messages that interact with the DataModel are handled automatically
 * by the EngineBindings.
 *
 * @see main.dart for what messages are getting sent from Flutter.
 */
interface EngineBindingsDelegate {
    fun onMethodCall(call:MethodCall,  result: MethodChannel.Result)
    fun onMessageCall(message: String?, reply: BasicMessageChannel.Reply<String>)
}

/**
 * This binds a FlutterEngine instance with the DataModel and a channel for communicating with that
 * engine.
 *
 * Messages involving the DataModel are handled by the EngineBindings, other messages are forwarded
 * to the EngineBindingsDelegate.
 *
 * @see main.dart for what messages are getting sent from Flutter.
 */
class EngineBindings(activity: Activity, delegate: EngineBindingsDelegate, entrypoint: String) {
    val channel: MethodChannel
    val message: BasicMessageChannel<String>
    val engine: FlutterEngine
    val delegate: EngineBindingsDelegate

    init {
        val app = activity.applicationContext as App
        // This has to be lazy to avoid creation before the FlutterEngineGroup.
        val dartEntrypoint =
            DartExecutor.DartEntrypoint(
                FlutterInjector.instance().flutterLoader().findAppBundlePath(), entrypoint
            )
        engine = app.engines.createAndRunEngine(activity, dartEntrypoint)
        this.delegate = delegate
        channel = MethodChannel(engine.dartExecutor.binaryMessenger, "com.example.flutter_module/methodChannel")
        message = BasicMessageChannel(engine.dartExecutor.binaryMessenger, "com.example.flutter_module/basicMessageChannel",
            StringCodec.INSTANCE)
    }

    /**
     * This setups the messaging connections on the platform channel and the DataModel.
     */
    fun attach() {
        channel.setMethodCallHandler { call, result ->
            this.delegate.onMethodCall(call, result)
        }
        message.setMessageHandler { message, reply ->
            this.delegate.onMessageCall(message, reply)
        }
    }

    /**
     * This tears down the messaging connections on the platform channel and the DataModel.
     */
    fun detach() {
        engine.destroy();
        channel.setMethodCallHandler(null)
        message.setMessageHandler(null)
    }
}
