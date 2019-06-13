package com.esi.dz_now.data

import com.esi.dz_now.R

enum class Categories(val title: Int, var isActivated:Boolean) {
    SPORTS(R.string.sport_category, true),
    POLITICS(R.string.politic_category, true),
    INTERNATIONAL(R.string.international_category, true),
    CULTURE(R.string.culture_category, true),
    SANTE(R.string.health_category, true),
    TECH(R.string.tech_category, true)

}