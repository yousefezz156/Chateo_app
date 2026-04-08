package com.example.chateo_app.verfication.domain

import android.content.Context
import android.telephony.TelephonyManager
import android.util.Log
import com.example.chateo_app.verfication.domain.utils.countryFlag
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.Locale

class VerificationUseCase {

     fun numList():List<Numbers>{
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


}