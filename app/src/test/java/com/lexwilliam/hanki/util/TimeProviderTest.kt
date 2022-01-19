package com.lexwilliam.hanki.util

import com.google.common.truth.ExpectFailure.assertThat
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

class TimeProviderTest {

    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }

    @Test
    fun currentTimeReturnTrue() {
        val result = TimeProvider.getCurrentDate()
        assertEquals("19 Jan", result)
    }
}