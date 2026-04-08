package com.example.chateo_app.verfication.data

import android.content.Context
import android.telephony.TelephonyManager
import android.util.Log
import com.google.i18n.phonenumbers.PhoneNumberUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class GetNumCode(val context: Context){
    suspend fun getNumCodeByNetwork(): String = withContext(Dispatchers.IO) {

        val tm = context.getSystemService(TelephonyManager::class.java).networkCountryIso
        Log.d("TAG", "getDeviceNetworkCountry: $tm")

        val phoneNum = PhoneNumberUtil.getInstance()
        val countryCode = phoneNum.getCountryCodeForRegion(tm.toString().uppercase())

        Log.d("TAG", "getDeviceNetworkCountry: $countryCode")

        return@withContext "+$countryCode"
    }
}