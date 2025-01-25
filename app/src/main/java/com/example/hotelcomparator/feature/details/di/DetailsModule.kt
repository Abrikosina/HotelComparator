package com.example.hotelcomparator.feature.details.di

import com.example.hotelcomparator.feature.details.data.DetailsApi
import com.example.hotelcomparator.feature.details.data.DetailsRepositoryImpl
import com.example.hotelcomparator.feature.details.data.PricesApi
import com.example.hotelcomparator.feature.details.data.PricesRepositoryImpl
import com.example.hotelcomparator.feature.details.domain.DetailsRepository
import com.example.hotelcomparator.feature.details.domain.PricesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DetailsModule {
	@Binds
	fun getDetailsRepository(photoRepository: DetailsRepositoryImpl): DetailsRepository

	@Binds
	fun getPricesRepository(repository: PricesRepositoryImpl): PricesRepository

	companion object {

		@Provides
		@Singleton
		fun provideDetailsApi(retrofit: Retrofit): DetailsApi {
			return retrofit.create(DetailsApi::class.java)
		}

		@Provides
		@Singleton
		fun providePricesApi(retrofit: Retrofit): PricesApi {
			return retrofit.create(PricesApi::class.java)
		}
	}
}