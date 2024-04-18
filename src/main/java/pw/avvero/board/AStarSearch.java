package pw.avvero.board;

import java.util.*;
import java.util.function.Function;

public class AStarSearch<T> {

    private final Heuristic<T> heuristic;

    public AStarSearch(Heuristic<T> heuristic) {
        this.heuristic = heuristic;
    }

    public List<Cell<T>> path(Cell<T> from, Cell<T> to, Function<Cell<T>, List<Cell<T>>> getNeighbors) {
        // Priority queue to manage exploration based on f-cost
        PriorityQueue<AStarNode<T>> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fCost));
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
            List<Cell<T>> neighbors = getNeighbors.apply(current.cell);
            for (Cell<T> neighbor : neighbors) {
                int tentativeGScore = gScore.get(current.cell) + 1; // Assumes uniform cost. Adjust as needed.
                if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current.cell);
                    gScore.put(neighbor, tentativeGScore);
                    double fCost = tentativeGScore + heuristic.estimate(neighbor, to);
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
        final double fCost;

        AStarNode(Cell<T> cell, int gCost, double fCost) {
            this.cell = cell;
            this.gCost = gCost;
            this.fCost = fCost;
        }
    }

    public interface Heuristic<T> {
        double estimate(Cell<T> from, Cell<T> to);
    }

    public static class ManhattanDistance<T> implements Heuristic<T> {
        @Override
        public double estimate(Cell<T> from, Cell<T> to) {
            return Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY());
        }
    }

    public static class DiagonalDistance<T> implements Heuristic<T> {
        private final double D = 1;  // Cost between directly connected nodes
        private final double D2 = (int) Math.sqrt(2);  // Cost between diagonally connected nodes

        @Override
        public double estimate(Cell<T> from, Cell<T> to) {
            int dx = Math.abs(from.getX() - to.getX());
            int dy = Math.abs(from.getY() - to.getY());
            return D * (dx + dy) + (D2 - 2 * D) * Math.min(dx, dy);
        }
    }

    public static class ModifiedChebyshevDistance<T> implements Heuristic<T> {
        private final int D = 1;   // Cost for straight moves (N, S, E, W)
        private final int D2 = 2;  // Increased cost for diagonal moves (NE, SE, SW, NW)

        @Override
        public double estimate(Cell<T> from, Cell<T> to) {
            int dx = Math.abs(from.getX() - to.getX());
            int dy = Math.abs(from.getY() - to.getY());
            return D * (dx + dy) + (D2 - 2 * D) * Math.min(dx, dy);
        }
    }

//    public static class WeightedGraphHeuristic<T> implements Heuristic<T> {
//        private Board<T> board;  // Assuming Board provides weight info
//
//        public WeightedGraphHeuristic(Board<T> board) {
//            this.board = board;
//        }
//
//        @Override
//        public int estimate(Cell<T> from, Cell<T> to) {
//            // This could be more complex depending on the weight calculation needs
//            int basicDistance = Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY());
//            int averageWeight = (board.getWeight(from) + board.getWeight(to)) / 2;
//            return basicDistance * averageWeight;
//        }
//    }
}
