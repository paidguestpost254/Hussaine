package com.hussaine.security

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme(
                primary = Color(0xFF72E0B4),
                secondary = Color(0xFF8CB8FF),
                background = Color(0xFF0B1220),
                surface = Color(0xFF121D2E)
            )) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    HussaineApp()
                }
            }
        }
    }
}

@Composable
private fun HussaineApp() {
    var selected by remember { mutableIntStateOf(0) }
    val tabs = listOf("Home", "Learn", "Notes", "Terminal")
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(20.dp)) {
            Text("HUSSAINE", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Security learning companion", color = MaterialTheme.colorScheme.secondary)
            Spacer(Modifier.height(20.dp))
            when (selected) {
                0 -> HomeScreen()
                1 -> LearnScreen()
                2 -> NotesScreen()
                else -> TerminalScreen()
            }
        }
        NavigationBar {
            tabs.forEachIndexed { index, label ->
                NavigationBarItem(
                    selected = selected == index,
                    onClick = { selected = index },
                    icon = { Text(listOf("⌂", "◎", "▤", ">_")[index]) },
                    label = { Text(label) }
                )
            }
        }
    }
}

@Composable
private fun HomeScreen() {
    Text("Welcome", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
    Spacer(Modifier.height(8.dp))
    Text("Learn practical digital-safety concepts in an offline-friendly space.")
    Spacer(Modifier.height(16.dp))
    InfoCard("Privacy first", "This app requests no Android permissions and does not scan nearby devices.")
    InfoCard("Learn safely", "Explore networking basics, account protection, and responsible security.")
    InfoCard("Practice mode", "The terminal is a local simulation. It does not execute system commands.")
}

@Composable
private fun LearnScreen() {
    Text("Learning library", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
    Spacer(Modifier.height(12.dp))
    InfoCard("Network basics", "An IP address identifies a network interface. A router forwards traffic between networks.")
    InfoCard("Wi-Fi safety", "Use WPA2-AES or WPA3, a unique router admin password, and current firmware.")
    InfoCard("Account security", "Use unique passwords, a password manager, and multi-factor authentication.")
    InfoCard("Phishing awareness", "Check the sender and destination carefully. Never enter credentials from unexpected links.")
    InfoCard("Responsible testing", "Only assess systems you own or have clear permission to test.")
}

@Composable
private fun NotesScreen() {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var saved by remember { mutableStateOf("Your notes stay on this screen during this session.") }
    Text("Manual notes", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
    Spacer(Modifier.height(12.dp))
    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Note title") }, modifier = Modifier.fillMaxWidth())
    Spacer(Modifier.height(8.dp))
    OutlinedTextField(value = body, onValueChange = { body = it }, label = { Text("Write a reminder or observation") }, modifier = Modifier.fillMaxWidth(), minLines = 4)
    Spacer(Modifier.height(8.dp))
    Button(onClick = { saved = if (title.isBlank() && body.isBlank()) "Add a title or note first." else "Draft: ${title.ifBlank { "Untitled" }} — ${body}" }) {
        Text("Preview note")
    }
    Spacer(Modifier.height(12.dp))
    InfoCard("Preview", saved)
}

@Composable
private fun TerminalScreen() {
    var command by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("HUSSAINE practice terminal\nType help to see the available demo commands.") }
    Text("Practice terminal", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
    Spacer(Modifier.height(8.dp))
    Text("Simulation only — commands are not sent to Android or the network.", color = MaterialTheme.colorScheme.secondary)
    Spacer(Modifier.height(12.dp))
    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF07101C)), modifier = Modifier.fillMaxWidth()) {
        Text(output, modifier = Modifier.padding(14.dp), color = Color(0xFF9EF0C5))
    }
    Spacer(Modifier.height(12.dp))
    OutlinedTextField(value = command, onValueChange = { command = it }, label = { Text("Enter demo command") }, modifier = Modifier.fillMaxWidth())
    Spacer(Modifier.height(8.dp))
    Button(onClick = {
        output = when (command.trim().lowercase()) {
            "help" -> "Available demo commands: help, about, date, clear"
            "about" -> "HUSSAINE offline security-learning demo."
            "date" -> "This is a simulated terminal; live system data is unavailable."
            "clear" -> "Terminal cleared. Type help for demo commands."
            else -> "Unknown demo command. Type help."
        }
        command = ""
    }) { Text("Run simulation") }
}

@Composable
private fun InfoCard(title: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(description, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
