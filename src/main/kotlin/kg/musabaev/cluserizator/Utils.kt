package kg.musabaev.cluserizator

class Utils {
    companion object {
        fun getResourcePath(resourceName: String): String  {
            return ClassLoader.getSystemClassLoader().getResource(resourceName)?.toURI().toString()
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