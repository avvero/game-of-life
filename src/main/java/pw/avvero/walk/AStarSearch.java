package pw.avvero.walk;

import pw.avvero.board.Cell;

import java.util.*;
import java.util.function.BiFunction;

public class AStarSearch<T> {

    private Heuristic<T> heuristic;

    public AStarSearch(Heuristic<T> heuristic) {
        this.heuristic = heuristic;
    }

    public List<Cell<T>> path(Cell<T> from, Cell<T> to, BiFunction<Integer, Integer, List<Cell<T>>> getNeighbors) {
        // Priority queue to manage exploration based on f-cost
        PriorityQueue<AStarNode<T>> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.fCost));
        Map<Cell<T>, Integer> gScore = new HashMap<>(); // Cost from start to a node
        Map<Cell<T>, Cell<T>> cameFrom = new HashMap<>(); // For reconstructing path

        gScore.put(from, 0);
        openSet.add(new AStarNode<T>(from, 0, heuristic.estimate(from, to)));

        while (!openSet.isEmpty()) {
            AStarNode<T> current = openSet.poll();
            if (current.cell.equals(to)) {
                return reconstructPath(cameFrom, to);
            }

            // Explore neighbors
            List<Cell<T>> neighbors = getNeighbors.apply(current.cell.x, current.cell.y);
            for (Cell<T> neighbor : neighbors) {
                int tentativeGScore = gScore.get(current.cell) + 1; // Assumes uniform cost. Adjust as needed.
                if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current.cell);
                    gScore.put(neighbor, tentativeGScore);
                    int fCost = tentativeGScore + heuristic.estimate(neighbor, to);
                    if (!containsCell(openSet, neighbor)) {
                        openSet.add(new AStarNode<T>(neighbor, tentativeGScore, fCost));
                    }
                }
            }
        }

        return new ArrayList<>(); // Return empty path if no path found
    }

    private List<Cell<T>> reconstructPath(Map<Cell<T>, Cell<T>> cameFrom, Cell<T> current) {
        List<Cell<T>> path = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            path.add(current);
            current = cameFrom.get(current);
        }
        path.add(current); // Add the start cell
        Collections.reverse(path);
        return path;
    }

    private boolean containsCell(PriorityQueue<AStarNode<T>> openSet, Cell<T> cell) {
        return openSet.stream().anyMatch(n -> n.cell.equals(cell));
    }

    private static class AStarNode<T> {
        final Cell<T> cell;
        final int gCost;
        final int fCost;

        AStarNode(Cell<T> cell, int gCost, int fCost) {
            this.cell = cell;
            this.gCost = gCost;
            this.fCost = fCost;
        }
    }

    public interface Heuristic<T> {
        int estimate(Cell<T> from, Cell<T> to);
    }

    public static class ManhattanDistance<T> implements Heuristic<T> {
        @Override
        public int estimate(Cell<T> from, Cell<T> to) {
            return Math.abs(from.x - to.x) + Math.abs(from.y - to.y);
        }
    }
}
