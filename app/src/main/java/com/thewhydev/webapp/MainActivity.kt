package com.thewhydev.webapp

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsServiceConnection
import androidx.browser.customtabs.CustomTabsSession
import androidx.browser.customtabs.TrustedWebUtils
import androidx.browser.trusted.TrustedWebActivityIntent
import androidx.browser.trusted.TrustedWebActivityIntentBuilder


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchWebSite()
    }

    private var customTabsClient: CustomTabsClient? = null
    private var customTabsSession: CustomTabsSession? = null
    private var customTabsServiceConnection: CustomTabsServiceConnection? = null

    private fun launchWebSite() {
        //showLoader()
        CustomTabsClient.bindCustomTabsService(
            this@MainActivity,
            "com.android.chrome",
            object : CustomTabsServiceConnection() {
                override fun onCustomTabsServiceConnected(
                    componentName: ComponentName,
                    client: CustomTabsClient
                ) {
                    customTabsClient = client;
                    customTabsSession = client.newSession(null);

                    val builder = TrustedWebActivityIntentBuilder(Uri.parse(YOUR_WEBSITE_HERE))

                    val customTabsIntent  = builder.build(customTabsSession!!)
                    customTabsIntent.launchTrustedWebActivity(this@MainActivity)

                }

                override fun onServiceDisconnected(componentName: ComponentName) {
                    // Handle service disconnection
                    //hideLoader()
                }
            })

    }

    override fun onDestroy() {
        super.onDestroy()
        if (customTabsClient != null) {
            customTabsClient = null;
            customTabsSession = null;
            customTabsServiceConnection = null;
        }
    }
}

