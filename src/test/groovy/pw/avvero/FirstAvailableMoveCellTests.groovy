package pw.avvero

import pw.avvero.board.Board
import pw.avvero.board.BoardBordered
import pw.avvero.board.Cell
import pw.avvero.move.FirstAvailableMoveCell
import pw.avvero.move.MoveTarget
import pw.avvero.move.Pawn
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.atomic.AtomicInteger

class FirstAvailableMoveCellTests extends Specification {

    @Unroll
    def "Move tests 1d"() {
        when:
        Board<MoveTarget> board = new BoardBordered<>(1, 5, FirstAvailableMoveCell::new)
        board.update(0, 4, (current) -> current.value = new Pawn(1))
        cycles.times {
            board.nextCycle()
        }
        then:
        BoardTestDisplay.toString(board, render()) == result
        where:
        cycles || result
        0      || "00001"
        1      || "00010"
        2      || "00100"
        3      || "01000"
        4      || "10000"
        5      || "01000"
        6      || "10000"
    }

    @Unroll
    def "Move tests 2d"() {
        when:
        Board<MoveTarget> board = new BoardBordered<>(3, 3, FirstAvailableMoveCell::new)
        AtomicInteger ids = new AtomicInteger(1);
        board.update(0, 2, (current) -> current.value = new Pawn(ids.getAndIncrement()))
        board.update(2, 0, (current) -> current.value = new Pawn(ids.getAndIncrement()))
        then:
        BoardTestDisplay.toString(board, render()) == trim("""001
                                                              000
                                                              200""")
        when:
        board.nextCycle()
        then:
        BoardTestDisplay.toString(board, render()) == trim("""010
                                                              200
                                                              000""")
        when:
        board.nextCycle()
        then:
        BoardTestDisplay.toString(board, render()) == trim("""100
                                                              200
                                                              000""")
    }

    Render render() {
        return new Render<Cell>() {
            @Override
            String draw(Cell cell) {
                if (cell.value instanceof Pawn) {
                    return cell.value.id
                }
                return "0";
//                return (cell.id < 10 ? "_" : "") + cell.id;
            }
        }
    }

    def trim(String string) {
        return string.replace(" ", "")
    }
}
