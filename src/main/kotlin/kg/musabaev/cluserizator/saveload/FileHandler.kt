package kg.musabaev.cluserizator.saveload

import java.util.stream.Stream

interface FileHandler {
    fun getLinesCsv(): Stream<String>
}
