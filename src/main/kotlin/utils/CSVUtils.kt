package utils

import java.nio.charset.Charset
import kotlin.collections.ArrayList
import java.util.Base64

fun getCsvInJson(base64File: String): MutableList< Map<String, String> > {
    val decodedCSV = Base64.getDecoder().decode(base64File)
    val sentences: List<String> = getSentences(decodedCSV)
    val keys: List<String> = getKeys(sentences)
    val values: List<String> = getValues(sentences)
    return getJsonList(keys, values)
}

fun getSentences(decodedCSV: ByteArray): List<String> {
    val decodedString: String = decodedCSV.toString(Charset.defaultCharset())
    return decodedString.split("\n")
}

fun getKeys(sentences: List<String>): List<String> {
    return sentences[0].split(",")
}

fun getValues(sentences: List<String>): List<String> {
    return sentences.drop(1)
}

fun getJsonList(keys: List<String>, valuesLists: List<String>): MutableList< Map<String, String> > {
    val jsonArray: MutableList< Map<String, String> > = ArrayList()
    for (valuesList in valuesLists) {
        val values = valuesList.split(",")
        if(values.indices == keys.indices){
            val jsonMap = getJsonMap(keys, values)
            jsonArray.add(jsonMap)
        }
    }
    return jsonArray
}

fun getJsonMap(keys: List<String>, values: List<String>): Map<String, String> {
    val jsonMap = mutableMapOf<String, String>()
    for (index in keys.indices) {
        jsonMap[keys[index].trimStart()] = values[index].trimStart()
    }
    return jsonMap
}
