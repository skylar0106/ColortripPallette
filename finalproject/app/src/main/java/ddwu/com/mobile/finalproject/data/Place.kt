package ddwu.com.mobile.finalproject.data

import java.io.Serializable

data class Place(
    val addr1: String?,
    val firstimage: String?,
    val mapx: String?,
    val mapy: String?,
    val title: String?,
    val tel: String?,
    val zipcode: String?,
    val areaCode: String?
) : Serializable