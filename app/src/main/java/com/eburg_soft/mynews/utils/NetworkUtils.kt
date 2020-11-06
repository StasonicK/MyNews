package com.eburg_soft.mynews.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import timber.log.Timber
import java.math.BigInteger
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Enumeration

object NetworkUtils {

    //Check the internet connection.
    fun detectNetwork(context: Context): String? {
        var isWifiConnection = false
        var isMobileConnection = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.allNetworkInfo
        for (netInfo in networkInfo) {
            if (netInfo.typeName.equals("WIFI", ignoreCase = true)) if (netInfo.isConnected) isWifiConnection = true
            if (netInfo.typeName.equals("MOBILE", ignoreCase = true)) if (netInfo.isConnected) isMobileConnection =
                true
        }

        var ipAddress: String? = null
        if (isWifiConnection) {
            ipAddress = getDeviceIpWiFiData(context)
        }

        if (isMobileConnection) {
            ipAddress = getDeviceIpMobileData()
        }
        return ipAddress
    }

    private fun getDeviceIpMobileData(): String? {
        try {
            val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val networkinterface: NetworkInterface = en.nextElement()
                val enumIpAddr: Enumeration<InetAddress> = networkinterface.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress: InetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress) {
                        val mobileIpAddress = inetAddress.hostAddress.toString()
                        Timber.d("mobileIpAddress: $mobileIpAddress")
                        return mobileIpAddress
                    }
                }
            }
        } catch (ex: Exception) {
            Timber.d(ex.toString())
        }
        return null
    }

    private fun getDeviceIpWiFiData(context: Context): String {
        val wm = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?
        val wifiinfo: WifiInfo? = wm?.connectionInfo
        val myIPAddress: ByteArray = BigInteger.valueOf(wifiinfo?.ipAddress!!.toLong()).toByteArray()
        // reverse the byte array before conversion
        myIPAddress.reverse()
        val myInetIP = InetAddress.getByAddress(myIPAddress)
        val wifiIpAddress = myInetIP.hostAddress
        Timber.d("wifiIpAddress: $wifiIpAddress")
        return wifiIpAddress
    }
}