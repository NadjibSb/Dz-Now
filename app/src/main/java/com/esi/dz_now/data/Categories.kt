package com.esi.dz_now.data

import androidx.core.content.res.TypedArrayUtils.getString
import com.esi.dz_now.R

enum class Categories(val title: Int) {
    SPORTS(R.string.sport_category),
    POLITICS(R.string.politic_category),
    INTERNATIONAL(R.string.international_category),
    CULTURE(R.string.culture_category),
    SANTE(R.string.health_category),
    TECH(R.string.tech_category)


}