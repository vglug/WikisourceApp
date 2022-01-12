package org.vglug.wikisourceapp.data

import org.vglug.wikisourceapp.data.model.WikiLang

object LangUtils {

    fun getWikiLangList(): List<WikiLang> {
        val list = ArrayList<WikiLang>()
        list.add(WikiLang("ta", "Tamil"))
        list.add(WikiLang("bn", "Bengali"))
        list.add(WikiLang("en", "English"))
        return list
    }
}