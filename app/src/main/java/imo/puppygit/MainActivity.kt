package imo.puppygit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CallSplit
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { // Override onCreate
        super.onCreate(savedInstanceState)
        setContent {
            GithubRepoScreen()
        }
    }

    @Composable
    fun GithubRepoScreen() {
        Scaffold(
            topBar = { TopBar() },
            // FloatingActionButton for potential future use, not directly in image but common
            // floatingActionButton = {
            //     FloatingActionButton(onClick = { /*TODO*/ }) {
            //         Icon(Icons.Filled.Add, contentDescription = "Add")
            //     }
            // }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                RepoActionsBar()
                LastCommitInfo()
                Divider(color = Color.LightGray, thickness = 1.dp)
                FileList() // This would ideally be a LazyColumn for scrollable content
                AddReadmeSection()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar() {
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Menu, // Placeholder for GitHub logo or similar
                        contentDescription = "GitHub Logo",
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("IMOitself / hunterexam", fontWeight = FontWeight.Bold)
                }
            },
            navigationIcon = {
                IconButton(onClick = { /* TODO: Handle navigation */ }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
                }
            },
            actions = {
                IconButton(onClick = { /* TODO: Handle search */ }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
                IconButton(onClick = { /* TODO: Handle notifications/issues */ }) {
                    Icon(Icons.Filled.Notifications, contentDescription = "Issues/Notifications") // Placeholder
                }
                // Placeholder for Profile Image
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "User Profile",
                    modifier = Modifier
                        .size(32.dp)
                    // .clip(CircleShape) // Icon is already circular, clipping might not be necessary
                    // but can be kept if a background color is applied to the icon.
                )
                Spacer(modifier = Modifier.width(8.dp))
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF24292E) , // Dark GitHub header color
                titleContentColor = Color.White,
                actionIconContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )
    }

    @Composable
    fun RepoActionsBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Branch Selector
            OutlinedButton(onClick = { /* TODO */ }, shape = RoundedCornerShape(8.dp)) {
                Icon(Icons.Filled.CallSplit, contentDescription = "Branch", tint = Color.Gray) // Git branch icon
                Spacer(modifier = Modifier.width(4.dp))
                Text("master", color = Color.Black)
                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown", tint = Color.Gray)
            }

            Row {
                Button(
                    onClick = { /* TODO */ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2EA44F)) // GitHub green
                ) {
                    Text("Code", color = Color.White)
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "More Code Options", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { /* TODO */ }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "More Options", tint = Color.Gray)
                }
            }
        }
    }

    @Composable
    fun LastCommitInfo() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFFF6F8FA), shape = RoundedCornerShape(6.dp)) // Light gray background
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder for Avatar
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "User Profile",
                modifier = Modifier
                    .size(32.dp)
                // .clip(CircleShape) // Icon is already circular, clipping might not be necessary
                // but can be kept if a background color is applied to the icon.
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("IMOitself", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("chore: format code style", fontSize = 14.sp, color = Color.DarkGray)
                Text("b5c7ab5", fontSize = 12.sp, color = Color.Gray)
            }
            Text("4 months ago", fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            // Placeholder for action icons (e.g., comment, history)
            Icon(Icons.Filled.ChatBubbleOutline, contentDescription = "Comment", tint = Color.Gray, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.Filled.History, contentDescription = "History", tint = Color.Gray, modifier = Modifier.size(20.dp))
        }
    }

    @Composable
    fun FileList() {
        // For a real app, this would be a LazyColumn
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            FileListItem(icon = Icons.Filled.Folder, name = "assets/fonts", lastModified = "5 months ago")
            FileListItem(icon = Icons.Filled.Folder, name = "java/imo/t", lastModified = "4 months ago")
            FileListItem(icon = Icons.Filled.Folder, name = "res", lastModified = "4 months ago")
            FileListItem(icon = Icons.Filled.Description, name = "AndroidManifest.xml", lastModified = "5 months ago") // File icon

            // Tab Row for README
            TabRow(selectedTabIndex = 0, containerColor = Color.White, contentColor = Color.Black) {
                Tab(
                    selected = true,
                    onClick = { /* TODO */ },
                    text = { Text("README", fontWeight = FontWeight.Bold) },
                    icon = { Icon(Icons.Filled.Book, contentDescription = "Readme") } // Book icon for readme
                )
                // Add other tabs if needed
            }
            Divider(color = Color.LightGray)
        }
    }

    @Composable
    fun FileListItem(icon: ImageVector, name: String, lastModified: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = name, tint = Color.Gray, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(name, modifier = Modifier.weight(1f), fontSize = 14.sp)
            Text(lastModified, fontSize = 12.sp, color = Color.Gray)
        }
    }

    @Composable
    fun AddReadmeSection() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Filled.Book, // Book icon
                contentDescription = "Add Readme Icon",
                modifier = Modifier.size(48.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Add a README",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Help people interested in this repository understand your project by adding a README.",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { /* TODO */ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2EA44F)) // GitHub green
            ) {
                Text("Add a README", color = Color.White)
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MaterialTheme { // Ensure you have a MaterialTheme wrapping your preview
            GithubRepoScreen()
        }
    }
}
