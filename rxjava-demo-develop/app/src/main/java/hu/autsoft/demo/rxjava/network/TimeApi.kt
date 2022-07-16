package hu.autsoft.demo.rxjava.network

import okhttp3.OkHttpClient
import okhttp3.Request

object TimeApi {

    private const val URL = "http://date.jsontest.com/"

    fun fetchCurrentTime(): String {
        return try {
            val request = Request.Builder()
                    .url(URL)
                    .build()

            val client = OkHttpClient()

            val response = client.newCall(request).execute()
            response.body!!.string()
        } catch (t: Throwable) {
            "error"
        }
    }
}
