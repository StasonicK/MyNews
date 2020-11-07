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
    fun detectNetwork(context: Context): Pair<String?, Boolean>? {
        var isWifiConnection = false
        var isMobileConnection = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.allNetworkInfo
        for (netInfo in networkInfo) {
            if (netInfo.typeName.equals("WIFI", ignoreCase = true)) if (netInfo.isConnected) isWifiConnection = true
            if (netInfo.typeName.equals("MOBILE", ignoreCase = true)) if (netInfo.isConnected) isMobileConnection =
                true
        }

        var ipAddressAndIsIPv4: Pair<String?, Boolean>? = null
        if (isWifiConnection) {
            ipAddressAndIsIPv4 = getDeviceIpWiFiData(context) to true
        }

        if (isMobileConnection) {
            ipAddressAndIsIPv4 = getDeviceIpMobileData() to false
        }
        return ipAddressAndIsIPv4
    }

    private fun getDeviceIpMobileData(): String? {
        try {
            val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val networkinterface: NetworkInterface = en.nextElement()
                Timber.d("networkinterface: $networkinterface")
                val enumIpAddr: Enumeration<InetAddress> = networkinterface.inetAddresses
                Timber.d("enumIpAddr: $enumIpAddr")
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress: InetAddress = enumIpAddr.nextElement()
                    Timber.d("inetAddress: $inetAddress")
                    if (!inetAddress.isLoopbackAddress) {
                        val mobileIpAddressWithName = inetAddress.hostAddress.toString()
                        val mobileIpAddress =
                            mobileIpAddressWithName.substring(0, mobileIpAddressWithName.indexOf("%") - 1)
//                        val mobileIpAddress = inetAddress.address.toString()
                        Timber.d("mobileIpAddress: $mobileIpAddress")
                        val mobileIpAddressReversed = StringBuilder(mobileIpAddress).reverse().toString()
                        Timber.d("mobileIpAddressReversed: $mobileIpAddressReversed")
                        return mobileIpAddressWithName
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