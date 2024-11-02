package com.threadsproject

import android.os.Bundle
import android.util.Log
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate

class MainActivity : ReactActivity() {

    companion object {
//        const val TAG = "[MAIN-ACTIVITY]"
        const val TAG = "RetrofitClient"
    }
  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  override fun getMainComponentName(): String = "ThreadsProject"

  /**
   * Returns the instance of the [ReactActivityDelegate]. We use [DefaultReactActivityDelegate]
   * which allows you to enable New Architecture with a single boolean flags [fabricEnabled]
   */
  override fun createReactActivityDelegate(): ReactActivityDelegate =
      DefaultReactActivityDelegate(this, mainComponentName, fabricEnabled)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: intent.data: ${intent.data}")

        // THREADS
        // get URI param
        val scheme = intent.data?.scheme ?: ""
        val code = intent.data?.getQueryParameter("code") ?: ""

        Log.d(TAG, "onCreate: scheme: $scheme code: $code")
    }

    override fun onResume() {
        super.onResume()
        // THREADS
        // get URI param
        val scheme = intent.data?.scheme ?: ""
        val code = intent.data?.getQueryParameter("code") ?: ""
        Log.d(TAG, "onResume: intent.data: ${intent.data}")
        Log.d(TAG, "onResume: scheme: $scheme code: $code")
    }
}
