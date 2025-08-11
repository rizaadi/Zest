package com.zephysus.zest.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zephysus.zest.component.scaffold.ZestScaffold
import com.zephysus.zest.component.scaffold.ZestTopAppBar
import com.zephysus.zest.ui.theme.blackBg2

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onNavigateUp: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    SettingsContent(
        state = state,
        onToggleNotifications = viewModel::toggleNotifications,
        onNavigateUp = onNavigateUp
    )
}

@Composable
fun SettingsContent(
    state: SettingsState,
    onToggleNotifications: () -> Unit,
    onNavigateUp: () -> Unit
) {
    ZestScaffold(
        isLoading = state.isLoading,
        error = state.error,
        zestTopAppBar = {
            ZestTopAppBar(
                title = "Settings",
                onNavigateUp = onNavigateUp
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                SettingsCard {
                    SettingsItem(
                        title = "Language",
                        description = "Change the language",
                        isChecked = false,
                        onToggle = {}
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    SettingsItem(
                        title = "Notifications",
                        description = "Enable push notifications",
                        isChecked = state.notificationsEnabled,
                        onToggle = onToggleNotifications
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
                        text = "App Version: ${state.appVersion}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    )
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
    title: String,
    description: String,
    isChecked: Boolean,
    onToggle: () -> Unit
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
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
        
        Switch(
            checked = isChecked,
            onCheckedChange = { onToggle() }
        )
    }
}