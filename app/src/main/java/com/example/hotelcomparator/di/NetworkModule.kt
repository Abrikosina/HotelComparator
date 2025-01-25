package com.example.hotelcomparator.di

import android.util.Log
import com.example.hotelcomparator.core.network.NetworkResultCallAdapterFactory
import com.example.hotelcomparator.feature.photo.data.PhotoApi
import com.example.hotelcomparator.feature.search.data.SearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

	@Provides
	@Singleton
	fun getUnsafeOkHttpClient(): OkHttpClient {
		val interceptor = HttpLoggingInterceptor()
		interceptor.level = HttpLoggingInterceptor.Level.BODY
		val builder = OkHttpClient.Builder()
		return builder
			.addInterceptor(interceptor)
			.connectTimeout(30, TimeUnit.SECONDS)
			.readTimeout(30, TimeUnit.SECONDS)
			.followRedirects(true)
			.followSslRedirects(true)
			.addInterceptor { chain ->
				var newRequest = chain.request()
				val url = newRequest.url.newBuilder().addQueryParameter(API_KEY, KEY).build()
				newRequest = newRequest.newBuilder().url(url).build()
				chain.proceed(newRequest)
			}.build()
	}

	@Provides
	@Singleton
	fun provideRetrofit(client: OkHttpClient): Retrofit {
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
			.client(client)
			.build()
	}

	@Provides
	@Singleton
	fun provideApi(retrofit: Retrofit): SearchApi {
		return retrofit.create(SearchApi::class.java)
	}

	@Provides
	@Singleton
	fun providePhotoApi(retrofit: Retrofit): PhotoApi {
		return retrofit.create(PhotoApi::class.java)
	}

	companion object {
		private const val API_KEY = "key"
		private const val KEY = "CA92C5DF4C274FE883CB7D61609689D1"
		private const val BASE_URL = "https://api.content.tripadvisor.com/api/v1/location/"
	}
}