package pw.avvero.board;

public class BoardBordered<T> extends Board<T> {

    public BoardBordered(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean exists(int i, int j) {
        if (i < 0 || i >= value.length || j < 0 || j >= value[i].length) return false;
        return true; //value[i][j].value() != 0;
    }

    @Override
    public Cell<T> get(int i, int j) {
        return value[i][j];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                Cell<T> cell = value[i][j];
//                String c = switch (cell.value()) { // ■ ◼ ⬛ ■ ▦ ⬛ ⛶ ⬜
//                    case (1) -> "" + cell.toString() + "";
//                    case (3) -> "" + cell.toString() + "";
//                    case (2) -> " *"; // < ! \
//                    case (4) -> " @";
//                    default -> "  ";
//                };
                sb.append(cell.toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
