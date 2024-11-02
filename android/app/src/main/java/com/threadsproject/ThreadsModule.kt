package com.threadsproject

import android.content.Intent
import android.net.Uri
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.facebook.react.bridge.BaseActivityEventListener
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.threadsproject.MainActivity.Companion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class ThreadsModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext),
    LifecycleEventListener {

    companion object {
        const val TAG = "[THREADS-MODULE]"
    }

    private var shareThreadsPromise : Promise? = null

    private val activityEventListener = object: BaseActivityEventListener() {
        override fun onNewIntent(intent: Intent?) {
            super.onNewIntent(intent)
            val scheme = intent?.data?.scheme ?: ""
            val code = intent?.data?.getQueryParameter("code") ?: ""

            Log.d(TAG, "onNewIntent: scheme: $scheme, code: $code, intent: ${intent}")
            Log.d(TAG, "onNewIntent: userInput: ${userInput}")

            val isMainThread = Looper.myLooper() == Looper.getMainLooper()
            Log.d(TAG, "onNewIntent: isMainThread: ${isMainThread}")
//            shareThreadsScope.cancel()

            if (scheme == "shareinsta") {

                shareThreadsScope.launch {
                    try {
                       val finalResponse = withContext(context = Dispatchers.IO) {
                            Log.d(TAG, "shareThreads: code : ${code}")
                            val response = RetrofitClient.getAccessToken(code)
                            val userId = BigDecimal("${response["user_id"]}")
                            val accessToken: String = (response["access_token"] ?: "").toString()
                            Log.d(TAG, "shareThreads: response : ${response}")
                            val longTermTokenResponse = RetrofitClient.getLongTermToken(shortAccessToken = accessToken)

                            val longTermToken : String = (longTermTokenResponse["access_token"] ?: "").toString()
                            val expiresIn : String = (longTermTokenResponse["expires_in"] ?: "").toString()
                            Log.d(TAG, "onCreate: longTermToken : $longTermToken, expiresIn : $expiresIn")

                            Log.d(TAG, "onCreate: userId : ${userId.toString()}")

                            //TODO: upload thread data
                            val mediaIdResponse = RetrofitClient.uploadThreadPost(userId = userId.toString(),
                                text = userInput, longTermToken)

                            Log.d(TAG, "onCreate: mediaIdResponse : $mediaIdResponse")

                            val mediaId = "${mediaIdResponse["id"]}"

                            val doneMediaId = RetrofitClient.makeThreadPublic(userId = "$userId",
                                mediaId, longTermToken)["id"]
                            Log.d(TAG, "onCreate: doneMediaId : $doneMediaId")
                            return@withContext doneMediaId
                        }
                        shareThreadsPromise?.resolve(finalResponse)
                    } catch (e: Exception) {
                        Log.d(TAG, "shareThreads: error : ${e.message}")
                        shareThreadsPromise?.reject("share Threads error", e)
                    }
                }
            }
        }
    }

    init {
        reactContext.addActivityEventListener(activityEventListener)
        reactContext.addLifecycleEventListener(this)
    }

    // in js
    // import { NativeModules } from 'react-native'
    // const { ThreadsModule } = NativeModules
    override fun getName(): String {
        return "ThreadsModule"
    }

    var userInput : String = ""

    // js -> android trigger
    @ReactMethod fun openShareThreadsWeb(_userInput: String, promise: Promise){

        shareThreadsPromise = null

        this.userInput = _userInput

        // 1. if longTerm

        val url = "https://threads.net/oauth/authorize?client_id=521096737301031&redirect_uri=https%3A%2F%2Fhidden-shadow-6351.enfn2001.workers.dev%2Fauth%2F&scope=threads_basic%2Cthreads_content_publish&response_type=code"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        currentActivity?.startActivity(intent)

        shareThreadsPromise = promise

    }

    val shareThreadsScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    suspend fun doSomethingHeavy(value: String): String {
        delay(1000)
        return "Hello from doSomethingHeavy value: $value"
    }

//    @ReactMethod(isBlockingSynchronousMethod = true)
//    fun shareThreads(shareText: String, code: String, promise: Promise) {
//        // share thread
//        // native work do
//        Log.d(TAG, "shareThreads: shareText : $shareText , code: $code")
//
//        RetrofitClient.getAccessToken(code, completion = {
//            Log.d(TAG, "shareThreads: response : ${it}")
//            promise.resolve(it)
//        })
//    }

    override fun onHostDestroy() {
        Log.d(TAG, "onHostDestroy: ")
    }

    override fun onHostPause() {
        Log.d(TAG, "onHostPause: ")
    }

    override fun onHostResume() {
        Log.d(TAG, "onHostResume: ")
    }


}