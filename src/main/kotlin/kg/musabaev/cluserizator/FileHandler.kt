package kg.musabaev.cluserizator

import java.util.stream.Stream

interface FileHandler {
    fun getLinesCsv(): Stream<String>
}
