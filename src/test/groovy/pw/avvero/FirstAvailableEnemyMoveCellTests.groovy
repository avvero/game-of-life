package pw.avvero

import pw.avvero.board.Board
import pw.avvero.board.BoardBordered
import pw.avvero.board.Cell
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
        Board<MoveTarget> board = new BoardBordered<>(4, 4, FirstAvailableEnemyMoveCell::new)
        AtomicInteger ids = new AtomicInteger(1);
        board.update(0, 0, (current) -> current.value = new Pawn(ids.getAndIncrement()))
        board.update(3, 3, (current) -> current.value = new Pawn(ids.getAndIncrement()))
        then:
        BoardTestDisplay.toString(board, render()) == trim("""1000
                                                              0000
                                                              0000
                                                              0002""")
        when:
        board.nextCycle()
        then:
        BoardTestDisplay.toString(board, render()) == trim("""0000
                                                              0100
                                                              0020
                                                              0000""")
        when:
        board.nextCycle()
        then:
        BoardTestDisplay.toString(board, render()) == trim("""0000
                                                              0100
                                                              0000
                                                              0000""")
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
