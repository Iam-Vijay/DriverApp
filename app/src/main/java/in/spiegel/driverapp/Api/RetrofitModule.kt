package `in`.spiegel.driverapp.Api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Vijay on 12/2/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
 class RetrofitModule {
    @Provides
    @Singleton
    fun ProvideokhttpClient():OkHttpClient{
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun Getohhttpclientinterptor(httpLoggingInterceptor: HttpLoggingInterceptor):OkHttpClient{
            return httpLoggingInterceptor.let {
                OkHttpClient.Builder().addInterceptor(it).build()
            }
    }
    @Provides
    @Singleton
    fun provideGson(): GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun getLoggingInterptor():HttpLoggingInterceptor{
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory,okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("url")
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun ProvideApiInterdace(retrofit: Retrofit):ApiInterface{
        return  retrofit.create(ApiInterface::class.java)
    }

}