package utils

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class UtilsTest {
    @Test
    fun testGetKeys() {
        val expected = listOf("key1", " key2")
        val fakeInput = listOf("key1, key2", "value1, value2")
        val actualResult = getKeys(fakeInput)
        assertEquals(expected, actualResult)
    }

    @Test
    fun testGetValues() {
        val fakeInput = listOf("key1, key2", "value1, value2")
        val expected = fakeInput.drop(1)
        val actualResult = getValues(fakeInput)
        assertEquals(expected, actualResult)
    }

    @Test
    fun testGetJsonMap() {
        val fakeValues = listOf("value1", " value2")
        val fakeKeys = listOf("key1", " key2")
        val expected = mapOf("key1" to "value1", "key2" to "value2")
        val actualResult = getJsonMap(fakeKeys, fakeValues)
        assertEquals(expected, actualResult)
    }

    @Test
    fun testGetJsonList() {
        val fakeValues = listOf("value1, value2")
        val fakeKeys = listOf("key1", " key2")
        val expected = listOf(mapOf("key1" to "value1", "key2" to "value2"))
        val actualResult = getJsonList(fakeKeys, fakeValues)
        assertEquals(expected, actualResult)
    }
}