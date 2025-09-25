package com.zephysus.zest.component.navigation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zephysus.zest.R
import com.zephysus.zest.ui.theme.LocalZestBackgroundTheme
import com.zephysus.zest.ui.theme.ZestTheme

@Composable
fun ZestBottomNavigation(
    currentRoute: String,
    onNavigateToSettings: () -> Unit,
    onNavigateToAddQuote: () -> Unit,
    onNavigateToQuotes: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundTheme = LocalZestBackgroundTheme.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(backgroundTheme.color)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
                    .clickable { onNavigateToSettings() }, contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_settings),
                    contentDescription = "Setting",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
                    .clickable { onNavigateToAddQuote() }, contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = "Add Quote",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(backgroundTheme.color)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
                    .clickable { onNavigateToQuotes() }, contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_list),
                    contentDescription = "Quotes",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, name = "Light Mode")
fun ZestBottomNavigationPreview() {
    ZestTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            ZestBottomNavigation(
                currentRoute = "home",
                onNavigateToSettings = { },
                onNavigateToAddQuote = { },
                onNavigateToQuotes = { },
            )
        }
    }
}

@Composable
@Preview(showBackground = true, name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ZestBottomNavigationDarkPreview() {
    ZestTheme(darkTheme = true) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        ) {
            ZestBottomNavigation(
                currentRoute = "home",
                onNavigateToSettings = { },
                onNavigateToAddQuote = { },
                onNavigateToQuotes = { },
            )
        }
    }
}