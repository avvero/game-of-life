package pw.avvero.unit

import pw.avvero.BoardTestDisplay
import pw.avvero.Render
import pw.avvero.board.Board
import pw.avvero.board.BoardBordered
import pw.avvero.board.Cell
import pw.avvero.board.MoorNeighborhood
import pw.avvero.board.VonNeumannNeighborhood
import pw.avvero.walk.FootPrint
import pw.avvero.walk.Hound
import pw.avvero.walk.Kennel
import pw.avvero.walk.WalkableCell
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.atomic.AtomicInteger

class HoundsFindTargetsTests extends Specification {

    @Unroll
    def "Move on MoorNeighborhood 1"() {
        when:
        Board<Object> board = new BoardBordered<>(7, 10, new MoorNeighborhood<>(), WalkableCell::new)
        AtomicInteger ids = new AtomicInteger(1)
        board.update(0, 0, (current) -> current.value = "3")
        board.update(6, 9, (current) -> current.value = "4")
        board.update(3, 3, (current) -> current.value = new Kennel(
                new Hound(ids.getAndIncrement(), (c) -> c == "3"),
                new Hound(ids.getAndIncrement(), (c) -> c == "4")
        ))
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim("""3 ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ^ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦      
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ 4""")
        when:
        10.times {
            board.nextCycle()
        }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim("""3 ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ 1 ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ . ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ^ . ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ . ▦ ▦ ▦ ▦ 
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ . ▦ ▦ ▦     
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ . 2 4""")
    }


    Render render() {
        return new Render<Cell>() {
            @Override
            String draw(Cell cell) {
                if (cell.value instanceof Kennel) {
                    return " ^"
                }
                if (cell.value instanceof Hound) {
                    return " " + cell.value.id
                }
                if (cell.value instanceof FootPrint) {
                    return " ."
                }
                if (cell.value == null) return " ▦"
                return " " + cell.value.toString()
            }
        }
    }

    def trim(String string) {
        return string.replace(" ", "")
    }
}
