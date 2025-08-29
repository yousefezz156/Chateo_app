package com.example.chateo_app.contact.mockData

import com.example.chateo_app.R

class ContactCard {

    fun getContactCard(): List<MockDataContact> {

        val mockContacts = listOf(
            MockDataContact(R.drawable.ph2, "Yousef", "Ezz", true),          // Original
            MockDataContact(R.drawable.ph1, "Aisha", "Khan", false),
            MockDataContact(R.drawable.ph3, "Omar", "Hassan", true),
            MockDataContact(R.drawable.ph4, "Layla", "Ali", true),
            MockDataContact(R.drawable.ph1, "Mohammed", "Salah", false),
            MockDataContact(R.drawable.ph2, "Fatima", "Ahmed", true),
            MockDataContact(R.drawable.ph3, "Ali", "Ibrahim", false),
            MockDataContact(R.drawable.ph4, "Noor", "Hussein", true),
            MockDataContact(R.drawable.ph1, "Zainab", "Farid", true),
            MockDataContact(R.drawable.ph2, "Khaled", "Mansour", false),
            MockDataContact(R.drawable.ph3, "Sara", "Jamal", true),
            MockDataContact(R.drawable.ph4, "Hassan", "Tariq", false),
            MockDataContact(R.drawable.ph1, "Mariam", "Adel", true),
            MockDataContact(R.drawable.ph2,"Ahmed", "Mustafa", true),
            MockDataContact(R.drawable.ph3, "Rania", "Said", false)
        )

        return mockContacts
    }
}