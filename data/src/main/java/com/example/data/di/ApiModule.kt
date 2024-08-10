package com.example.data.di

import com.example.data.WebServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideHttpLoggingInterceptor():HttpLoggingInterceptor{

        return HttpLoggingInterceptor{

        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor):OkHttpClient{

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun  provideGsonConverter():GsonConverterFactory{

        return GsonConverterFactory.create()
    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory):Retrofit{

        return Retrofit.Builder()
            .baseUrl("https://api.quran.com/api/v4/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun provideWebServices(retrofit: Retrofit): WebServices {

        return retrofit.create(WebServices::class.java)
    }
}