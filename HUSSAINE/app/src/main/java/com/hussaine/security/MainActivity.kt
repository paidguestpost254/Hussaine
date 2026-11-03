package com.hussaine.security

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF080D0B)
private val Panel = Color(0xFF111A16)
private val Panel2 = Color(0xFF17231D)
private val Green = Color(0xFF55F29A)
private val Muted = Color(0xFF91A69A)
private val White = Color(0xFFE8F3EC)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HussaineApp() }
    }
}

@Composable
fun HussaineApp() {
    var selected by remember { mutableStateOf("Home") }
    MaterialTheme(
        colorScheme = darkColorScheme(
            background = Bg, surface = Panel, primary = Green,
            onBackground = White, onSurface = White
        )
    ) {
        Scaffold(
            containerColor = Bg,
            bottomBar = {
                NavigationBar(containerColor = Panel, contentColor = Green) {
                    val tabs = listOf(
                        "Home" to Icons.Default.Home,
                        "Terminal" to Icons.Default.Terminal,
                        "Network" to Icons.Default.Wifi,
                        "Learn" to Icons.Default.School
                    )
                    tabs.forEach { (name, icon) ->
                        NavigationBarItem(
                            selected = selected == name,
                            onClick = { selected = name },
                            icon = { Icon(icon, contentDescription = name) },
                            label = { Text(name, fontSize = 11.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Green,
                                selectedTextColor = Green,
                                indicatorColor = Panel2,
                                unselectedIconColor = Muted,
                                unselectedTextColor = Muted
                            )
                        )
                    }
                }
            }
        ) { padding ->
            Box(Modifier.fillMaxSize().padding(padding)) {
                when (selected) {
                    "Home" -> HomeScreen(
                        onTerminal = { selected = "Terminal" },
                        onNetwork = { selected = "Network" },
                        onLearn = { selected = "Learn" }
                    )
                    "Terminal" -> TerminalScreen()
                    "Network" -> NetworkScreen()
                    else -> LearnScreen()
                }
            }
        }
    }
}

@Composable
private fun Page(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier.fillMaxSize().background(Bg).padding(horizontal = 18.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = content
    )
}

@Composable
private fun Header(kicker: String, title: String, subtitle: String? = null) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(kicker.uppercase(), color = Green, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        Text(title, color = White, fontSize = 27.sp, fontWeight = FontWeight.Bold)
        if (subtitle != null) Text(subtitle, color = Muted, fontSize = 13.sp)
    }
}

@Composable
private fun PanelCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(18.dp)).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = content
    )
}

@Composable
private fun HomeScreen(onTerminal: () -> Unit, onNetwork: () -> Unit, onLearn: () -> Unit) {
    Page {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Column {
                Text("HUSSAINE", color = Green, fontWeight = FontWeight.Black, fontSize = 14.sp, letterSpacing = 3.sp)
                Text("MOBILE SECURITY TOOLKIT", color = Muted, fontSize = 9.sp, letterSpacing = 1.5.sp)
            }
            Box(Modifier.size(38.dp).background(Panel2, CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Shield, contentDescription = null, tint = Green)
            }
        }
        Header("Your workspace", "Security console", "A clean space to learn and review device security.")
        PanelCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(10.dp).background(Green, CircleShape))
                Text("SYSTEM READY", color = Green, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.weight(1f))
                Text("LOCAL MODE", color = Muted, fontSize = 10.sp)
            }
            Text("Welcome back.", color = White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("HUSSAINE runs without account sign-in, network access, or runtime permissions in this starter build.", color = Muted, fontSize = 13.sp, lineHeight = 19.sp)
        }
        Text("QUICK ACCESS", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            QuickTile("Terminal", "Safe demo shell", Icons.Default.Terminal, Modifier.weight(1f), onTerminal)
            QuickTile("Network", "Manual notes", Icons.Default.Wifi, Modifier.weight(1f), onNetwork)
        }
        QuickTile("Security learning", "Guides and checklists", Icons.Default.School, Modifier.fillMaxWidth(), onLearn)
        Spacer(Modifier.weight(1f))
        Text("HUSSAINE  •  VERSION 1.0.0", color = Color(0xFF53645A), fontSize = 10.sp)
    }
}

@Composable
private fun QuickTile(title: String, desc: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier.background(Panel, RoundedCornerShape(16.dp)).clickable(onClick = onClick).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(icon, contentDescription = null, tint = Green, modifier = Modifier.size(22.dp))
        Text(title, color = White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(desc, color = Muted, fontSize = 11.sp)
    }
}

@Composable
private fun TerminalScreen() {
    var command by remember { mutableStateOf("") }
    var output by remember { mutableStateOf(listOf("HUSSAINE safe demo terminal", "Type help to see available demo commands.", "")) }
    val examples = mapOf(
        "help" to "Available commands: help, about, status, clear",
        "about" to "HUSSAINE 1.0 — offline security-learning companion.",
        "status" to "App status: READY\\nMode: LOCAL DEMO\\nPermissions: NONE"
    )
    Page {
        Header("Local tools", "Terminal", "A simulated terminal for safe, offline demonstrations.")
        Column(Modifier.fillMaxWidth().weight(1f).background(Color(0xFF050907), RoundedCornerShape(16.dp)).padding(14.dp)) {
            LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                items(output) { line ->
                    Text(line, color = if (line.startsWith("$")) Green else Muted, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("$", color = Green, fontFamily = FontFamily.Monospace)
                Spacer(Modifier.width(8.dp))
                BasicTextField(
                    value = command, onValueChange = { command = it },
                    modifier = Modifier.weight(1f),
                    textStyle = androidx.compose.ui.text.TextStyle(color = White, fontFamily = FontFamily.Monospace, fontSize = 13.sp),
                    singleLine = true,
                    decorationBox = { inner -> Box { if (command.isEmpty()) Text("enter demo command", color = Color.Gray, fontSize = 12.sp); inner() } }
                )
                TextButton(onClick = {
                    val c = command.trim()
                    val result = when (c) {
                        "clear" -> null
                        else -> examples[c] ?: "Command not available in safe demo mode."
                    }
                    output = if (c == "clear") emptyList() else output + listOf("$ $c", result ?: "")
                    command = ""
                }) { Text("RUN", color = Green, fontWeight = FontWeight.Bold) }
            }
        }
        Text("This screen does not execute operating-system or network commands.", color = Muted, fontSize = 11.sp)
    }
}

@Composable
private fun NetworkScreen() {
    var label by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    Page {
        Header("Network workspace", "Network notes", "Record details for a network you own or are allowed to document.")
        PanelCard {
            Text("MANUAL NETWORK RECORD", color = Green, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Text("Network label", color = Muted, fontSize = 12.sp)
            OutlinedTextField(
                value = label, onValueChange = { label = it }, modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g. Home Wi-Fi") }, singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Green, unfocusedBorderColor = Muted, focusedTextColor = White, unfocusedTextColor = White)
            )
            Text("Notes", color = Muted, fontSize = 12.sp)
            OutlinedTextField(
                value = notes, onValueChange = { notes = it }, modifier = Modifier.fillMaxWidth().height(110.dp),
                placeholder = { Text("Router model, date checked, observations...") },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Green, unfocusedBorderColor = Muted, focusedTextColor = White, unfocusedTextColor = White)
            )
            Text("Saved only while this screen remains open in this starter version.", color = Muted, fontSize = 11.sp)
        }
        PanelCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, tint = Green, contentDescription = null)
                Text("No Wi-Fi scan is performed", color = White, fontWeight = FontWeight.Bold)
            }
            Text("Android Wi-Fi discovery can require sensitive permissions. HUSSAINE avoids requesting them and uses manual notes instead.", color = Muted, fontSize = 12.sp, lineHeight = 18.sp)
        }
    }
}

@Composable
private fun LearnScreen() {
    val lessons = listOf(
        "Strong passwords" to "Use a unique passphrase for every important account. A password manager can help you keep track.",
        "Two-factor authentication" to "Enable a second sign-in factor on accounts that support it. Keep recovery options up to date.",
        "Safe Wi-Fi habits" to "Use trusted networks for sensitive activity. Avoid sharing router passwords publicly.",
        "Updates and backups" to "Install system and app updates, and keep a recent backup of important files.",
        "Phishing awareness" to "Check the sender and destination before opening unexpected links or attachments."
    )
    Page {
        Header("Knowledge base", "Security learning", "Short, practical lessons for everyday digital safety.")
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(lessons) { lesson ->
                PanelCard {
                    Text(lesson.first, color = Green, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(lesson.second, color = Muted, fontSize = 13.sp, lineHeight = 19.sp)
                }
            }
        }
        Text("Educational content only • No scanning or exploitation tools", color = Muted, fontSize = 10.sp)
    }
}
