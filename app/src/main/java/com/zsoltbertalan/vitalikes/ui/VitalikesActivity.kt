package com.zsoltbertalan.vitalikes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.DefaultComponentContext
import com.zsoltbertalan.vitalikes.domain.api.VitalikesRepository
import com.zsoltbertalan.vitalikes.di.IoDispatcher
import com.zsoltbertalan.vitalikes.di.MainDispatcher
import com.zsoltbertalan.vitalikes.root.VitalikesRootComponent
import com.zsoltbertalan.vitalikes.root.VitalikesRootContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@AndroidEntryPoint
class VitalikesActivity : ComponentActivity() {

    @Inject
    lateinit var vitalikesRepository: VitalikesRepository

    @Inject
    @MainDispatcher
    lateinit var mainContext: CoroutineDispatcher

    @Inject
    @IoDispatcher
    lateinit var ioContext: CoroutineDispatcher

    private lateinit var vitalikesRootComponent: VitalikesRootComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vitalikesRootComponent = VitalikesRootComponent(
            DefaultComponentContext(lifecycle, savedStateRegistry, viewModelStore, null),
            mainContext,
            ioContext,
            vitalikesRepository
        ) { finish() }

        setContent {
            VitalikesRootContent(vitalikesRootComponent)
        }
    }

}
