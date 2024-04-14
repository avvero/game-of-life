package pw.avvero

import pw.avvero.board.Board
import pw.avvero.board.BoardBordered
import pw.avvero.move.FirstAvailableEnemyMoveCell
import pw.avvero.move.MoveTarget
import pw.avvero.move.Pawn
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.atomic.AtomicInteger

class FirstAvailableEnemyMoveCellTests extends Specification {

    @Unroll
    def "Move tests 2d"() {
        when:
        Board<MoveTarget> board = new BoardBordered<>(5, 5, FirstAvailableEnemyMoveCell::new)
        AtomicInteger ids = new AtomicInteger(1);
        board.update(0, 0, (current) -> current.value = new Pawn(ids.getAndIncrement()))
        board.update(4, 4, (current) -> current.value = new Pawn(ids.getAndIncrement()))
        then:
        BoardTestDisplay.toString(board, render()) == trim("""1 0 0 0 0
                                                              0 0 0 0 0
                                                              0 0 0 0 0
                                                              0 0 0 0 0
                                                              0 0 0 0 2""")
        when:
        board.nextCycle()
        then:
        BoardTestDisplay.toString(board, render()) == trim("""0 0 0 0 0
                                                              0 1 0 0 0
                                                              0 0 0 0 0
                                                              0 0 0 2 0
                                                              0 0 0 0 0""")
    }

    Render render() {
        return new Render() {
            @Override
            String draw(Object value) {
                if (value instanceof Pawn) {
                    return value.id
                }
                return "0"
            }
        }
    }

    def trim(String string) {
        return string.replace(" ", "")
    }
}
