package com.musica.phone

import org.junit.Assert
import org.junit.Test

class TheBigGuyTest {

    @Test
    fun testAdd() {
        val theBigGuy = TheBigGuy()

        Assert.assertEquals(theBigGuy.add(2, 5), 7)
    }

    @Test
    fun testSub() {
        val theBigGuy = TheBigGuy()

        Assert.assertEquals(theBigGuy.sub(5, 3), 2)
    }

    @Test
    fun testMulti() {
        val theBigGuy = TheBigGuy()

        Assert.assertEquals(theBigGuy.multi(2, 5), 10)
    }
}