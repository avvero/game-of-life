package pw.avvero.board;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MoorVonNeumannNeighborhood<T> implements Neighborhood<T> {

    private final Neighborhood<T> moorNeighborhood = new MoorNeighborhood<>();
    private final Neighborhood<T> vonNeumannNeighborhood = new VonNeumannNeighborhood<>();
    private final AtomicInteger cycle = new AtomicInteger();

    @Override
    public List<Neighbour<T>> neighbours(Board<T> board, int i, int j) {
        Neighborhood<T> neighborhood = cycle.getAndIncrement() % 9 == 0 ? moorNeighborhood : vonNeumannNeighborhood;
        return neighborhood.neighbours(board, i, j);
    }
}
