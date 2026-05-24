@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ComponentDoc
import com.example.data.ComponentRegistry
import com.example.data.ParameterItem
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentsShowcase(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()

    var selectedCategory by remember { mutableStateOf("Progress & Loaders") }
    val categories = listOf("Progress & Loaders", "Buttons & Actions", "Pickers & Inputs", "Navigation & Structure", "Containers & Visuals")

    val categoryIcons = mapOf(
        "Progress & Loaders" to Icons.Default.Refresh,
        "Buttons & Actions" to Icons.Default.Mouse,
        "Pickers & Inputs" to Icons.Default.DateRange,
        "Navigation & Structure" to Icons.Default.Menu,
        "Containers & Visuals" to Icons.Default.Dashboard
    )

    // Current selected component for active inspection dialog/details
    var inspectedComponent by remember { mutableStateOf<ComponentDoc?>(null) }

    // Playground Interactive States
    var progressWavyValue by remember { mutableFloatStateOf(0.45f) }
    var badgeCount by remember { mutableStateOf(3) }
    var expandedFabMenu by remember { mutableStateOf(false) }
    var selectedSegmentIndex by remember { mutableStateOf(0) }
    var buttonGroupSpacing by remember { mutableFloatStateOf(8f) }
    var showBottomSheetDemo by remember { mutableStateOf(false) }
    var isLikedIconButton by remember { mutableStateOf(false) }
    var selectedIconButtonShapeName by remember { mutableStateOf("Symmetric Flower") }

    // DatePicker & TimePicker interactive state
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showTimePickerDialog by remember { mutableStateOf(false) }
    var selectedDateText by remember { mutableStateOf("No Date selected") }
    var selectedTimeText by remember { mutableStateOf("No Time selected") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Featured Adaptive Carousel Banner precisely matching Geometric Balance HTML spec
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                // Background decorative ambient abstract shape
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = 10.dp, y = 20.dp)
                        .background(
                            color = Color(0xFFD0BCFF).copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                )

                // Content Column
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "FEATURED",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Adaptive\nCarousel",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 28.sp,
                            lineHeight = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                     Button(
                        onClick = {
                            selectedCategory = "Containers & Visuals"
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            contentColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(
                            text = "View Docs",
                            color = MaterialTheme.colorScheme.primaryContainer,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Horizontal scrolling category bar
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(categories) { category ->
                val isSelected = selectedCategory == category
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategory = category },
                    label = {
                        Text(
                            text = category,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    leadingIcon = {
                        val icon = categoryIcons[category] ?: Icons.Default.Label
                        Icon(
                            imageVector = icon,
                            contentDescription = category,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.testTag("category_chip_${category.lowercase().replace(" ", "_")}")
                )
            }
        }

        Divider(color = MaterialTheme.colorScheme.surfaceVariant, thickness = 1.dp)

        // Main Showcase Scroll List
        val filteredComponents = ComponentRegistry.items.filter { it.category == selectedCategory }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(top = 8.dp, bottom = 48.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(filteredComponents) { doc ->
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("component_card_${doc.id}"),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(24.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = doc.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = doc.id,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontFamily = FontFamily.Monospace,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                            IconButton(
                                onClick = { inspectedComponent = doc },
                                colors = IconButtonDefaults.filledTonalIconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                ),
                                modifier = Modifier.testTag("inspect_btn_${doc.id}")
                            ) {
                                Icon(Icons.Default.Info, contentDescription = "Documentation")
                            }
                        }

                        Text(
                            text = doc.shortDescription,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Render the actual fully functional component inside the card slot
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(18.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(18.dp)
                                )
                                .padding(16.dp)
                                .animateContentSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            RenderInteractiveDemo(
                                id = doc.id,
                                progressWavyValue = progressWavyValue,
                                onProgressWavyChange = { progressWavyValue = it },
                                badgeCount = badgeCount,
                                onBadgeChange = { badgeCount = it },
                                expandedFabMenu = expandedFabMenu,
                                onFabMenuChange = { expandedFabMenu = it },
                                selectedSegmentIndex = selectedSegmentIndex,
                                onSegmentChange = { selectedSegmentIndex = it },
                                buttonGroupSpacing = buttonGroupSpacing,
                                onGroupSpacingChange = { buttonGroupSpacing = it },
                                isLikedIconButton = isLikedIconButton,
                                onLikeIconChange = { isLikedIconButton = it },
                                selectedIconButtonShapeName = selectedIconButtonShapeName,
                                onIconButtonShapeChange = { selectedIconButtonShapeName = it },
                                selectedDateText = selectedDateText,
                                showDatePicker = { showDatePickerDialog = true },
                                selectedTimeText = selectedTimeText,
                                showTimePicker = { showTimePickerDialog = true },
                                openBottomSheet = { showBottomSheetDemo = true }
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Auxiliary action buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(doc.codeSnippet))
                                    Toast.makeText(context, "Copied code to clipboard!", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.testTag("copy_code_btn_${doc.id}")
                            ) {
                                Icon(
                                    Icons.Default.ContentCopy,
                                    contentDescription = "Copy code",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Copy Snippet")
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal dialogue showing docs
    inspectedComponent?.let { doc ->
        AlertDialog(
            onDismissRequest = { inspectedComponent = null },
            confirmButton = {
                Button(
                    onClick = { inspectedComponent = null }
                ) {
                    Text("Understood")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(doc.codeSnippet))
                        Toast.makeText(context, "Copied code block!", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(Icons.Default.ContentCopy, "Copy", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Copy Code")
                }
            },
            title = {
                Column {
                    Text(doc.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                    Text(doc.category, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                }
            },
            text = {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = doc.deepDescription,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    item {
                        Text(
                            text = "Usage Guidelines",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = doc.usageGuidelines,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    item {
                        Text(
                            text = "Key Parameters",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    items(doc.parameters) { param ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(8.dp)
                        ) {
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Text(param.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge, fontFamily = FontFamily.Monospace, color = MaterialTheme.colorScheme.primary)
                                Text(param.type, style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace, color = MaterialTheme.colorScheme.secondary)
                            }
                            Text(param.description, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 2.dp))
                        }
                    }

                    item {
                        Text(
                            text = "Compose Source Code",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF23242A), RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Text(
                                doc.codeSnippet,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                color = Color(0xFFA9B2C3),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .heightIn(max = 600.dp)
        )
    }

    // Bottom Sheet System Demo Triggering
    if (showBottomSheetDemo) {
        val sheetState = rememberModalBottomSheetState()
        ModalBottomSheet(
            onDismissRequest = { showBottomSheetDemo = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier.testTag("bottom_sheet_modal")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Material 3 Expressive Bottom Sheet",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "This sheet is dynamic and full-featured, offering edge-to-edge layout safe overlays and smooth spring dismissing animations.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheetDemo = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Close Bottom Sheet")
                }
            }
        }
    }

    // Dynamic Date Picker Dialog
    if (showDatePickerDialog) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val timestamp = datePickerState.selectedDateMillis
                        if (timestamp != null) {
                            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                            selectedDateText = sdf.format(java.util.Date(timestamp))
                        } else {
                            selectedDateText = "No Date Selected"
                        }
                        showDatePickerDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePickerDialog = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // Dynamic Time Picker Dialog
    if (showTimePickerDialog) {
        val timePickerState = rememberTimePickerState()
        AlertDialog(
            onDismissRequest = { showTimePickerDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val hour = timePickerState.hour
                        val minute = timePickerState.minute
                        selectedTimeText = String.format("%02d:%02d", hour, minute)
                        showTimePickerDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePickerDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Select Time", fontWeight = FontWeight.Bold) },
            text = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    TimePicker(state = timePickerState)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenderInteractiveDemo(
    id: String,
    progressWavyValue: Float,
    onProgressWavyChange: (Float) -> Unit,
    badgeCount: Int,
    onBadgeChange: (Int) -> Unit,
    expandedFabMenu: Boolean,
    onFabMenuChange: (Boolean) -> Unit,
    selectedSegmentIndex: Int,
    onSegmentChange: (Int) -> Unit,
    buttonGroupSpacing: Float,
    onGroupSpacingChange: (Float) -> Unit,
    isLikedIconButton: Boolean,
    onLikeIconChange: (Boolean) -> Unit,
    selectedIconButtonShapeName: String,
    onIconButtonShapeChange: (String) -> Unit,
    selectedDateText: String,
    showDatePicker: () -> Unit,
    selectedTimeText: String,
    showTimePicker: () -> Unit,
    openBottomSheet: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    when (id) {
        "LinearWavyProgressIndicator" -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Live Progress Indicator Render
                LinearWavyProgressIndicator(
                    progress = { progressWavyValue },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                )

                // Drag Slider to change value
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.GraphicEq, contentDescription = "Adjust", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Adjust value:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(String.format("%.0f%%", progressWavyValue * 100), style = MaterialTheme.typography.labelMedium, fontFamily = FontFamily.Monospace)
                }
                Slider(
                    value = progressWavyValue,
                    onValueChange = onProgressWavyChange,
                    valueRange = 0f..1f,
                    modifier = Modifier.testTag("progress_wavy_slider")
                )
            }
        }

        "LoadingIndicator" -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                var morphColorToggle by remember { mutableStateOf(true) }
                val targetColor = if (morphColorToggle) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary

                // Real Loading Indicator from Compose M3!
                LoadingIndicator(
                    modifier = Modifier.size(56.dp),
                    color = targetColor
                )

                Button(
                    onClick = { morphColorToggle = !morphColorToggle },
                    modifier = Modifier.testTag("loading_indicator_color_trigger")
                ) {
                    Text("Switch Morph Vibe")
                }
            }
        }

        "SplitButtonLayout" -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var expandedSplitDropdown by remember { mutableStateOf(false) }

                SplitButtonLayout(
                    leadingButton = {
                        Button(
                            onClick = { Toast.makeText(context, "Commit changes", Toast.LENGTH_SHORT).show() },
                            shape = RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp, topEnd = 0.dp, bottomEnd = 0.dp)
                        ) {
                            Text("Commit")
                        }
                    },
                    trailingButton = {
                        IconButton(
                            onClick = { expandedSplitDropdown = !expandedSplitDropdown },
                            shape = RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, topEnd = 24.dp, bottomEnd = 24.dp),
                            modifier = Modifier.testTag("split_arrow_button")
                        ) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                        }
                    }
                )

                DropdownMenu(
                    expanded = expandedSplitDropdown,
                    onDismissRequest = { expandedSplitDropdown = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Commit & Push") },
                        onClick = {
                            expandedSplitDropdown = false
                            Toast.makeText(context, "Changes committed & pushed!", Toast.LENGTH_SHORT).show()
                        },
                        leadingIcon = { Icon(Icons.Default.CloudUpload, null) }
                    )
                    DropdownMenuItem(
                        text = { Text("Amend Commit") },
                        onClick = {
                            expandedSplitDropdown = false
                            Toast.makeText(context, "Amended last commit!", Toast.LENGTH_SHORT).show()
                        },
                        leadingIcon = { Icon(Icons.Default.Edit, null) }
                    )
                }
            }
        }

        "SegmentedButton" -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val items = listOf("Grid View", "List Stream", "Gallery Tab")

                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items.forEachIndexed { index, label ->
                        SegmentedButton(
                            selected = selectedSegmentIndex == index,
                            onClick = { onSegmentChange(index) },
                            shape = SegmentedButtonDefaults.itemShape(index = index, count = items.size),
                            modifier = Modifier.testTag("segment_${index}")
                        ) {
                            Text(label)
                        }
                    }
                }
                Text(
                    text = "Active view mode: ${items[selectedSegmentIndex]}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        "IconButton" -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Determine shapes custom
                val squircleShape = RoundedCornerShape(14.dp)
                val triangleShape = GenericShape { size, _ ->
                    moveTo(size.width / 2f, 0f)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }
                val flowerShape = GenericShape { size, _ ->
                    val numPetals = 8
                    val center = size.width / 2f
                    val outerRadius = size.width / 2f
                    val innerRadius = outerRadius * 0.72f
                    moveTo(center + outerRadius, center)
                    for (i in 0..numPetals * 2) {
                        val angle = i * PI / numPetals
                        val r = if (i % 2 == 0) outerRadius else innerRadius
                        val x = center + r * cos(angle).toFloat()
                        val y = center + r * sin(angle).toFloat()
                        lineTo(x, y)
                    }
                    close()
                }

                val currentShape = when (selectedIconButtonShapeName) {
                    "Asymmetric Flower" -> flowerShape
                    "Organic Triangle" -> triangleShape
                    else -> squircleShape
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Elevated Custom Shape Icon Button
                    IconButton(
                        onClick = { onLikeIconChange(!isLikedIconButton) },
                        shape = currentShape,
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = if (isLikedIconButton) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.tertiaryContainer,
                        ),
                        modifier = Modifier
                            .size(56.dp)
                            .testTag("expressive_icon_button")
                    ) {
                        Icon(
                            imageVector = if (isLikedIconButton) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isLikedIconButton) Color.Red else MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text("Active Shape: $selectedIconButtonShapeName", style = MaterialTheme.typography.labelMedium)
                        Text(if (isLikedIconButton) "Trigger: Liked!" else "Trigger: Unliked", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Toggle between shapes
                val shapeNames = listOf("Symmetric Flower", "Organic Triangle", "Rounded Squircle")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(listOf("Asymmetric Flower", "Organic Triangle", "Rounded Squircle")) { shapeOption ->
                        val isSelected = selectedIconButtonShapeName == shapeOption
                        ElevatedFilterChip(
                            selected = isSelected,
                            onClick = { onIconButtonShapeChange(shapeOption) },
                            label = { Text(shapeOption, fontSize = 11.sp) }
                        )
                    }
                }
            }
        }

        "FloatingActionButtonMenu" -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.BottomEnd
            ) {
                // Fabricated floating button menu preview area
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Click FAB below to test expansion states in miniature viewport.",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.TopStart),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // FAB Menu layout integration from Compose
                FloatingActionButtonMenu(
                    expanded = expandedFabMenu,
                    button = {
                        FloatingActionButton(
                            onClick = { onFabMenuChange(!expandedFabMenu) },
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.testTag("fab_menu_toggle")
                        ) {
                            Icon(
                                if (expandedFabMenu) Icons.Filled.Close else Icons.Filled.Create,
                                contentDescription = "Create Actions"
                            )
                        }
                    },
                    modifier = Modifier.padding(16.dp),
                    content = {
                        FloatingActionButtonMenuItem(
                            onClick = {
                                onFabMenuChange(false)
                                Toast.makeText(context, "Launch Task Creation", Toast.LENGTH_SHORT).show()
                            },
                            icon = { Icon(Icons.Default.AssignmentTurnedIn, "Task") },
                            text = { Text("New Project Task") }
                        )
                        FloatingActionButtonMenuItem(
                            onClick = {
                                onFabMenuChange(false)
                                Toast.makeText(context, "Launch Note Creation", Toast.LENGTH_SHORT).show()
                            },
                            icon = { Icon(Icons.Default.NoteAdd, "Note") },
                            text = { Text("New Note File") }
                        )
                    }
                )
            }
        }

        "ExtendedFloatingActionButton" -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                var fabExpandedState by remember { mutableStateOf(true) }

                ExtendedFloatingActionButton(
                    onClick = { fabExpandedState = !fabExpandedState },
                    icon = { Icon(Icons.Default.SendAndArchive, "Archive") },
                    text = { Text("Archive Active Logs") },
                    expanded = fabExpandedState,
                    modifier = Modifier.testTag("extended_fab")
                )

                Text(
                    text = if (fabExpandedState) "Click me to contract icon size" else "Click me to expand labels",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        "Button" -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { Toast.makeText(context, "Standard trigger", Toast.LENGTH_SHORT).show() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.OpenInNew, "Logo", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Expressive Primary Button")
                }

                ElevatedButton(
                    onClick = { Toast.makeText(context, "Elevated trigger", Toast.LENGTH_SHORT).show() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Elevated Material Card Button")
                }

                FilledTonalButton(
                    onClick = { Toast.makeText(context, "Tonal trigger", Toast.LENGTH_SHORT).show() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Filled Tonal Container Button")
                }
            }
        }

        "ButtonGroup" -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Interactive Spacing control
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Spacers Gap: ${buttonGroupSpacing.toInt()}dp", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                    Slider(
                        value = buttonGroupSpacing,
                        onValueChange = onGroupSpacingChange,
                        valueRange = 0f..24f,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                            .testTag("group_spacing_slider")
                    )
                }

                // Render real ButtonGroup layout
                ButtonGroup(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonGroupSpacing.dp)
                ) {
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Discard")
                    }

                    Button(
                        onClick = { Toast.makeText(context, "Changes committed", Toast.LENGTH_SHORT).show() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }

        "DatePicker" -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Selected Date value: $selectedDateText",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Button(
                    onClick = showDatePicker,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("show_date_picker_trigger")
                ) {
                    Icon(Icons.Default.CalendarMonth, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Open Calendar DatePicker")
                }
            }
        }

        "TimePicker" -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Selected Time value: $selectedTimeText",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Button(
                    onClick = showTimePicker,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("show_time_picker_trigger")
                ) {
                    Icon(Icons.Default.AccessTime, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Open Dial Clock TimePicker")
                }
            }
        }

        "NavigationBar" -> {
            var activeNavIndex by remember { mutableStateOf(0) }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Simulated Screen Navigation Bar:", style = MaterialTheme.typography.labelSmall)
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.clip(RoundedCornerShape(12.dp))
                ) {
                    NavigationBarItem(
                        selected = activeNavIndex == 0,
                        onClick = { activeNavIndex = 0 },
                        icon = { Icon(Icons.Default.Home, "Home") },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = activeNavIndex == 1,
                        onClick = { activeNavIndex = 1 },
                        icon = {
                            BadgedBox(badge = { Badge { Text("9") } }) {
                                Icon(Icons.Default.Chat, "Chats")
                            }
                        },
                        label = { Text("Chat") }
                    )
                    NavigationBarItem(
                        selected = activeNavIndex == 2,
                        onClick = { activeNavIndex = 2 },
                        icon = { Icon(Icons.Default.Settings, "Configure") },
                        label = { Text("Settings") }
                    )
                }
            }
        }

        "NavigationRail" -> {
            var activeRailIndex by remember { mutableStateOf(0) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                        RoundedCornerShape(8.dp)
                    )
            ) {
                // Miniature side navigation rail
                NavigationRail(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    header = {
                        FloatingActionButton(
                            onClick = { Toast.makeText(context, "New entry", Toast.LENGTH_SHORT).show() },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(Icons.Default.Add, "Add", modifier = Modifier.size(20.dp))
                        }
                    },
                    modifier = Modifier.clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    NavigationRailItem(
                        selected = activeRailIndex == 0,
                        onClick = { activeRailIndex = 0 },
                        icon = { Icon(Icons.Default.Folder, "Files") },
                        label = { Text("Files", fontSize = 10.sp) }
                    )
                    NavigationRailItem(
                        selected = activeRailIndex == 1,
                        onClick = { activeRailIndex = 1 },
                        icon = { Icon(Icons.Default.Analytics, "Data") },
                        label = { Text("Stats", fontSize = 10.sp) }
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Side Nav Rail is ideal for Medium/Landscape width devices. Active state: index $activeRailIndex.",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        "NavigationDrawer" -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
            ) {
                var activeDrawerIndex by remember { mutableStateOf(0) }

                DismissibleNavigationDrawer(
                    drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
                    drawerContent = {
                        DismissibleDrawerSheet(
                            drawerContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.width(170.dp).fillMaxHeight()
                        ) {
                            Text("Menu Drawer", modifier = Modifier.padding(12.dp), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                            NavigationDrawerItem(
                                label = { Text("All Mail", fontSize = 11.sp) },
                                selected = activeDrawerIndex == 0,
                                onClick = { activeDrawerIndex = 0 },
                                icon = { Icon(Icons.Default.Mail, null, modifier = Modifier.size(16.dp)) },
                                modifier = Modifier.padding(horizontal = 6.dp)
                            )
                            NavigationDrawerItem(
                                label = { Text("Trash Bucket", fontSize = 11.sp) },
                                selected = activeDrawerIndex == 1,
                                onClick = { activeDrawerIndex = 1 },
                                icon = { Icon(Icons.Default.Delete, null, modifier = Modifier.size(16.dp)) },
                                modifier = Modifier.padding(horizontal = 6.dp)
                            )
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface).padding(12.dp)) {
                        Text("Active Page: Mail", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }

        "AppBarRow" -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AppBarRow(
                    title = {
                        Text("Compose Expressive AppBar", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = { }) { Icon(Icons.Default.Search, "Search") }
                        IconButton(onClick = { }) { Icon(Icons.Default.MoreVert, "More") }
                    },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
                        .clip(RoundedCornerShape(6.dp))
                )
            }
        }

        "Badges" -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(28.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Normal Dot Badge
                    BadgedBox(
                        badge = { Badge() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "New email alert",
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    // Count Badge
                    BadgedBox(
                        badge = {
                            if (badgeCount > 0) {
                                Badge {
                                    Text("$badgeCount")
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications alerts",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                // Controls to adjust badge count
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FilledTonalButton(
                        onClick = { if (badgeCount > 0) onBadgeChange(badgeCount - 1) },
                        modifier = Modifier.testTag("badge_decrement")
                    ) {
                        Text("- Decr")
                    }
                    Button(
                        onClick = { onBadgeChange(badgeCount + 1) },
                        modifier = Modifier.testTag("badge_increment")
                    ) {
                        Text("+ Incr")
                    }
                }
            }
        }

        "Cards" -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Elevated Material Card Theme", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Text("Utilizes soft background shadows to establish localized depth layers.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Filled Container Card Theme", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Blends directly into primary background sheets using solid grey fills.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                OutlinedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Outlined Border Card Theme", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Text("Uses sharp outlines instead of visual height elevations.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        "Carousel" -> {
            val slides = listOf(
                Triple("Creative Idea Flow", "Launch innovative project designs with wavy templates.", MaterialTheme.colorScheme.primaryContainer),
                Triple("Spring Velocity Animation", "Build super-responsive components using spring curves.", MaterialTheme.colorScheme.secondaryContainer),
                Triple("Responsive Layout Grid", "Ensure precise visual scales from compact to tablets.", MaterialTheme.colorScheme.tertiaryContainer),
                Triple("Adaptive Space Map", "Seamlessly reflow charts and lists dynamically.", MaterialTheme.colorScheme.primaryContainer),
                Triple("Precision Timing Matrix", "Measure fluid states in microscopic latency frames.", MaterialTheme.colorScheme.secondaryContainer)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Style 1: Multi-Browse Carousel (Clipped)
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("M3 Style", color = MaterialTheme.colorScheme.onPrimary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(
                            text = "Multi-Browse Carousel",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = "The last item in the view is partially clipped at the container edge to establish scroll affordance.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    HorizontalMultiBrowseCarousel(
                        state = rememberCarouselState { slides.size },
                        preferredItemWidth = 145.dp, // Sized perfectly so the trailing card is clipped!
                        itemSpacing = 8.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    ) { index ->
                        val slide = slides[index]
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(slide.third, RoundedCornerShape(16.dp))
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                                .padding(12.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
                                Icon(Icons.Default.Star, null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.primary)
                                Column {
                                    Text(slide.first, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(slide.second, style = MaterialTheme.typography.bodySmall, fontSize = 10.sp, maxLines = 2, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                                }
                            }
                        }
                    }
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), thickness = 1.dp)

                // Style 2: Uncontained Carousel (Unclipped)
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("Unclipped", color = MaterialTheme.colorScheme.onSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(
                            text = "Uncontained Carousel",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = "Shows the active item fully size-aligned. The last item is not clipped when resting, creating a clean rectangular look.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    HorizontalMultiBrowseCarousel(
                        state = rememberCarouselState { slides.size },
                        preferredItemWidth = 265.dp, // Sized so that each slide takes up full available width and doesn't partially clip other slides!
                        itemSpacing = 8.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    ) { index ->
                        val slide = slides[index]
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(slide.third, RoundedCornerShape(16.dp))
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                                .padding(12.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
                                Icon(Icons.Default.Favorite, null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.secondary)
                                Column {
                                    Text(slide.first, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(slide.second, style = MaterialTheme.typography.bodySmall, fontSize = 10.sp, maxLines = 2, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                                }
                            }
                        }
                    }
                }
            }
        }

        "BottomSheet" -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = openBottomSheet,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("open_bottom_sheet_trigger")
                ) {
                    Icon(Icons.Default.VerticalAlignTop, "Open Sheet")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Trigger Bottom Overlay Sheet")
                }
                Text("Clicking triggers sheet rise with modal dismissals.", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun AppBarRow(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.padding(start = 4.dp)) {
            navigationIcon()
        }
        Box(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
            title()
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            content = actions
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalMultiBrowseCarousel(
    state: CarouselState,
    preferredItemWidth: androidx.compose.ui.unit.Dp,
    itemSpacing: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier,
    content: @Composable (Int) -> Unit
) {
    LazyRow(
        modifier = modifier.height(200.dp),
        horizontalArrangement = Arrangement.spacedBy(itemSpacing),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(state.itemsCount) { index ->
            Box(modifier = Modifier.width(preferredItemWidth)) {
                content(index)
            }
        }
    }
}

class CarouselState(val itemsCount: Int)

@Composable
fun rememberCarouselState(itemsCount: () -> Int): CarouselState {
    val count = remember { itemsCount() }
    return remember { CarouselState(count) }
}
