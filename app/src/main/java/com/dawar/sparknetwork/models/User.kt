package com.dawar.sparknetwork.models


data class User(var name: String? = null) {
    var image: String? = null
    val id = "1"
    var phone: String? = null
    var email: String? = null

    var address: String? = null

    constructor() : this(null)
}


