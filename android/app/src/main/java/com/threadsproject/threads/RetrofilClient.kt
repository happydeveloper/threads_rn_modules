package com.threadsproject

import android.util.Log
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


// 싱글턴
object RetrofitClient {
    // 레트로핏 클라이언트 선언

    const val TAG = "RetrofitClient"

    private var retrofit: Retrofit? = null
//    private lateinit var retrofitClient: Retrofit


    // 레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG, "RetrofitClient - getClient() called")

        // okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()

        // 로그를 찍기 위해 로깅 인터셉터 설정
        val loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger{

            override fun log(message: String) {
//                Log.d(TAG, "RetrofitClient - log() called / message: $message")

                when {
                    message.isJsonObject() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    message.isJsonArray() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    else -> {
                        try {
                            Log.d(TAG, JSONObject(message).toString(4))
                        } catch (e: Exception) {
                            Log.d(TAG, message)
                        }
                    }
                }

            }

        })

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        // 위에서 설정한 로깅 인터셉터를 okhttp 클라이언트에 추가한다.
        client.addInterceptor(loggingInterceptor)


//        // 기본 파라매터 인터셉터 설정
//        val baseParameterInterceptor : Interceptor = (object : Interceptor{
//
//            override fun intercept(chain: Interceptor.Chain): Response {
//                Log.d(TAG, "RetrofitClient - intercept() called")
//                // 오리지날 리퀘스트
//                val originalRequest = chain.request()
//
//                // ?client_id=asdfadsf
//                // 쿼리 파라매터 추가하기
//                val addedUrl = originalRequest.url.newBuilder().addQueryParameter("client_id", API.CLIENT_ID).build()
//
//                val finalRequest = originalRequest.newBuilder()
//                    .url(addedUrl)
//                    .method(originalRequest.method, originalRequest.body)
//                    .build()
//
//                return chain.proceed(finalRequest)
//            }
//
//        })


        // 위에서 설정한 기본파라매터 인터셉터를 okhttp 클라이언트에 추가한다.
//        client.addInterceptor(baseParameterInterceptor)

        // 커넥션 타임아웃
        client.connectTimeout(10, TimeUnit.SECONDS)
        client.readTimeout(10, TimeUnit.SECONDS)
        client.writeTimeout(10, TimeUnit.SECONDS)
        client.retryOnConnectionFailure(true)

        //retrofit_

        if(retrofit == null){

            // 레트로핏 빌더를 통해 인스턴스 생성
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())

                // 위에서 설정한 클라이언트로 레트로핏 클라이언트를 설정한다.
                .client(client.build())
                .build()
        }

        return retrofit
    }


    suspend fun getAccessToken(code: String) : Map<String, Any> {

        Log.d(TAG, "getAccessToken: code: ${code}")

        val urlString = "https://graph.threads.net/oauth/access_token"

        val codeBody = "${code}".toRequestBody("text/plain".toMediaTypeOrNull())

        Log.d(TAG, "getAccessToken: codeBody: ${codeBody}")

        val clientIdBody = "521096737301031".toRequestBody("text/plain".toMediaTypeOrNull())
        val clientSecretBody = "69b605f9d43c13e773f39deb4f358f7d".toRequestBody("text/plain".toMediaTypeOrNull())
        val grantTypeBody = "authorization_code".toRequestBody("text/plain".toMediaTypeOrNull())
        val redirectUriBody = "https://hidden-shadow-6351.enfn2001.workers.dev/auth/".toRequestBody("text/plain".toMediaTypeOrNull())

        return  RetrofitClient.getClient("https://graph.threads.net/")?.create(ThreadsInterface::class.java)
            ?.getAccessToken(clientIdBody, clientSecretBody, grantTypeBody, redirectUriBody, codeBody) ?: emptyMap()
    }

    suspend fun getLongTermToken(shortAccessToken: String) : Map<String, Any>  {

        return RetrofitClient.getClient("https://graph.threads.net/")?.create(ThreadsInterface::class.java)
            ?.getLongTermToken(accessToken = shortAccessToken) ?: emptyMap()
    }

    suspend fun uploadThreadPost(userId: String,
                         text: String,
                         accessToken: String) : Map<String, Any>  {
        return RetrofitClient.getClient("https://graph.threads.net/")?.create(ThreadsInterface::class.java)
            ?.uploadThreadPost(userId = userId, text = text, accessToken = accessToken) ?: emptyMap()
    }

    suspend fun makeThreadPublic(userId: String,
                         mediaId: String,
                         accessToken: String) : Map<String, Any>  {
        return RetrofitClient.getClient("https://graph.threads.net/")?.create(ThreadsInterface::class.java)
            ?.makeThreadPublic(userId, mediaId = mediaId, accessToken = accessToken) ?: emptyMap()
    }

//
//    fun getAccessToken(code: String,
//                       completion: (Map<String, Any>?) -> Unit) {
//
//        Log.d(TAG, "getAccessToken: code: ${code}")
//
//        val urlString = "https://graph.threads.net/oauth/access_token"
//
//        val codeBody = "${code}".toRequestBody("text/plain".toMediaTypeOrNull())
//
//        Log.d(TAG, "getAccessToken: codeBody: ${codeBody}")
//
//        val clientIdBody = "521096737301031".toRequestBody("text/plain".toMediaTypeOrNull())
//        val clientSecretBody = "69b605f9d43c13e773f39deb4f358f7d".toRequestBody("text/plain".toMediaTypeOrNull())
//        val grantTypeBody = "authorization_code".toRequestBody("text/plain".toMediaTypeOrNull())
//        val redirectUriBody = "https://hidden-shadow-6351.enfn2001.workers.dev/auth/".toRequestBody("text/plain".toMediaTypeOrNull())
//
//        val call = RetrofitClient.getClient("https://graph.threads.net/")?.create(ThreadsInterface::class.java)
//            ?.getAccessToken(clientIdBody, clientSecretBody, grantTypeBody, redirectUriBody, codeBody)
//
//        call?.enqueue(object: Callback<Map<String, Any>?> {
//            override fun onResponse(p0: Call<Map<String, Any>?>, p1: Response<Map<String, Any>?>) {
//                if (p1.isSuccessful){
//                    completion(p1.body())
//                } else {
//                    completion(null)
//                }
//            }
//
//            override fun onFailure(p0: Call<Map<String, Any>?>, p1: Throwable) {
//
//                completion(null)
//            }
//
//        })
//
//    }
//
//    fun getLongTermToken(shortAccessToken: String, completion: (Map<String, Any>?) -> Unit)  {
//
//        val call = RetrofitClient.getClient("https://graph.threads.net/")?.create(ThreadsInterface::class.java)
//            ?.getLongTermToken(accessToken = shortAccessToken)
//        call?.enqueue(object: Callback<Map<String, Any>?> {
//            override fun onResponse(p0: Call<Map<String, Any>?>, p1: Response<Map<String, Any>?>) {
//                if (p1.isSuccessful){
//                    completion(p1.body())
//                } else {
//                    completion(null)
//                }
//            }
//
//            override fun onFailure(p0: Call<Map<String, Any>?>, p1: Throwable) {
//                completion(null)
//            }
//        })
//    }
//
//    fun uploadThreadPost(userId: String,
//                         text: String,
//                         accessToken: String,
//                         completion: (Map<String, Any>?) -> Unit)  {
//        val call = RetrofitClient.getClient("https://graph.threads.net/")?.create(ThreadsInterface::class.java)
//            ?.uploadThreadPost(userId = userId, text = text, accessToken = accessToken)
//        call?.enqueue(object: Callback<Map<String, Any>?> {
//            override fun onResponse(p0: Call<Map<String, Any>?>, p1: Response<Map<String, Any>?>) {
//                if (p1.isSuccessful){
//                    completion(p1.body())
//                } else {
//                    completion(null)
//                }
//            }
//
//            override fun onFailure(p0: Call<Map<String, Any>?>, p1: Throwable) {
//                completion(null)
//            }
//        })
//    }
//
//    fun makeThreadPublic(userId: String,
//                         mediaId: String,
//                         accessToken: String,
//                         completion: (Map<String, Any>?) -> Unit)  {
//        val call = RetrofitClient.getClient("https://graph.threads.net/")?.create(ThreadsInterface::class.java)
//            ?.makeThreadPublic(userId, mediaId = mediaId, accessToken = accessToken)
//        call?.enqueue(object: Callback<Map<String, Any>?> {
//            override fun onResponse(p0: Call<Map<String, Any>?>, p1: Response<Map<String, Any>?>) {
//                if (p1.isSuccessful){
//                    completion(p1.body())
//                } else {
//                    completion(null)
//                }
//            }
//
//            override fun onFailure(p0: Call<Map<String, Any>?>, p1: Throwable) {
//                completion(null)
//            }
//        })
//    }

}


// 문자열이 제이슨 형태인지
fun String?.isJsonObject():Boolean {
    if(this?.startsWith("{") == true && this.endsWith("}")){
        return true
    } else {
        return false
    }
//    return this?.startsWith("{") == true && this.endsWith("}")
}
//fun String?.isJsonObject():Boolean = this?.startsWith("{") == true && this.endsWith("}")

// 문자열이 제이슨 배열인지
fun String?.isJsonArray() : Boolean {
    if(this?.startsWith("[") == true && this.endsWith("]")){
        return true
    } else {
        return false
    }
}

