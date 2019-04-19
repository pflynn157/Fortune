package org.patrickf4664.fortune

import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader

class StorageManager {

    companion object {
        @JvmStatic
        var quotes = ArrayList<String>()

        @JvmStatic
        fun loadFromFile(context: Context, file: String): ArrayList<String> {
            val qlines = ArrayList<String>()

            val inputStream = context.assets.open(file)
            val inputStreamReader = InputStreamReader(inputStream)
            val reader = BufferedReader(inputStreamReader)

            var line = reader.readLine()
            var content = ""

            while (line != null) {
                if (line == "%") {
                    qlines.add(content)
                    content = ""
                } else {
                    content += line + "\n"
                }

                line = reader.readLine()
            }

            qlines.add(content)
            inputStreamReader.close()
            reader.close()

            return qlines
        }

        @JvmStatic
        fun getQuote(context: Context): String {
            try {
                var ret = ""
                var index = (0..quotes.size-1).random()
                val file = quotes.get(index)

                val qlines = loadFromFile(context, file)

                index = (0..qlines.size-1).random()
                ret = qlines.get(index)
                return ret
            } catch (e: FileNotFoundException) {
                return ""
            }
        }

        @JvmStatic
        fun loadAllQuotes(context: Context) {
            var assets = context.assets.list("")
            for (str in assets) {
                quotes.add(str)
            }
        }
    }
}