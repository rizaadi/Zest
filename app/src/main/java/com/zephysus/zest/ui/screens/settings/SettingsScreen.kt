package com.zephysus.zest.ui.screens.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zephysus.core.model.NotificationInterval
import com.zephysus.zest.component.scaffold.ZestScaffold
import com.zephysus.zest.component.scaffold.ZestTopAppBar
import com.zephysus.zest.ui.theme.blackBg2

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(), onNavigateUp: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.onPermissionGranted()
        } else {
            viewModel.onPermissionDenied()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.checkNotificationPermission(context)
    }

    SettingsContent(
        state = state,
        onToggleNotifications = viewModel::toggleNotifications,
        onUpdateInterval = viewModel::updateNotificationInterval,
        onDismissPermissionDialog = viewModel::dismissPermissionDialog,
        onDismissNoQuotesWarning = viewModel::dismissNoQuotesWarning,
        onRequestPermission = {
            viewModel.requestNotificationPermission(permissionLauncher)
        },
        onNavigateUp = onNavigateUp
    )
}

@Composable
fun SettingsContent(
    state: SettingsState,
    onToggleNotifications: () -> Unit,
    onUpdateInterval: (NotificationInterval) -> Unit,
    onDismissPermissionDialog: () -> Unit,
    onDismissNoQuotesWarning: () -> Unit,
    onRequestPermission: () -> Unit,
    onNavigateUp: () -> Unit
) {
    ZestScaffold(zestTopAppBar = {
        ZestTopAppBar(
            title = "Settings", onNavigateUp = onNavigateUp
        )
    }, content = { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            SettingsCard {

                NotificationSettingsSection(
                    notificationSettings = state.notificationSettings,
                    hasPermission = state.hasNotificationPermission,
                    hasFeaturedQuotes = state.hasFeaturedQuotes,
                    onToggleNotifications = onToggleNotifications,
                    onUpdateInterval = onUpdateInterval
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            SettingsCard {
                Text(
                    text = "About",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "App Version: ${state.appVersion}", fontSize = 14.sp, color = Color.Gray
                )
            }
        }
    })

    // Permission Dialog
    if (state.showPermissionDialog) {
        AlertDialog(
            onDismissRequest = onDismissPermissionDialog,
            title = { Text("Notification Permission Required") },
            text = {
                Text("Please grant notification permission to receive featured quote reminders.")
            },
            confirmButton = {
                Button(onClick = onRequestPermission) {
                    Text("Grant Permission")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissPermissionDialog) {
                    Text("Cancel")
                }
            })
    }

    // No Quotes Warning Dialog
    if (state.showNoQuotesWarning) {
        AlertDialog(
            onDismissRequest = onDismissNoQuotesWarning,
            title = { Text("No Featured Quotes") },
            text = {
                Text("Please add some featured quotes before enabling notifications. Go to the quotes page and mark quotes as featured.")
            },
            confirmButton = {
                Button(onClick = onDismissNoQuotesWarning) {
                    Text("OK")
                }
            })
    }
}

@Composable
private fun SettingsCard(
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = blackBg2),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun SettingsItem(
    title: String, description: String, isChecked: Boolean, onToggle: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.White
            )
            Text(
                text = description, fontSize = 14.sp, color = Color.Gray
            )
        }

        Switch(
            checked = isChecked, onCheckedChange = { onToggle() })
    }
}

@Composable
private fun NotificationSettingsSection(
    notificationSettings: com.zephysus.core.model.NotificationSettings,
    hasPermission: Boolean,
    hasFeaturedQuotes: Boolean,
    onToggleNotifications: () -> Unit,
    onUpdateInterval: (NotificationInterval) -> Unit
) {
    Column {
        SettingsItem(
            title = "Notifications", description = when {
                !hasFeaturedQuotes -> "Add featured quotes first"
                !hasPermission -> "Permission required"
                else -> "Receive featured quote reminders"
            }, isChecked = notificationSettings.isEnabled, onToggle = onToggleNotifications
        )

        if (notificationSettings.isEnabled) {
            Spacer(modifier = Modifier.height(12.dp))

            IntervalSelector(
                selectedInterval = notificationSettings.interval,
                onIntervalSelected = onUpdateInterval
            )
        }
    }
}

@Composable
private fun IntervalSelector(
    selectedInterval: NotificationInterval, onIntervalSelected: (NotificationInterval) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = "Notification Interval",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box {
            Button(
                onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedInterval.displayName)
            }

            DropdownMenu(
                expanded = expanded, onDismissRequest = { expanded = false }) {
                NotificationInterval.entries.forEach { interval ->
                    DropdownMenuItem(text = { Text(interval.displayName) }, onClick = {
                        onIntervalSelected(interval)
                        expanded = false
                    })
                }
            }
        }
    }
}