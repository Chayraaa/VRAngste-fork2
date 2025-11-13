package dev.group6.vrappcontroller

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Vrpano
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.group6.vrappcontroller.model.ControlModel
import dev.group6.vrappcontroller.model.DataModel
import dev.group6.vrappcontroller.model.ServerPopupModel
import dev.group6.vrappcontroller.model.StreamModel
import dev.group6.vrappcontroller.ui.theme.AppTheme
import dev.group6.vrappcontroller.view.ControlView
import dev.group6.vrappcontroller.view.DataView
import dev.group6.vrappcontroller.view.NavigationView
import dev.group6.vrappcontroller.view.ServerPopupView
import dev.group6.vrappcontroller.view.StreamView

@Composable
fun App() {

    val controlModel = ControlModel()
    val dataModel = DataModel()
    val streamModel = StreamModel()
    val serverPopupModel = ServerPopupModel()

    var isServerPopupVisible by remember { mutableStateOf(true) }

    AppTheme {
        Surface {
            Scaffold(
                modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
            ) { contentPadding ->
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    if (isServerPopupVisible)
                        ServerPopupView(
                            serverPopupModel,
                            onDismiss = {
                                isServerPopupVisible = false
                            }
                        )
                    NavigationView(
                        contentPadding,
                        controlModel,
                        dataModel,
                        streamModel,
                        reopenServerPopup = { isServerPopupVisible = true }
                    )
                }
            }
        }
    }
}