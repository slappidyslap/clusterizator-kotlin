package kg.musabaev.cluserizator.util

import java.io.File

class Utils {
    companion object {
        fun getResourcePath(resourcePath: String): String  {
            if (!resourcePath.contains("/")) {
                return ClassLoader.getSystemClassLoader().getResource(resourcePath)?.toURI().toString()
            } else {
                val segments = resourcePath.split("/")
                ClassLoader.getSystemClassLoader().getResource(segments[0])?.toURI()?.let {
                    var rootPath = File(it)
                    for (i in 1..<segments.size) {
                        rootPath = rootPath.resolve(segments[i])
                    }
                    return rootPath.toURI().toString()
                }
                return ""
            }
        }
        fun getFilePathFromRoot(vararg filenames: String): String  {
            if (filenames.size > 1) {
                var path = ClassLoader.getSystemResource(filenames[0])?.toURI().toString()
                for (i in 1..<filenames.size) {
                    path += "/${filenames[i]}"
                }
                return path
            } else {
                return ClassLoader.getSystemResource(filenames[0])?.toURI().toString()
            }
        }

    }
}