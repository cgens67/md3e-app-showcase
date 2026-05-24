@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdaptiveSandbox(modifier: Modifier = Modifier) {
    var deviceMode by remember { mutableStateOf("Compact") } // Options: Compact, Medium, Expanded

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Upper Intro Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Responsive Viewport Simulator",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Material 3 Expressive dynamically adapts navigation and layout structures across compact (mobile), medium (tablet), and expanded (desktop) screen classes. Toggle below to witness live adaptive transformations inside the preview viewport.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Segmented Device selector buttons
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            SegmentedButton(
                selected = deviceMode == "Compact",
                onClick = { deviceMode = "Compact" },
                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3),
                modifier = Modifier.testTag("device_compact_btn"),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.primary,
                    activeContentColor = MaterialTheme.colorScheme.onPrimary,
                    inactiveContainerColor = MaterialTheme.colorScheme.surface,
                    inactiveContentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text("Compact")
            }
            SegmentedButton(
                selected = deviceMode == "Medium",
                onClick = { deviceMode = "Medium" },
                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3),
                modifier = Modifier.testTag("device_medium_btn"),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.primary,
                    activeContentColor = MaterialTheme.colorScheme.onPrimary,
                    inactiveContainerColor = MaterialTheme.colorScheme.surface,
                    inactiveContentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text("Medium")
            }
            SegmentedButton(
                selected = deviceMode == "Expanded",
                onClick = { deviceMode = "Expanded" },
                shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3),
                modifier = Modifier.testTag("device_expanded_btn"),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.primary,
                    activeContentColor = MaterialTheme.colorScheme.onPrimary,
                    inactiveContainerColor = MaterialTheme.colorScheme.surface,
                    inactiveContentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text("Expanded")
            }
        }

        // Responsive Blueprint info line
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.CompassCalibration, "Specs", tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                val specs = when (deviceMode) {
                    "Compact" -> Pair("Compact Mobile Class (< 600dp)", "Bottom Navigation Bar • High-density bottom sheet overlays • Standard list scrolling")
                    "Medium" -> Pair("Medium Tablet Class (600dp - 840dp)", "Side Navigation Rail • Floating components • Dual Column Supporting Pane")
                    else -> Pair("Expanded Display Class (> 840dp)", "Persistent/Dismissible Navigation Drawer • Landscape Triple Column flow")
                }
                Text(specs.first, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                Text(specs.second, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Device simulator Viewport Frame
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                    shape = RoundedCornerShape(24.dp)
                )
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            // Animate width changes nicely
            val containerWidthFraction = when (deviceMode) {
                "Compact" -> 0.65f
                "Medium" -> 0.85f
                else -> 1f
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(containerWidthFraction)
                    .background(Color.Black, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .padding(2.dp)
            ) {
                // Smartphone Screen Inside
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    RenderDeviceMockContent(mode = deviceMode)
                }
            }
        }
    }
}

@Composable
fun RenderDeviceMockContent(mode: String) {
    when (mode) {
        "Compact" -> {
            // Mobile: Bottom Navigation Bar + Single column scrolling
            Scaffold(
                bottomBar = {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                        modifier = Modifier.height(64.dp)
                    ) {
                        NavigationBarItem(
                            selected = true,
                            onClick = {},
                            icon = { Icon(Icons.Default.Home, null, modifier = Modifier.size(20.dp)) },
                            label = { Text("Feed", fontSize = 9.sp) },
                            alwaysShowLabel = true
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = {},
                            icon = { Icon(Icons.Default.MailOutline, null, modifier = Modifier.size(20.dp)) },
                            label = { Text("Inbound", fontSize = 9.sp) },
                            alwaysShowLabel = true
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = {},
                            icon = { Icon(Icons.Default.PersonOutline, null, modifier = Modifier.size(20.dp)) },
                            label = { Text("Profile", fontSize = 9.sp) },
                            alwaysShowLabel = true
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {},
                        shape = CircleShape,
                        containerColor = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.Default.Add, null)
                    }
                }
            ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Text(
                            "Mobile Viewport",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Everything stacks single-column with rapid finger reach zones.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    items(4) { idx ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.size(24.dp).background(MaterialTheme.colorScheme.secondaryContainer, CircleShape))
                                    Text("Record #$idx", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("A compact single line metadata string rendering context.", style = MaterialTheme.typography.bodySmall, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }

        "Medium" -> {
            // Foldable: Navigation Rail + Dual Column list panel
            Row(modifier = Modifier.fillMaxSize()) {
                NavigationRail(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                    header = {
                        FloatingActionButton(onClick = {}, modifier = Modifier.size(40.dp)) {
                            Icon(Icons.Default.Add, null)
                        }
                    }
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    NavigationRailItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.Home, null) })
                    NavigationRailItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.MailOutline, null) })
                    Spacer(modifier = Modifier.weight(1f))
                    NavigationRailItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Settings, null) })
                }

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Left Column: Master feed
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Text("Master List", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(3) { idx ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
                                ) {
                                    Text("Active Item ${idx + 1}", modifier = Modifier.padding(12.dp), style = MaterialTheme.typography.labelMedium)
                                }
                            }
                        }
                    }

                    // Right Column: Detail preview pane
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Detail Pane", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Selecting an item on the left renders its content instantly inside this supporting detail panel, eliminating screen transitions completely.", style = MaterialTheme.typography.bodySmall, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        "Expanded" -> {
            // Tablet: Left persistent side drawer + Triple grid flow
            Row(modifier = Modifier.fillMaxSize()) {
                // Simulated Small Navigation Drawer
                Column(
                    modifier = Modifier
                        .width(85.dp)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                        )
                        .padding(6.dp)
                ) {
                    Text(
                        text = "Workspace",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Home, null, modifier = Modifier.size(12.dp), tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Office", fontSize = 9.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Inbox, null, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Mail", fontSize = 9.sp, maxLines = 1)
                    }
                }

                // Tablet screen body: Double grid structure for elegant simulator preview!
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Expanded Platform Dashboard",
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Adaptive grids utilize maximum width.",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 8.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(6) { idx ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(75.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(Icons.Default.BarChart, null, modifier = Modifier.size(12.dp), tint = MaterialTheme.colorScheme.secondary)
                                        Text(
                                            text = "Graph #$idx",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 9.sp,
                                            maxLines = 1,
                                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                        )
                                    }
                                    Text(
                                        text = "Metric data data",
                                        fontSize = 8.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1,
                                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
