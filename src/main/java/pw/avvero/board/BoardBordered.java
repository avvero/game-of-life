package pw.avvero.board;

import pw.avvero.State;

public class BoardBordered extends Board {

    public BoardBordered(int x, int y, State state) {
        super(new Cell[x][y], state);
    }

    @Override
    public boolean exists(int i, int j) {
        if (i < 0 || i >= value.length || j < 0 || j >= value[i].length) return false;
        return true; //value[i][j].value() != 0;
    }

    @Override
    public Cell get(int i, int j) {
        return value[i][j];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                Cell cell = value[i][j];
                String c = switch (cell.value()) { // ■ ◼ ⬛ ■ ▦ ⬛ ⛶ ⬜
                    case (1) -> "" + cell.getRole().sign() + "";
                    case (3) -> "" + cell.getRole().sign() + "";
                    case (2) -> " *"; // < ! \
                    case (4) -> " @";
                    default -> "  ";
                };
                sb.append(c);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
