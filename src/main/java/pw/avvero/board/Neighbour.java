package pw.avvero.board;

import java.util.List;

public record Neighbour<T>(int level, Cell<T> cell, List<Cell<T>> path) {

}
