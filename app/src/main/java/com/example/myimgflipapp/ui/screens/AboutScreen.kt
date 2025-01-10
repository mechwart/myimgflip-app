package com.example.myimgflipapp.ui.screens

import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun getAppVersionInfo(): String {
    val context = LocalContext.current
    val packageInfo = context.packageManager.getPackageInfo(
        context.packageName,
        PackageManager.GET_META_DATA
    )

    val versionName = packageInfo.versionName ?: "N/A"

    return versionName
}

@Composable
fun AboutScreen() {
    AboutScreenContent()
}

@Composable
fun AboutScreenContent() {
    val versionName = getAppVersionInfo()
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)  {
            Text(text = "Szilagyi Richard")
            Text(text = "Version: $versionName")
        }
    }
}