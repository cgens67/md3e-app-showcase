package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.AdaptiveSandbox
import com.example.ui.screens.ComponentsShowcase
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val systemDark = isSystemInDarkTheme()
            val isDarkThemeState = remember { mutableStateOf(systemDark) }
            val isDarkTheme = isDarkThemeState.value

            MyApplicationTheme(darkTheme = isDarkTheme) {
                MainAppFrame(
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = { isDarkThemeState.value = !isDarkThemeState.value }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppFrame(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    var activeTab by remember { mutableStateOf("Showcase") } // Options: Showcase, Adaptive
    var showBlueprintInfo by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "MD3 Expressive",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Geometric Balance Labs • Interactive Doc",
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onThemeToggle,
                        modifier = Modifier.testTag("theme_toggle_btn")
                    ) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Theme Mode",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(
                        onClick = { showBlueprintInfo = true },
                        modifier = Modifier.testTag("app_info_trigger")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About MD3E",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .navigationBarsPadding()
            ) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), thickness = 1.dp)
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.height(80.dp),
                    windowInsets = WindowInsets(0, 0, 0, 0)
                ) {
                    NavigationBarItem(
                        selected = activeTab == "Showcase",
                        onClick = { activeTab = "Showcase" },
                        icon = { Icon(Icons.Default.Category, "Showcase Icons") },
                        label = { Text("Components", fontWeight = FontWeight.Bold, fontSize = 11.sp) },
                        modifier = Modifier.testTag("nav_tab_showcase")
                    )
                    NavigationBarItem(
                        selected = activeTab == "Adaptive",
                        onClick = { activeTab = "Adaptive" },
                        icon = { Icon(Icons.Default.AspectRatio, "Adaptive Sandbox Icons") },
                        label = { Text("Adaptive", fontWeight = FontWeight.Bold, fontSize = 11.sp) },
                        modifier = Modifier.testTag("nav_tab_adaptive")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Animate transition between different screens beautifully!
            AnimatedContent(
                targetState = activeTab,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
                },
                label = "ScreenTransition"
            ) { currentTab ->
                when (currentTab) {
                    "Showcase" -> ComponentsShowcase(modifier = Modifier.fillMaxSize())
                    "Adaptive" -> AdaptiveSandbox(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }

    // Modal Sheet or Dialog for Blueprint spec overview info
    if (showBlueprintInfo) {
        AlertDialog(
            onDismissRequest = { showBlueprintInfo = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Brush,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Material 3 Expressive",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Welcome to the official Material Design 3 Expressive (MD3E) Interactive Laboratory. MD3E is a stylistic upgrade to basic Material Design, characterized by fluid kinetics, organic morphing states, and dynamic typography sizing rules.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Divider(color = MaterialTheme.colorScheme.outlineVariant)
                    Text(
                        text = "Key Foundations:",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall
                    )
                    BulletItem("Wavy Kinetics: Custom bezier curves replace traditional linear structures on indicators.")
                    BulletItem("Asymmetry & Pill Shapes: Selective corner rounding highlights prioritized operations.")
                    BulletItem("Viewport Fluidity: Adaptive containers and side panels reshape dynamically.")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showBlueprintInfo = false },
                    modifier = Modifier.testTag("about_dialog_close")
                ) {
                    Text("Decline Labs View", color = MaterialTheme.colorScheme.outline)
                }
                Button(
                    onClick = {
                        showBlueprintInfo = false
                        Toast.makeText(context, "Welcome to the Future of Android Customization!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.testTag("about_dialog_confirm")
                ) {
                    Text("Enter Labs")
                }
            },
            shape = RoundedCornerShape(28.dp)
        )
    }
}

@Composable
fun BulletItem(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text("•", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(end = 8.dp))
        Text(text, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
