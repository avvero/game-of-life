package pw.avvero

import pw.avvero.board.Board
import pw.avvero.board.BoardBordered
import pw.avvero.board.Cell
import pw.avvero.board.MoorNeighborhood
import pw.avvero.board.VonNeumannNeighborhood
import pw.avvero.move.FirstAvailableEnemyMoveCell
import pw.avvero.move.MoveTarget
import pw.avvero.move.Pawn
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.atomic.AtomicInteger

class FirstAvailableEnemyMoveCellTests extends Specification {

    @Unroll
    def "Move tests for two enemies 4x4"() {
        when:
        Board<MoveTarget> boardM = new BoardBordered<>(4, 4, new MoorNeighborhood<>(), FirstAvailableEnemyMoveCell::new)
        AtomicInteger idsm = new AtomicInteger(1);
        boardM.update(0, 0, (current) -> current.value = new Pawn(idsm.getAndIncrement()))
        boardM.update(3, 3, (current) -> current.value = new Pawn(idsm.getAndIncrement()))
        and:
        Board<MoveTarget> boardV = new BoardBordered<>(4, 4, new VonNeumannNeighborhood<>(), FirstAvailableEnemyMoveCell::new)
        AtomicInteger idsv = new AtomicInteger(1);
        boardV.update(0, 0, (current) -> current.value = new Pawn(idsv.getAndIncrement()))
        boardV.update(3, 3, (current) -> current.value = new Pawn(idsv.getAndIncrement()))
        then:
        BoardTestDisplay.toString(boardM, render()) == trim("""1000
                                                               0000
                                                               0000
                                                               0002""")
        BoardTestDisplay.toString(boardV, render()) == trim("""1000
                                                               0000
                                                               0000
                                                               0002""")
        when:
        boardM.nextCycle()
        boardV.nextCycle()
        then:
        BoardTestDisplay.toString(boardM, render()) == trim("""0000
                                                               0100
                                                               0020
                                                               0000""")
        BoardTestDisplay.toString(boardV, render()) == trim("""0000
                                                               1000
                                                               0002
                                                               0000""")
        when:
        boardM.nextCycle()
        boardV.nextCycle()
        then:
        BoardTestDisplay.toString(boardM, render()) == trim("""0000
                                                               0100
                                                               0000
                                                               0000""")
        BoardTestDisplay.toString(boardM, render()) == trim("""0000
                                                               0100
                                                               0000
                                                               0000""")
    }

    @Unroll
    def "Move tests for two enemies 4x7"() {
        when:
        Board<MoveTarget> boardM = new BoardBordered<>(4, 7, new MoorNeighborhood<>(), FirstAvailableEnemyMoveCell::new)
        AtomicInteger idsm = new AtomicInteger(1);
        boardM.update(1, 0, (current) -> current.value = new Pawn(idsm.getAndIncrement()))
        boardM.update(2, 6, (current) -> current.value = new Pawn(idsm.getAndIncrement()))
        and:
        Board<MoveTarget> boardV = new BoardBordered<>(4, 7, new VonNeumannNeighborhood<>(), FirstAvailableEnemyMoveCell::new)
        AtomicInteger idsv = new AtomicInteger(1);
        boardV.update(1, 0, (current) -> current.value = new Pawn(idsv.getAndIncrement()))
        boardV.update(2, 6, (current) -> current.value = new Pawn(idsv.getAndIncrement()))
        then:
        BoardTestDisplay.toString(boardM, render()) == trim("""0000000
                                                               1000000
                                                               0000002
                                                               0000000""")
        BoardTestDisplay.toString(boardV, render()) == trim("""0000000
                                                               1000000
                                                               0000002
                                                               0000000""")
        when:
        boardM.nextCycle()
        boardV.nextCycle()
        then:
        BoardTestDisplay.toString(boardM, render()) == trim("""0100000
                                                               0000020
                                                               0000000
                                                               0000000""")
        BoardTestDisplay.toString(boardV, render()) == trim("""0000000
                                                               0000002
                                                               1000000
                                                               0000000""")
        when:
        boardM.nextCycle()
        boardV.nextCycle()
        then:
        BoardTestDisplay.toString(boardM, render()) == trim("""0010200
                                                               0000000
                                                               0000000
                                                               0000000""")
        BoardTestDisplay.toString(boardV, render()) == trim("""0000000
                                                               1000000
                                                               0000002
                                                               0000000""")
        when:
        boardM.nextCycle()
        boardV.nextCycle()
        then:
        BoardTestDisplay.toString(boardM, render()) == trim("""0001200
                                                               0000000
                                                               0000000
                                                               0000000""")
        BoardTestDisplay.toString(boardV, render()) == trim("""0000000
                                                               0000002
                                                               1000000
                                                               0000000""")
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
