package com.dawar.sparknetwork

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    var s = "Hey"


    @Test
    fun checkCryptography() {
        val e = Utils.encrypt(s, "1")
        assertEquals(s, Utils.decrypt(e, "1"))
    }
}
