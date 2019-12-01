package com.dawar.sparknetwork.models

data class Location(val latitude: Double?, val longitude: Double?) {
    constructor() : this(null, null)
}