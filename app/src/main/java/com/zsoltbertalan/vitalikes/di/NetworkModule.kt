package com.zsoltbertalan.vitalikes.di

import com.zsoltbertalan.vitalikes.BASE_URL_ETHER_EXPLORER
import com.zsoltbertalan.vitalikes.BASE_URL_ETHER_SCAN
import com.zsoltbertalan.vitalikes.data.network.EtherExplorerApi
import com.zsoltbertalan.vitalikes.data.network.EtherScanApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
class NetworkModule {

	@Provides
	@Singleton
	@Named("EtherExplorerRetrofit")
	internal fun provideEtherExplorerRetrofit(): Retrofit {
		val logging = HttpLoggingInterceptor()
		logging.level = HttpLoggingInterceptor.Level.BODY

		val httpClient = OkHttpClient.Builder()

		httpClient.addInterceptor(logging)
		return Retrofit.Builder()
			.baseUrl(BASE_URL_ETHER_EXPLORER)
			.addConverterFactory(GsonConverterFactory.create())
			.client(httpClient.build())
			.build()
	}

	@Provides
	@Singleton
	internal fun provideEtherExplorerApi(@Named("EtherExplorerRetrofit") retroFit: Retrofit): EtherExplorerApi {
		return retroFit.create(EtherExplorerApi::class.java)
	}

	@Provides
	@Singleton
	@Named("EtherScanRetrofit")
	internal fun provideEtherScanRetrofit(): Retrofit {
		val logging = HttpLoggingInterceptor()
		logging.level = HttpLoggingInterceptor.Level.BODY

		val httpClient = OkHttpClient.Builder()

		httpClient.addInterceptor(logging)
		return Retrofit.Builder()
			.baseUrl(BASE_URL_ETHER_SCAN)
			.addConverterFactory(GsonConverterFactory.create())
			.client(httpClient.build())
			.build()
	}

	@Provides
	@Singleton
	internal fun provideEtherScanApi(@Named("EtherScanRetrofit") retroFit: Retrofit): EtherScanApi {
		return retroFit.create(EtherScanApi::class.java)
	}

}
