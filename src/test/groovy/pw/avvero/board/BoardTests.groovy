package pw.avvero.board

import pw.avvero.BoardTestDisplay
import pw.avvero.Render
import pw.avvero.walk.Hound
import pw.avvero.walk.FootPrint
import pw.avvero.walk.WalkableCell
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.atomic.AtomicInteger

import static pw.avvero.BoardTestDisplay.trim

class BoardTests extends Specification {

    @Unroll
    def "Find first neighbour"() {
        when:
        Board<Object> board = new BoardBordered<>(7, 10, new MoorNeighborhood<>(), WalkableCell::new)
        AtomicInteger ids = new AtomicInteger(1)
        def actor = new Hound(ids.getAndIncrement(), null)
        board.update(3, 7, (current) -> current.value = actor)
        board.update(5, 3, (current) -> current.value = "!")
        board.update(6, 9, (current) -> current.value = "?")
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim("""▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ 1 ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ! ▦ ▦ ▦ ▦ ▦ ▦      
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ?""")
        and:
        board.findFirstNeighbour(c -> c.value == null).cell.x == 0
        board.findFirstNeighbour(c -> c.value == null).cell.y == 0
        board.findFirstNeighbour(c -> c.value == actor).cell.x == 3
        board.findFirstNeighbour(c -> c.value == actor).cell.y == 7
        board.findFirstNeighbour(c -> c.value == "!").cell.x == 5
        board.findFirstNeighbour(c -> c.value == "!").cell.y == 3
        board.findFirstNeighbour(c -> c.value == "?").cell.x == 6
        board.findFirstNeighbour(c -> c.value == "?").cell.y == 9
        board.findFirstNeighbour(c -> c.value == "@") == null
    }

    Render render() {
        return new Render<Cell>() {
            @Override
            String draw(Cell cell) {
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
}
