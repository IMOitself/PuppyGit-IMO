package imo.puppygit

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Download
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class MainActivity : ComponentActivity() {
    private val buttonBorderColor = Color(0xFF9198A1)
    private val buttonBorderRadius = 4.dp
    private val darkColorPalette = darkColorScheme(
        primary = Color(0xFF010409),
        primaryContainer = Color(0xFF0D1117),
        secondaryContainer = Color(0xFF999999),
        tertiary = Color(0xFF29903B)
    )
    private var showBottomBar = true
    private var appNameRetrieve: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apkFileUri = intent.data
        if (apkFileUri != null) appNameRetrieve = getAppNameFromApkUri(this, apkFileUri)
        if (apkFileUri == null) showBottomBar = false

        // TODO: separate UI to code
        setContent {
            MaterialTheme(
                colorScheme = darkColorPalette
            ) {
                MainScreen()
            }
        }
    }

    private fun goToMainActivity(){
        val intent = Intent(this, com.catpuppyapp.puppygit.play.pro.MainActivity::class.java)
        startActivity(intent)
    }

    fun getAppNameFromApkUri(context: Context, apkUri: Uri?): String? {
        if (apkUri == null) return null

        var apkPath: String? = ""
        var tempFile: File? = null
        var isTempFileUsed = false

        try {
            val scheme = apkUri.scheme

            if ("file" == scheme) {
                apkPath = apkUri.path
                if (apkPath == null) return null
            } else
            if ("content" == scheme) {
                var inputStream: InputStream? = null
                try {
                    inputStream = context.contentResolver.openInputStream(apkUri)
                    if (inputStream == null) return null

                    tempFile = File.createTempFile("apk_info_", ".apk", context.cacheDir)
                    FileOutputStream(tempFile).use { outputStream ->
                        val buffer = ByteArray(4096)
                        var bytesRead: Int
                        while ((inputStream.read(buffer).also { bytesRead = it }) != -1) {
                            outputStream.write(buffer, 0, bytesRead)
                        }
                    }
                    apkPath = tempFile.absolutePath
                    isTempFileUsed = true
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close()
                        } catch (ignored: IOException) {
                            // Intentionally ignored as per requirements
                        }
                    }
                }
            }
            else return null

            if (apkPath == null) return null

            val pm = context.packageManager
            val packageInfo = pm.getPackageArchiveInfo(apkPath, 0) ?: return null + "packageInfo null"

            val appInfo = packageInfo.applicationInfo ?: return null + "appInfo null"

            appInfo.sourceDir = apkPath
            appInfo.publicSourceDir = apkPath

            val appName = appInfo.loadLabel(pm) ?: return null
            return appName.toString()
        } catch (e: Exception) { }
        finally {
            if (isTempFileUsed && tempFile != null && tempFile.exists()) {
                tempFile.delete()
            }
        }
        return null
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MaterialTheme(
            colorScheme = darkColorPalette
        ) {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen() {
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { if (showBottomBar) BottomBar() }
        )
        { paddingValues ->
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .padding(paddingValues)
                    .fillMaxSize()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BranchSection()
                    Spacer(Modifier.weight(1f))
                    ActionSection()
                }
                Spacer(modifier = Modifier.height(64.dp))
                Text(
                    text = "Show Changed files here\n\n¯\\_(ツ)_/¯",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
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
            IconButton(onClick = { goToMainActivity() }, modifier = Modifier
                .defaultButtonBg()
                .size(32.dp)) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Filled.Folder, contentDescription = "App Icon", modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(8.dp))

            FlowRow(modifier = Modifier.weight(1.0f)) {
                Text(
                    text = "ParentFolderPath / ",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
                Text(
                    text = "RepositoryLabel",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            // search_icon
            IconButton(onClick = { /* TODO */ }, modifier = Modifier
                .defaultButtonBg()
                .size(32.dp)) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
            Spacer(modifier = Modifier.width(8.dp))

            // settings_icon
            IconButton(onClick = { /* TODO */ }, modifier = Modifier
                .defaultButtonBg()
                .size(32.dp)) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
            }
            Spacer(modifier = Modifier.width(8.dp))

            // profile_icon
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(32.dp)) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile")
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun BottomBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(8.dp)
                .padding(top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Filled.Android, contentDescription = "Apk", modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "$appNameRetrieve",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                modifier = Modifier.weight(1.0f)
            )
            Row(
                modifier = Modifier
                    .height(32.dp)
                    .highlightButtonBg(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.Download,
                    contentDescription = "Install",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Continue Install Apk", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.width(8.dp))
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
            Text(text = "Actions", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Dropdown", modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
        }
        Spacer(Modifier.width(8.dp))
        IconButton(onClick = { /* TODO */ }, modifier = Modifier
            .defaultButtonBg()
            .size(32.dp)) {
            Icon(imageVector = Icons.Filled.MoreHoriz, contentDescription = "More Actions", modifier = Modifier.fillMaxSize())
        }
    }

    @Composable
    private fun Modifier.highlightButtonBg(): Modifier = this.then(
        Modifier
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(buttonBorderRadius)
            )
            .border(
                border = BorderStroke(0.dp, Color(0x00000000)),
                shape = RoundedCornerShape(buttonBorderRadius)
            )
    )
    private fun Modifier.defaultButtonBg(): Modifier = this.then(
        Modifier
            .background(
                color = Color(0x00000000),
                shape = RoundedCornerShape(buttonBorderRadius)
            )
            .border(
                border = BorderStroke(0.25.dp, buttonBorderColor),
                shape = RoundedCornerShape(buttonBorderRadius)
            )
    )
}
