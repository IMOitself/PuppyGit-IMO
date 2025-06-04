package imo.puppygit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.ArrowDropDown
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
        Scaffold(
            topBar = { TopBar() },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BranchSection()
                    Spacer(Modifier.weight(1f))
                    ActionSection()
                    Spacer(modifier = Modifier.width(8.dp)) // I JUST DONT KNOW WHY IT DOESNT GIVE A PADDING!!!
                }
            }
        }
    }

    @Composable
    fun TopBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // menu_icon
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(32.dp)) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
            }
            Spacer(modifier = Modifier.width(8.dp))

            // app_icon
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(32.dp)) {
                Icon(imageVector = Icons.Filled.Folder, contentDescription = "App Icon")
            }
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .weight(1.0f)
            ) {
                Text(
                    text = "IMOitself /",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
                Text(
                    text = "hunterexam",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            // search_icon
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(32.dp)) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
            Spacer(modifier = Modifier.width(8.dp))

            // settings_icon
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(32.dp)) {
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
                .padding(8.dp)
                .height(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(16.dp)) {
                Icon(imageVector = Icons.Filled.AccountTree, contentDescription = "Branch Icon Left", modifier = Modifier.fillMaxSize())
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "master",
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(16.dp)) {
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Branch Icon Right", modifier = Modifier.fillMaxSize())
            }
        }
    }

    @Composable
    fun ActionSection() {
        Row(
            modifier = Modifier
                .height(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Actions",
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(16.dp)) {
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Code Action Icon", modifier = Modifier.fillMaxSize())
            }
            Spacer(Modifier.width(8.dp))
        }
        Spacer(Modifier.width(8.dp))
        IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(32.dp)) {
            Icon(imageVector = Icons.Filled.MoreHoriz, contentDescription = "More Actions", modifier = Modifier.fillMaxSize())
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MaterialTheme(
            //colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
            colorScheme = darkColorScheme()
        ) {
            TranslatedScreen()
        }
    }
}
