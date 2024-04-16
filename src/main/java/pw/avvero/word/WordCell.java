package pw.avvero.word;

import pw.avvero.board.Cell;

public class WordCell extends Cell<WordObject> {

    public WordCell(WordObject value) {
        super(value);
    }

    @Override
    public Runnable nextState() {
        if (this.value == null) return null;
        if (this.value instanceof WordObject wordObject) {
            return wordObject.process(board, this);
        }
        throw new UnsupportedOperationException();
    }
}
