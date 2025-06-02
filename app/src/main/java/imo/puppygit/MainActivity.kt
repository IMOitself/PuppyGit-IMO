package imo.puppygit

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.catpuppyapp.puppygit.play.pro.R
import com.catpuppyapp.puppygit.screen.shared.IntentHandler.intent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { // Override onCreate
        super.onCreate(savedInstanceState)
        setContent {
            GitHubLikeUI()
        }
    }
}

val DarkBackground = Color(0xFF202225)
val DarkSurface = Color(0xFF2F3136)
val TextColor = Color(0xFFDCDDDE)
val DarkPrimary = Color(0xFF5865F2) // A common Discord-like blue

@Composable
fun GitHubLikeUI() {
    // State for selected tab
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Changes", "History")

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().background(DarkBackground)) {
                // Top menu bar (File, Edit, View, Repository)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Share",
                        tint = TextColor,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Text("Commands", color = TextColor, fontSize = 12.sp, modifier = Modifier.padding(8.dp))
                    Text("Folder", color = TextColor, fontSize = 12.sp, modifier = Modifier.padding(8.dp))
                    Text("Settings", color = TextColor, fontSize = 12.sp, modifier = Modifier.padding(8.dp))
                }

                val context = LocalContext.current
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkSurface)
                        .padding(8.dp)
                        .clickable {
                            val intent = Intent()
                            val targetPackageName = "com.catpuppyapp.puppygit.play.pro"
                            val targetActivityName = "com.catpuppyapp.puppygit.play.pro.MainActivity"
                            intent.component = ComponentName(targetPackageName, targetActivityName)

                            try {
                                context.startActivity(intent)
                            } catch (_: Exception) {}
                        },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.git_folder),
                        contentDescription = "Repository",
                        modifier = Modifier.size(32.dp)
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                            .background(DarkSurface)
                    ){
                        Text("Current Repository", color = TextColor, fontSize = 12.sp)
                        Text("ExampleRepo", color = TextColor, fontSize = 16.sp)
                    }

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Reveal",
                        modifier = Modifier.size(24.dp)
                    )

                }
                HorizontalDivider(color = Color.Black)
            }
        },
        containerColor = DarkBackground // Set overall background color
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(DarkBackground)
            ){
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reveal",
                    modifier = Modifier.size(24.dp)
                )
                Text("FETCH Last fetched was 1 minutes ago", color = TextColor, fontSize = 12.sp, modifier = Modifier.padding(start = 8.dp))
            }
            HorizontalDivider(color = Color.Black)

            // Tabs: Changes and History
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkBackground),
                containerColor = DarkBackground,
                contentColor = TextColor,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = DarkPrimary // Highlight color for the selected tab
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title, color = TextColor) },
                        selectedContentColor = DarkPrimary,
                        unselectedContentColor = TextColor
                    )
                }
            }

            // Content area below tabs
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(DarkSurface)
                    .padding(16.dp)
            ) {
                // Large empty area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkBackground, MaterialTheme.shapes.medium)
                        .border(1.dp, Color.Gray.copy(alpha = 0.3f), MaterialTheme.shapes.medium)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGitHubLikeUI() {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = DarkPrimary,
            onPrimary = Color.White,
            background = DarkBackground,
            surface = DarkSurface,
            onSurface = TextColor
        )
    ) {
        GitHubLikeUI()
    }
}
