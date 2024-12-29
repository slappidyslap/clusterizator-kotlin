package kg.musabaev.cluserizator;

import java.util.stream.Stream;

public interface FileHandler {
    Stream<String> getLinesCsv();
}
