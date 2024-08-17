package com.goalpanzi.mission_mate.core.network.di

import android.util.Log
import com.goalpanzi.mission_mate.core.datastore.datasource.AuthDataSource
import com.goalpanzi.mission_mate.core.network.BuildConfig
import com.goalpanzi.mission_mate.core.network.service.LoginService
import com.goalpanzi.mission_mate.core.network.service.MissionService
import com.goalpanzi.mission_mate.core.network.service.OnboardingService
import com.goalpanzi.mission_mate.core.network.service.ProfileService
import com.goalpanzi.mission_mate.core.network.service.TokenService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileService(retrofit: Retrofit): ProfileService {
        return retrofit.create(ProfileService::class.java)
    }

    @Provides
    @Singleton
    fun provideOnboardingService(retrofit: Retrofit): OnboardingService {
        return retrofit.create(OnboardingService::class.java)
    }

    @Provides
    @Singleton
    fun provideMissionService(retrofit: Retrofit): MissionService {
        return retrofit.create(MissionService::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenService(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        converterFactory: Converter.Factory,
        authDataSource: AuthDataSource
    ): TokenService {
        val tokenReissueInterceptor = Interceptor { chain ->
            val newRequest = chain.request().newBuilder().apply {
                runBlocking {
                    val token = authDataSource.getAccessToken().first()
                    token?.let {
                        addHeader("Authorization", "Bearer $it")
                    }
                }
            }
            chain.proceed(newRequest.build())
        }
        val retrofit = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(tokenReissueInterceptor)
                    .addInterceptor(httpLoggingInterceptor)
                    .build()
            )
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
        return retrofit.create(TokenService::class.java)
    }
}