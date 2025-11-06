package dev.group6.vrappcontroller.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Model for handling the slider values.
 * Use the _<variableName> MutableStateFlow to update the variable value.
 * Outside the model use the given methods.
 * To watch for changes use val <variable> by viewModel.<variable>.collectAsState()
 */
class MainModel() : ViewModel() {
    var _thunderVolume = MutableStateFlow(1.0f)
    val thunderVolume: MutableStateFlow<Float> = _thunderVolume
    var _lightningBrightness = MutableStateFlow(1.0f)
    val lightningBrightness: MutableStateFlow<Float> = _lightningBrightness
    var _lightningDistance = MutableStateFlow(1.0f)
    val lightningDistance: MutableStateFlow<Float> = _lightningDistance

    var _rain = MutableStateFlow(0)
    val rain: MutableStateFlow<Int> = _rain
    var _wind = MutableStateFlow(0)
    val wind: MutableStateFlow<Int> = _wind
    var _clouds = MutableStateFlow(0)
    val clouds: MutableStateFlow<Int> = _clouds

    var _minThunderInterval = MutableStateFlow(10)
    val minThunderInterval: MutableStateFlow<Int> = _minThunderInterval
    var _maxThunderInterval = MutableStateFlow(100)
    val maxThunderInterval: MutableStateFlow<Int> = _maxThunderInterval
    var _minLightningInterval = MutableStateFlow(10)
    val minLightningInterval: MutableStateFlow<Int> = _minLightningInterval
    var _maxLightningInterval = MutableStateFlow(100)
    val maxLightningInterval: MutableStateFlow<Int> = _maxLightningInterval

    init {
        _thunderVolume.value = 1.0f
        _lightningBrightness.value = 1.0f
        _lightningDistance.value = 1.0f
    }

    fun setThunderVolume(value: Float) {
        _thunderVolume.value = value
    }
    fun setLightningBrightness(value: Float) {
        _lightningBrightness.value = value
    }
    fun setLightningDistance(value: Float) {
        _lightningDistance.value = value
    }
    fun setRain(value: Int) {
        _rain.value = value
    }
    fun setWind(value: Int) {
        _wind.value = value
    }
    fun setClouds(value: Int) {
        _clouds.value = value
    }
    fun setMinThunderInterval(value: Int) {
        _minThunderInterval.value = value
    }
    fun setMaxThunderInterval(value: Int) {
        _maxThunderInterval.value = value
    }
    fun setMinLightningInterval(value: Int) {
        _minLightningInterval.value = value
    }
    fun setMaxLightningInterval(value: Int) {
        _maxLightningInterval.value = value
    }

}