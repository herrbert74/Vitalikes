package com.zsoltbertalan.vitalikes.di

import com.zsoltbertalan.vitalikes.data.db.TokenDao
import com.zsoltbertalan.vitalikes.data.db.TokenDataSource
import com.zsoltbertalan.vitalikes.data.db.TokenDbo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
class DataModule {

	@Provides
	@Singleton
	fun provideRealmConfiguration() = RealmConfiguration.Builder(
		schema = setOf(TokenDbo::class)
	).build()

	@Provides
	@Singleton
	fun provideRealm(realmConfiguration: RealmConfiguration) = Realm.open(realmConfiguration)

	@Provides
	@Singleton
	internal fun provideTokenDataSource(realm: Realm): TokenDataSource {
		return TokenDao(realm)
	}

}
