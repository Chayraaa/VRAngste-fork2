package dev.group6.vrappcontroller.view

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.group6.vrappcontroller.model.MainModel
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.roundToInt

@Composable
fun MainView(
    viewModel: MainModel
) {

    val thunderVolume by viewModel.thunderVolume.collectAsState()
    val lightningBrightness by viewModel.lightningBrightness.collectAsState()
    val lightningDistance by viewModel.lightningDistance.collectAsState()

    val rain by viewModel.rain.collectAsState()
    val wind by viewModel.wind.collectAsState()
    val clouds by viewModel.clouds.collectAsState()

    val minThunderInterval by viewModel.minThunderInterval.collectAsState()
    val maxThunderInterval by viewModel.maxThunderInterval.collectAsState()
    val minLightningInterval by viewModel.minLightningInterval.collectAsState()
    val maxLightningInterval by viewModel.maxLightningInterval.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(32.dp)
            .windowInsetsPadding(WindowInsets.systemBars),
    ) {
        Category("Blitz/Donner") {
            SubCategory("Lautstärke Donner") {
                Slider(
                    value = thunderVolume,
                    onValueChange = viewModel::setThunderVolume,
                )
                Text("${(thunderVolume * 100).roundToInt()}%")
            }
            SubCategory("Helligkeit Blitz") {
                Slider(
                    value = lightningBrightness,
                    onValueChange = viewModel::setLightningBrightness,
                )
                Text("${(lightningBrightness * 100).roundToInt()}%")
            }
            SubCategory("Distanz Blitz") {
                Slider(
                    value = lightningDistance,
                    onValueChange = viewModel::setLightningDistance,
                )
                Text("${(lightningDistance * 100).roundToInt()}%")
            }

        }
        Category("Wetter") {
            SubCategory("Regen") {
                StrengthSlider(
                    value = rain,
                    onValueChange = viewModel::setRain,
                )
            }
            SubCategory("Wind") {
                StrengthSlider(
                    value = wind,
                    onValueChange = viewModel::setWind,
                )
            }
            SubCategory("Wolken") {
                StrengthSlider(
                    value = clouds,
                    onValueChange = viewModel::setClouds,
                )
            }
        }
        Category("Interval") {
            SubCategory("Intervall Donner") {
                RangeSlider(
                    value = minThunderInterval.toFloat()..maxThunderInterval.toFloat(),
                    onValueChange = { rangeSliderState ->
                        viewModel.setMinThunderInterval(rangeSliderState.start.roundToInt())
                        viewModel.setMaxThunderInterval(rangeSliderState.endInclusive.roundToInt())
                    },
                    valueRange = 0f..100f,
                )
                Text("From $minThunderInterval to $maxThunderInterval per minute")
            }
            SubCategory("Intervall Blitz") {
                RangeSlider(
                    value = minLightningInterval.toFloat()..maxLightningInterval.toFloat(),
                    onValueChange = { rangeSliderState ->
                        viewModel.setMinLightningInterval(rangeSliderState.start.roundToInt())
                        viewModel.setMaxLightningInterval(rangeSliderState.endInclusive.roundToInt())
                    },
                    valueRange = 0f..100f,
                )
                Text("From $minLightningInterval to $maxLightningInterval per minute")
            }
        }
    }
}

@Composable
fun Category(name: String, content: @Composable () -> Unit) {
    Column {
        Text(
            name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.outline,
            thickness = 1.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        content()

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun SubCategory(name: String, content: @Composable () -> Unit) {
    Column {
        Text(
            name,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left,
        )

        Spacer(modifier = Modifier.height(4.dp))
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            content()
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun StrengthSlider(
    value: Int,
    onValueChange: (Int) -> Unit
) {
    val labels = listOf("Aus", "Wenig", "Mittel", "Stark")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            value = value.toFloat(),
            onValueChange = { newValue ->
                onValueChange(newValue.roundToInt().coerceIn(0, 3))
            },
            valueRange = 0f..3f,
            steps = 2,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            labels.forEach { label ->
                Text(text = label, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}