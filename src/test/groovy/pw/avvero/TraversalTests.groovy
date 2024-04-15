package pw.avvero

import pw.avvero.board.Board
import pw.avvero.board.BoardBordered
import pw.avvero.board.Cell
import pw.avvero.board.MoorNeighborhood
import pw.avvero.move.Actor
import pw.avvero.move.TraversalSpaceCell
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.atomic.AtomicInteger

class TraversalTests extends Specification {

    @Unroll
    def "Move tests"() {
        when:
        Board<Object> board = new BoardBordered<>(7, 10, new MoorNeighborhood<>(), TraversalSpaceCell::new)
        AtomicInteger ids = new AtomicInteger(1)
        def actor = new Actor(ids.getAndIncrement(), "!")
        board.update(0, 0, (current) -> current.value = actor)
        board.update(6, 9, (current) -> current.value = "!")
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim("""1 ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦      
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ !""")
        when:
        20.times {
            board.nextCycle()
        }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(""". . . . ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ . ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ . ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ . ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ . ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ 1 ▦      
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ !""")
    }

    Render render() {
        return new Render<Cell>() {
            @Override
            String draw(Cell cell) {
                if (cell.value instanceof Actor) {
                    return " " + cell.value.id
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
