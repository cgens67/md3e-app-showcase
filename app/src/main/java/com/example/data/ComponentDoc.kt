package com.example.data

data class ComponentDoc(
    val id: String,
    val name: String,
    val category: String,
    val shortDescription: String,
    val deepDescription: String,
    val usageGuidelines: String,
    val parameters: List<ParameterItem>,
    val codeSnippet: String
)

data class ParameterItem(
    val name: String,
    val type: String,
    val description: String
)

object ComponentRegistry {
    val items = listOf(
        ComponentDoc(
            id = "LinearWavyProgressIndicator",
            name = "Linear Wavy Progress Indicator",
            category = "Progress & Loaders",
            shortDescription = "Expressive wavy progress indicator that brings a high degree of visual personality.",
            deepDescription = "LinearWavyProgressIndicator is a dynamic alternative to the standard flat ProgressIndicator. It utilizes bezier wavy paths that animate to provide feedback on pending processes with an organic, playful touch.",
            usageGuidelines = "Use for major content loads, file transfers, or asynchronous operations where a playful brand presentation matches user experiences.",
            parameters = listOf(
                ParameterItem("progress", "Float / () -> Float", "The current progress value (0.0 to 1.0)."),
                ParameterItem("modifier", "Modifier", "To apply styling, padding, and size constraints."),
                ParameterItem("color", "Color", "The color of the wavy progress path foreground."),
                ParameterItem("trackColor", "Color", "The color of the underlying track vector."),
                ParameterItem("stroke", "Stroke", "Controls the stroke width and cap style of the progress bar."),
                ParameterItem("wavelength", "Dp", "The spacing/frequency between wave crests.")
            ),
            codeSnippet = """
LinearWavyProgressIndicator(
    progress = { progressValue },
    modifier = Modifier.fillMaxWidth(),
    color = MaterialTheme.colorScheme.primary,
    trackColor = MaterialTheme.colorScheme.surfaceVariant,
    wavelength = 24.dp
)
            """.trimIndent()
        ),
        ComponentDoc(
            id = "LoadingIndicator",
            name = "Loading Indicator",
            category = "Progress & Loaders",
            shortDescription = "High-energy Material 3 Expressive circular loading morphing shape.",
            deepDescription = "The expressive LoadingIndicator performs playful morphing transitions between organic geometric paths, keeping users engaged during empty states or full-page sync operations.",
            usageGuidelines = "Implement at the center of empty dashboards, full-screen transitions, or splash loads.",
            parameters = listOf(
                ParameterItem("modifier", "Modifier", "Sizing or alignment configurations."),
                ParameterItem("color", "Color", "Vibrant container color of the indicator morphing fill."),
                ParameterItem("polygons", "List<Polygon>", "Shapes used in the keypoint morph sequence.")
            ),
            codeSnippet = """
LoadingIndicator(
    modifier = Modifier.size(48.dp),
    color = MaterialTheme.colorScheme.primary
)
            """.trimIndent()
        ),
        ComponentDoc(
            id = "SplitButtonLayout",
            name = "Split Button Layout",
            category = "Buttons & Actions",
            shortDescription = "Combines a main click action with a supplementary dropdown drawer target.",
            deepDescription = "An expressive layout representing common quick actions. It splits clickable boundaries neatly into a primary trigger surface (start) and an anchor icon (end) nested inside a single contextual pill.",
            usageGuidelines = "Ideal for actions with explicit local histories, such as 'Save' matched with 'Save As...' or 'Run' and 'Debug'.",
            parameters = listOf(
                ParameterItem("leadingButton", "Composable", "The primary button element (usually Filled Button)."),
                ParameterItem("trailingButton", "Composable", "The anchor button (usually a dropdown arrow icon outline)."),
                ParameterItem("modifier", "Modifier", "Provides external layout boundaries."),
                ParameterItem("spacing", "Dp", "Divider spacing size between both halves.")
            ),
            codeSnippet = """
SplitButtonLayout(
    leadingButton = {
        Button(
            onClick = { onPrimaryClick() },
            shape = SplitButtonDefaults.leadingButtonShape()
        ) {
            Text("Publish Live")
        }
    },
    trailingButton = {
        IconButton(
            onClick = { onDropdownClick() },
            shape = SplitButtonDefaults.trailingButtonShape()
        ) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = "More options")
        }
    }
)
            """.trimIndent()
        ),
        ComponentDoc(
            id = "SegmentedButton",
            name = "Segmented Button Row",
            category = "Buttons & Actions",
            shortDescription = "Linear choice clusters toggling exclusive or multi-select layout operations.",
            deepDescription = "SegmentedButtons visually cluster mutual options in one continuous block, showing active options with a background color shift and checked vector checkmarks.",
            usageGuidelines = "Perfect for changing viewport formats, sorting preferences, or basic quick filters (e.g. Day/Week/Month).",
            parameters = listOf(
                ParameterItem("selected", "Boolean", "States whether the target item is currently active."),
                ParameterItem("onClick", "() -> Unit", "Callback when the button segment is clicked."),
                ParameterItem("shape", "Shape", "The bounding corner style representing position inside the cluster.")
            ),
            codeSnippet = """
SingleChoiceSegmentedButtonRow {
    options.forEachIndexed { index, label ->
        SegmentedButton(
            selected = selectedIndex == index,
            onClick = { selectedIndex = index },
            shape = SegmentedButtonDefaults.itemShape(index = index, count = count)
        ) {
            Text(label)
        }
    }
}
            """.trimIndent()
        ),
        ComponentDoc(
            id = "IconButton",
            name = "Expressive Icons & Shapes",
            category = "Buttons & Actions",
            shortDescription = "Icon-centric touch targets utilizing playful, asymmetric shapes in MD3E.",
            deepDescription = "Material 3 Expressive introduces diverse shapes (like flowers, rounded triangles, hearts, pentagons) for icon button shapes to reflect dynamic state changes or playfulness.",
            usageGuidelines = "Use for key toolbar actions, creative favoriting mechanisms, or localized widget state triggers.",
            parameters = listOf(
                ParameterItem("onClick", "() -> Unit", "Input trigger action."),
                ParameterItem("shape", "Shape", "Enables custom expressive styling like Flower, Star, Squircle, or Triangle."),
                ParameterItem("colors", "IconButtonColors", "Determines state-driven visual fills (Filled, Outlined, FilledTonal).")
            ),
            codeSnippet = """
IconButton(
    onClick = { isFav = !isFav },
    shape = SquircleShape(12.dp),
    colors = IconButtonDefaults.filledIconButtonColors(
        containerColor = MaterialTheme.colorScheme.tertiaryContainer
    )
) {
    Icon(Icons.Filled.Favorite, "Like")
}
            """.trimIndent()
        ),
        ComponentDoc(
            id = "FloatingActionButtonMenu",
            name = "Floating Action Button Menu",
            category = "Buttons & Actions",
            shortDescription = "A primary action FAB that expands elegantly into a secondary command list.",
            deepDescription = "The Floating Action Button Menu transitions smoothly between an active collapsed indicator flag and a gorgeous contextual list layout, drawing clean focus onto action hierarchies.",
            usageGuidelines = "Use on bottom corners when a screen centers a primary action that has several secondary creation options (e.g., 'Assemble project' expanding into 'Add File', 'Add URL').",
            parameters = listOf(
                ParameterItem("expanded", "Boolean", "Controls visibility of secondary menu items."),
                ParameterItem("button", "Composable", "Primary FAB click trigger container."),
                ParameterItem("items", "Composable", "A column displaying secondary navigation or creation buttons.")
            ),
            codeSnippet = """
FloatingActionButtonMenu(
    expanded = isExpanded,
    button = {
        FloatingActionButton(onClick = { isExpanded = !isExpanded }) {
            Icon(Icons.Default.Add, "Open actions")
        }
    },
    items = {
        FloatingActionButtonMenuItem(
            onClick = { /* File action */ },
            icon = { Icon(Icons.Default.UploadFile, "Add File") },
            text = { Text("Upload Document") }
        )
    }
)
            """.trimIndent()
        ),
        ComponentDoc(
            id = "ButtonGroup",
            name = "Button Group Showcase",
            category = "Buttons & Actions",
            shortDescription = "Expressive, spring-aligned button clusters with contextual spacing.",
            deepDescription = "ButtonGroup helps layout, cluster, and compress standard M3 buttons. Utilizing smart negative spacing, it supports flexible dynamic spacing based on responsive context.",
            usageGuidelines = "Implement at the bottom of forms, modal sheet actions, or wizard steps to align actions properly.",
            parameters = listOf(
                ParameterItem("modifier", "Modifier", "Structural spacing constraints."),
                ParameterItem("spacing", "Dp", "The gap between adjacent button bodies.")
            ),
            codeSnippet = """
ButtonGroup(
    modifier = Modifier.fillMaxWidth(),
    spacing = 8.dp
) {
    OutlinedButton(onClick = { }) { Text("Cancel") }
    Button(onClick = { }) { Text("Submit Form") }
}
            """.trimIndent()
        ),
        ComponentDoc(
            id = "DatePicker",
            name = "Date & Range Pickers",
            category = "Pickers & Inputs",
            shortDescription = "Expressive, scrollable grid selector for singular dates or multi-range intervals.",
            deepDescription = "DatePickers provide high-altitude scheduling controls. In Material 3, they feature an elegant calendar grid, smooth year-swapping menus, and high-contrast text fields for direct key inputs.",
            usageGuidelines = "Implement for travel booking timelines, report generations, or chronological filter bounds.",
            parameters = listOf(
                ParameterItem("state", "DatePickerState", "Tracks chosen date calendar timestamps."),
                ParameterItem("showModeToggle", "Boolean", "Permits shifting between calendar grids and manual inputs.")
            ),
            codeSnippet = """
val datePickerState = rememberDatePickerState()
DatePicker(
    state = datePickerState,
    modifier = Modifier.padding(16.dp),
    showModeToggle = true
)
            """.trimIndent()
        ),
        ComponentDoc(
            id = "TimePicker",
            name = "Time Picker Dial & Inputs",
            category = "Pickers & Inputs",
            shortDescription = "Intuitive dial clock selector supporting clock hands or manual typed states.",
            deepDescription = "Compose TimePickers offer dual interaction modes: a playful clock Dial where a dragging cursor follows a circular sequence, or raw Text Input grids for rapid manual typings.",
            usageGuidelines = "Ideal for morning alarms, calendar invite times, or system automation schedulers.",
            parameters = listOf(
                ParameterItem("state", "TimePickerState", "Tracks hours, minutes, and AM/PM options.")
            ),
            codeSnippet = """
val timePickerState = rememberTimePickerState()
TimePicker(
    state = timePickerState,
    modifier = Modifier.align(Alignment.CenterHorizontally)
)
            """.trimIndent()
        ),
        ComponentDoc(
            id = "NavigationBar",
            name = "Interactive Navigation Bar",
            category = "Navigation & Structure",
            shortDescription = "Standard, screen-bottom navigation bar with active state pill sliders.",
            deepDescription = "Modern NavigationBar holds 3 to 5 destinations. Clicking handles translate into active sliding capsule highlights with smooth spring rebounds.",
            usageGuidelines = "Mandatory primary navigation mechanism for Compact Mobile viewports.",
            parameters = listOf(
                ParameterItem("modifier", "Modifier", "Layout configuration."),
                ParameterItem("containerColor", "Color", "Underlying surface color."),
                ParameterItem("windowInsets", "WindowInsets", "Edge safe area management.")
            ),
            codeSnippet = """
NavigationBar {
    destinations.forEach { dest ->
        NavigationBarItem(
            selected = currentRoute == dest,
            onClick = { navigateTo(dest) },
            icon = { Icon(dest.icon, dest.label) },
            label = { Text(dest.label) }
        )
    }
}
            """.trimIndent()
        ),
        ComponentDoc(
            id = "NavigationRail",
            name = "Side Navigation Rail",
            category = "Navigation & Structure",
            shortDescription = "Left-aligned side panel designed for medium and landscape viewports.",
            deepDescription = "Provides rapid, thumb-accessible navigation items on wide viewports (from folded phones to mini tablets) by shifting indices from the bottom edge to the side.",
            usageGuidelines = "Recommended primary navigation for Medium-class devices (screen width between 600dp and 840dp).",
            parameters = listOf(
                ParameterItem("header", "Composable", "Optional FAB or avatar anchor placed at the rail top."),
                ParameterItem("containerColor", "Color", "Visual backing color.")
            ),
            codeSnippet = """
NavigationRail(
    header = { FloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, "") } }
) {
    items.forEach { item ->
        NavigationRailItem(
            selected = activeIndex == item.index,
            onClick = { activeIndex = item.index },
            icon = { Icon(item.icon, item.label) },
            label = { Text(item.label) }
        )
    }
}
            """.trimIndent()
        ),
        ComponentDoc(
            id = "NavigationDrawer",
            name = "Modal & Dismissible Drawers",
            category = "Navigation & Structure",
            shortDescription = "Expansive side drawer handling dense navigation paths and sub-categories.",
            deepDescription = "Drawers slide horizontally from the leading end. They manage high-level information columns, settings routes, or switching custom Workspace scopes.",
            usageGuidelines = "Recommended primary structural framework for Expanded-class viewports (tablets, ChromeOS, and desktops wider than 840dp).",
            parameters = listOf(
                ParameterItem("drawerContent", "Composable", "Navigation lists nested inside the sliding sheet."),
                ParameterItem("gesturesEnabled", "Boolean", "Allows sliding open via touch edge swipe.")
            ),
            codeSnippet = """
ModalNavigationDrawer(
    drawerContent = {
        ModalDrawerSheet {
            Text("Settings Header", modifier = Modifier.padding(16.dp))
            NavigationDrawerItem(
                label = { Text("Profile") },
                selected = false,
                onClick = {}
            )
        }
    }
) {
    // Scaffold UI Here
}
            """.trimIndent()
        ),
        ComponentDoc(
            id = "AppBarRow",
            name = "AppBarRow Layout Helper",
            category = "Navigation & Structure",
            shortDescription = "Expressive structural horizontal layout helper for toolbar elements.",
            deepDescription = "An expressive layout utility that balances central labels, navigation arrow buttons, and trailing action lists smoothly inside Material 3 title headers.",
            usageGuidelines = "Use for building brand custom search headers or action-dense view titles.",
            parameters = listOf(
                ParameterItem("title", "Composable", "Horizontal center element."),
                ParameterItem("navigationIcon", "Composable", "The backward or drawer sliding switch on the start side.")
            ),
            codeSnippet = """
AppBarRow(
    title = { Text("Dynamic Settings") },
    navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Menu, "") } },
    actions = { IconButton(onClick = {}) { Icon(Icons.Default.Search, "") } }
)
            """.trimIndent()
        ),
        ComponentDoc(
            id = "Badges",
            name = "Interactive BadgedBox",
            category = "Navigation & Structure",
            shortDescription = "Visual indicator badges displaying notifications, numbers, or alerts.",
            deepDescription = "Badges frame an icon to communicate real-time alert states, such as unread chat counts, calendar schedules, or pending network sync errors.",
            usageGuidelines = "Add overlay bubbles above bottom navigation items or toolbar alerts.",
            parameters = listOf(
                ParameterItem("badge", "Composable", "Small circle dot or text count representation."),
                ParameterItem("content", "Composable", "Target icon being badged.")
            ),
            codeSnippet = """
BadgedBox(
    badge = { Badge { Text("5") } }
) {
    Icon(Icons.Default.Notifications, contentDescription = "Alerts")
}
            """.trimIndent()
        ),
        ComponentDoc(
            id = "Cards",
            name = "Material 3 Multi-Cards",
            category = "Containers & Visuals",
            shortDescription = "Visual structural canvases grouped under Elevated, Filled, or Outlined themes.",
            deepDescription = "Cards act as structural clusters. In M3 Expressive, they utilize dynamic elevation shifts and variable borders to highlight information hierarchy with depth.",
            usageGuidelines = "Use for listing complex records, organizing features, or wrapping dashboard summaries.",
            parameters = listOf(
                ParameterItem("colors", "CardColors", "Adjusts active background values."),
                ParameterItem("elevation", "CardElevation", "Sets structural visual height on Z-axis.")
            ),
            codeSnippet = """
ElevatedCard(
    onClick = { },
    modifier = Modifier.fillMaxWidth().padding(8.dp)
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Visual Heading", style = MaterialTheme.typography.titleMedium)
        Text("Content description text body.")
    }
}
            """.trimIndent()
        ),
        ComponentDoc(
            id = "Carousel",
            name = "Horizontal Carousel Slider",
            category = "Containers & Visuals",
            shortDescription = "Gorgeous, fluid horizontal scrolling carousel that shrinks items dynamically.",
            deepDescription = "An incredible layout helper for multi-item sliding streams, automatically scaling item dimensions as the user swifts them horizontally.",
            usageGuidelines = "Use for display banners, visual gallery collections, or browsing feature collections.",
            parameters = listOf(
                ParameterItem("state", "CarouselState", "Manages page indices and page offsets."),
                ParameterItem("itemSpacing", "Dp", "Horizontal gaps separating items.")
            ),
            codeSnippet = """
val state = rememberCarouselState { itemsCount }
HorizontalMultiBrowseCarousel(
    state = state,
    preferredItemWidth = 180.dp,
    itemSpacing = 8.dp
) { index ->
    Box(modifier = Modifier.fillMaxHeight().background(Color.Gray)) {
        Text("Banner " + index)
    }
}
            """.trimIndent()
        ),
        ComponentDoc(
            id = "BottomSheet",
            name = "Bottom Drawer Sheets",
            category = "Containers & Visuals",
            shortDescription = "An overlay panel sliding up from screen bottoms for immersive quick actions.",
            deepDescription = "BottomSheets keep interactions contextual, rising up to cover primary routes while retaining state focus in the outer viewport.",
            usageGuidelines = "Ideal for complex settings controls, choosing item categories, or sharing link menus.",
            parameters = listOf(
                ParameterItem("onDismissRequest", "() -> Unit", "Triggered when user clicks backdrop or swipes down."),
                ParameterItem("sheetState", "SheetState", "Tracks height animations and peek targets.")
            ),
            codeSnippet = """
val sheetState = rememberModalBottomSheetState()
if (showSheet) {
    ModalBottomSheet(
        onDismissRequest = { showSheet = false },
        sheetState = sheetState
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Extra Details Settings")
        }
    }
}
            """.trimIndent()
        )
    )
}
