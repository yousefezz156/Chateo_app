package com.example.chateo_app.DataNumbers

import android.content.Context
import android.telephony.TelephonyManager
import android.util.Log
import androidx.compose.ui.text.toUpperCase
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.Locale

open class NumbersList {
    open fun numList():List<Numbers>{
        val NumberLists = mutableListOf<Numbers>()
        val phoneNum = PhoneNumberUtil.getInstance()

        val locale = Locale.getISOCountries()
        for(i in locale){

                val countryCodeNum = phoneNum.getCountryCodeForRegion(i)
            Log.d("TAG", "numList: $countryCodeNum")
            if(countryCodeNum!=0) {
                val countryCode = Locale("", i).country
                val flag = countryFlag(countryCode)
                NumberLists.add(Numbers(flag, "+$countryCodeNum"))
            }
        }

//        NumberLists.add(Numbers("\uD83C\uDDF5\uD83C\uDDF8", "+970"))
//        NumberLists.add(Numbers("\uD83C\uDDEA\uD83C\uDDEC", "+20"))
//        NumberLists.add(Numbers("\uD83C\uDDFA\uD83C\uDDF8", "+1"))
//        NumberLists.add(Numbers("\uD83C\uDDEC\uD83C\uDDE7", "+44"))
//
        return NumberLists
    }

    fun countryFlag(country:String):String{
        return country
            .uppercase()
            .map { char ->
                Character.toChars(0x1F1E6 + char.code - 'A'.code)
            }
            .joinToString("") { String(it) }

    }

    fun getDeviceNetworkCountry(context: Context):String{

        val tm = context.getSystemService(TelephonyManager::class.java).networkCountryIso
        Log.d("TAG", "getDeviceNetworkCountry: $tm")
        val phoneNum = PhoneNumberUtil.getInstance()

        val countryCode = phoneNum.getCountryCodeForRegion(tm.toString().uppercase() )
        Log.d("TAG", "getDeviceNetworkCountry: $countryCode")

        return "+$countryCode"

    }
}