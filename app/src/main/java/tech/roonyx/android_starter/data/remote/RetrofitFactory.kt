package tech.roonyx.android_starter.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.roonyx.android_starter.BuildConfig
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    inline fun <reified T> create(
        baseUrl: String
    ): T = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(getOkHttpClient(createLoggingHttpInterceptor()))
        .addConverterFactory(GsonConverterFactory.create(getGson()))
        .build()
        .create(T::class.java)

    fun getGson(): Gson = GsonBuilder().create()

    fun getOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        vararg interceptors: Interceptor
    ) = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) addNetworkInterceptor(loggingInterceptor)
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        interceptors.forEach { addInterceptor(it) }
    }.build()

    fun createLoggingHttpInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private const val CONNECT_TIMEOUT: Long = 120
    private const val WRITE_TIMEOUT: Long = 300
    private const val READ_TIMEOUT: Long = 120
}