package imo.puppygit;

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.catpuppyapp.puppygit.ui.theme.PuppyGitAndroidTheme
import com.catpuppyapp.puppygit.ui.theme.Theme
import com.catpuppyapp.puppygit.utils.AppModel
import com.catpuppyapp.puppygit.utils.pref.PrefMan

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { // Override onCreate
        super.onCreate(savedInstanceState)
        setContent {
            val theme = rememberSaveable { mutableStateOf(""+ PrefMan.getInt(applicationContext, PrefMan.Key.theme, Theme.defaultThemeValue)) }
            AppModel.theme = theme
            PuppyGitAndroidTheme(
                theme = theme.value,
            ) {
                MainCompose()
            }
        }
    }
}

@Composable
fun MainCompose() {
    Text("Hello, PuppyGit!")
}
