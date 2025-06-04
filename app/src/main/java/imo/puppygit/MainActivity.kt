package imo.puppygit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { // Override onCreate
        super.onCreate(savedInstanceState)
        setContent {
            TranslatedScreen()
        }
    }

    @Composable
    fun TranslatedScreen() {
        Scaffold(topBar = { TopBar() })
        { paddingValues ->
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BranchSection()
                    Spacer(Modifier.weight(1f))
                    ActionSection()
                }
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun TopBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(8.dp)
                .padding(top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // menu_icon
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.defaultButtonBg().size(32.dp)) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Filled.Folder, contentDescription = "App Icon", modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(8.dp))

            FlowRow(modifier = Modifier.weight(1.0f)) {
                Text(
                    text = "IMOitself / ",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
                Text(
                    text = "hunter_exam",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            // search_icon
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.defaultButtonBg().size(32.dp)) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
            Spacer(modifier = Modifier.width(8.dp))

            // settings_icon
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.defaultButtonBg().size(32.dp)) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
            }
            Spacer(modifier = Modifier.width(8.dp))

            // profile_icon
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(32.dp)) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile")
            }
        }
    }

    @Composable
    fun BranchSection() {
        Row(
            modifier = Modifier
                .height(32.dp)
                .defaultButtonBg(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(8.dp))
            Icon(imageVector = Icons.Filled.AccountTree, contentDescription = "Branch", modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "master", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Dropdown", modifier = Modifier.size(16.dp))

            Spacer(Modifier.width(8.dp))
        }
    }

    @Composable
    fun ActionSection() {
        Row(
            modifier = Modifier
                .height(32.dp)
                .highlightButtonBg(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(8.dp))
            Icon(imageVector = Icons.Filled.Code, contentDescription = "Command", modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Actions", style = MaterialTheme.typography.bodySmall,)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Dropdown", modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
        }
        Spacer(Modifier.width(8.dp))
        IconButton(onClick = { /* TODO */ }, modifier = Modifier.defaultButtonBg().size(32.dp)) {
            Icon(imageVector = Icons.Filled.MoreHoriz, contentDescription = "More Actions", modifier = Modifier.fillMaxSize())
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MaterialTheme(
            //colorScheme = if (isSystemInDarkTheme()) DarkColorPalette else lightColorScheme()
            colorScheme = DarkColorPalette
        ) {
            TranslatedScreen()
        }
    }

    private val DarkColorPalette = darkColorScheme(
    primary = Color(0xFF010409),
    primaryContainer = Color(0xFF0D1117),
    secondaryContainer = Color(0xFF999999),
    tertiary = Color(0xFF29903B)
    )

    val cornerRadius = 4.dp

    private fun Modifier.defaultButtonBg(): Modifier = this.then(
        Modifier
            .background(
                color = Color(0x00000000),
                shape = RoundedCornerShape(cornerRadius)
            )
            .border(
                border = BorderStroke(0.25.dp, Color(0xFF9198A1)),
                shape = RoundedCornerShape(cornerRadius)
            )
    )

    @Composable
    private fun Modifier.highlightButtonBg(): Modifier = this.then(
            Modifier
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(cornerRadius)
                )
                .border(
                    border = BorderStroke(0.dp, Color(0x00000000)),
                    shape = RoundedCornerShape(cornerRadius)
                )
        )
}
