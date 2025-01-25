package com.example.hotelcomparator.feature.search.di

import com.example.hotelcomparator.feature.search.data.SearchRepositoryImpl
import com.example.hotelcomparator.feature.search.domain.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SearchModule {
	@Binds
	fun getSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository
}