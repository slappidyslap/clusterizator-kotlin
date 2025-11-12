package kg.musabaev.cluserizator.saveload

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

class TestCsvFileHandler : FileHandler {
    override fun getLinesCsv(): Stream<String> {
        val url = this.javaClass.classLoader.getResource("Keyword Stats 2025-11-13 at 01_00_59.csv")
        val path = Paths.get(url!!.toURI())
        return Files.lines(path, Charsets.UTF_16LE)
    }
}