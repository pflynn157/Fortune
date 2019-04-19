package org.patrickf4664.fortune.favorites

import android.content.Context
import java.io.File

class FavoritesManager {

    companion object {
        @JvmStatic
        var path = ""

        @JvmStatic
        fun initFolders(context: Context) {
            path = context.filesDir.absolutePath
            if (!path.endsWith("/")) {
                path += "/"
            }
            path += "favorites/"

            var file = File(path)
            if (!file.exists()) {
                file.mkdirs()
            }
        }

        @JvmStatic
        fun createFavorite(quote: String) {
            var file = File(path)
            val fav_count = (file.listFiles().size) + 1

            val new_path = path + "fav" + fav_count.toString() + ".txt"
            file = File(new_path)

            file.createNewFile()
            file.writeText(quote)
        }

        @JvmStatic
        fun loadFavQuotes() : ArrayList<String> {
            val quotes = ArrayList<String>()

            val file = File(path)
            val contents = file.listFiles()

            for (f in contents) {
                val current = f.readText()
                quotes.add(current)
            }

            return quotes
        }

        @JvmStatic
        fun rmFile(no: Int) {
            val target = "fav" + no.toString() + ".txt"

            var contents = File(path).listFiles()
            for (f in contents) {
                val current = f.name

                if (current == target) {
                    f.delete()
                    break
                }
            }

            contents = File(path).listFiles()
            var index = 1

            for (f in contents) {
                val newName = "fav" + index.toString() + ".txt"
                val newFile = File(path+newName)

                f.renameTo(newFile)
                index++
            }
        }
    }
}