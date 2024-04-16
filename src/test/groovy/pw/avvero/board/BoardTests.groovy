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
        board.findFirst(c -> c.value == null).x == 0
        board.findFirst(c -> c.value == null).y == 0
        board.findFirst(c -> c.value == actor).x == 3
        board.findFirst(c -> c.value == actor).y == 7
        board.findFirst(c -> c.value == "!").x == 5
        board.findFirst(c -> c.value == "!").y == 3
        board.findFirst(c -> c.value == "?").x == 6
        board.findFirst(c -> c.value == "?").y == 9
        board.findFirst(c -> c.value == "@") == null
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
