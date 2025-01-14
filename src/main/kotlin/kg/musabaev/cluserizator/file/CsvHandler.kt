package kg.musabaev.cluserizator.file

import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Stream
import kotlin.streams.asSequence

class CsvHandler(filename: String) {
    private val bufReader = BufferedReader(FileReader(filename), 128)

    fun linesAsStream(): Stream<List<String>> {
        return bufReader
            .lines()
//            .parallel()
            .map { it.split(Regex("\\s*,\\s*")) }
    }

    fun linesAsSequence(): Sequence<List<String>> {
        return bufReader
            .lines()
            .asSequence()
            .map { it.split(Regex("\\s*,\\s*")) }
    }
}