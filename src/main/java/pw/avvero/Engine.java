package pw.avvero;

import pw.avvero.board.Board;
import pw.avvero.board.Cell;

import java.io.IOException;

public class Engine<T> {
    public void run(Board<T> board, Render<Cell<T>> render, int sleepTime) throws InterruptedException {
        display(board, render, 0);
        Thread.sleep(sleepTime);
        while (true) {
            long start = System.currentTimeMillis();
            board.nextCycle();
            long cycleTime = System.currentTimeMillis() - start;
            display(board, render, cycleTime);
            Thread.sleep(Math.max(0, sleepTime - cycleTime));
        }
    }

    private void display(Board<T> board, Render<Cell<T>> render, long cycleTime) {
        String payload = toString(board.value(), render, cycleTime);
        clear();
        System.out.println(payload);
    }

    public String toString(Cell<T>[][] board, Render<Cell<T>> render, long cycleTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("--".repeat(board[0].length));
        sb.append("\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Cell<T> cell = board[i][j];
                sb.append(render.draw(cell));
            }
            sb.append("\n");
        }
        sb.append("--".repeat(board[0].length) + cycleTime);
        return sb.toString();
    }

    private void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
        }
    }
}
