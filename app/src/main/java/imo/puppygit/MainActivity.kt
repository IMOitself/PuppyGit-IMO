package imo.puppygit

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class MainActivity : ComponentActivity() {
    companion object {
        var showBottomBar = true
        var appNameRetrieve: String? = null
        var apkFileUri: Uri? = null

        fun goToMainActivity(context: Context){
            val intent = Intent(context , com.catpuppyapp.puppygit.play.pro.MainActivity::class.java)
            context.startActivity(intent)
        }
        fun continueInstallApk(context: Context) {
            if (!context.packageManager.canRequestPackageInstalls()) {
                Toast.makeText(
                    context,
                    "Please grant permission to install from unknown sources in settings, then try installing again.",
                    Toast.LENGTH_LONG
                ).show()

                val settingsIntent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).apply {
                    data = Uri.parse("package:${context.packageName}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                try {
                    context.startActivity(settingsIntent)
                } catch (_: Exception) {
                    Toast.makeText(context, "Could not open settings to grant permission.", Toast.LENGTH_SHORT).show()
                }
                return
            }

            // Permission already granted
            val installIntent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(apkFileUri, "application/vnd.android.package-archive")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            context.startActivity(installIntent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        apkFileUri = intent.data
        if (apkFileUri != null) appNameRetrieve = getAppNameFromApkUri(this, apkFileUri)
        if (apkFileUri == null) showBottomBar = false

        setContent {
            MainScreen()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MainScreen()
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
                            } catch (_: IOException) {
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

            val appName = appInfo.loadLabel(pm)
            return appName.toString()
        } catch (_: Exception) { }
        finally {
            if (isTempFileUsed && tempFile != null && tempFile.exists()) {
                tempFile.delete()
            }
        }
        return null
    }
}
