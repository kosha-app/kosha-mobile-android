package com.musica.dashboard.player

import org.junit.Assert
import org.junit.Test

class PlayerUtilTest {

    @Test
    fun testFormatTimeUtil() {
        Assert.assertEquals(PlayerUtil.formatTime(20000F), "00:20")
    }
}