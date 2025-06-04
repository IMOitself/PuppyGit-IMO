package imo.puppygit

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
        fun goToMainActivity(context: Context?){
            val intent = Intent(context , com.catpuppyapp.puppygit.play.pro.MainActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apkFileUri = intent.data
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
}
