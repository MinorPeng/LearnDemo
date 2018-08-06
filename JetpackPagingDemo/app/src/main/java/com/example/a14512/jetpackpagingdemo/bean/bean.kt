package com.example.a14512.jetpackpagingdemo.bean

/**
 * @author 14512 on 2018/6/6
 */
class Response(val data: ArrayList<KituItem>)

data class KituItem(val id: Int, val type: String?, val attributes: KituItemAttributes?)

data class KituItemAttributes(val synopsis: String?,
                              val subtype: String?,
                              val titles: KituItemAttributesTitles?,
                              val posterImage: KituItemAttributesImage?)

data class KituItemAttributesTitles(val en_jp: String?)

data class KituItemAttributesImage(val small: String?)