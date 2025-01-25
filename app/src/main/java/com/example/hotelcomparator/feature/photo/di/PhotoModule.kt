package com.example.hotelcomparator.feature.photo.di

import com.example.hotelcomparator.feature.photo.data.PhotoRepositoryImpl
import com.example.hotelcomparator.feature.photo.domain.PhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PhotoModule {
	@Binds
	fun getPhotoRepository(photoRepository: PhotoRepositoryImpl): PhotoRepository
}