package de.bytepark.autoorientation

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** AutoOrientationPlugin  */
class AutoOrientationPlugin private constructor(internal var activity: Activity?=null) : MethodCallHandler {

    @Override
    fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "setLandscapeRight" -> this.activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            "setLandscapeLeft" -> this.activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE)
            "setPortraitUp" -> this.activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            "setPortraitDown" -> this.activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT)
            "setPortraitAuto" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                this.activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT)
            } else {
                this.activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT)
            }
            "setLandscapeAuto" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                this.activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE)
            } else {
                this.activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)
            }
            "setAuto" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                this.activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER)
            } else {
                this.activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR)
            }
            else -> result.notImplemented()
        }
    }

    companion object {

        /** Plugin registration.  */
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "auto_orientation")
            channel.setMethodCallHandler(AutoOrientationPlugin(registrar.activity()))
        }
    }
}
