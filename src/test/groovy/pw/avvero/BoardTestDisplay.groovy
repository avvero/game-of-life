package pw.avvero

import pw.avvero.board.Board
import pw.avvero.board.Cell

class BoardTestDisplay {

    static def toString(Board board, Render<Cell<Integer>> render) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.value().length; i++) {
            for (int j = 0; j < board.value()[i].length; j++) {
                Cell cell = board.value()[i][j];
                sb.append(render.draw(cell));
            }
            if (i < board.value().length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    static String trim(String string) {
        return string.replace(" ", "")
    }
}
