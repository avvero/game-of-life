package pw.avvero

import pw.avvero.board.Board
import pw.avvero.board.BoardBordered
import pw.avvero.board.NoopCell
import spock.lang.Specification

import java.util.concurrent.atomic.AtomicInteger

class BoardTests extends Specification {

    def "Neighbours"() {
        when:
        def id = new AtomicInteger()
        Board<Integer> board = new BoardBordered<>(3, 3, () -> new NoopCell(id.getAndIncrement()))
        then:
        BoardTestDisplay.toString(board, render()) == trim("""012
                                                              345
                                                              678""")
        and:
        board.neighbours(1, 1).cell.value == [0, 1, 2, 3, 5, 6, 7, 8]
    }

    Render render() {
        return new Render() {
            @Override
            String draw(Object value) {
                return value
            }
        }
    }

    def trim(String string) {
        return string.replace(" ", "")
    }
}
