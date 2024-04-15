package pw.avvero

import pw.avvero.board.Board
import pw.avvero.board.BoardBordered
import pw.avvero.board.Cell
import pw.avvero.board.MoorNeighborhood
import pw.avvero.move.Actor
import pw.avvero.move.FootPrint
import pw.avvero.move.WalkableCell
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.atomic.AtomicInteger

class WalkerMoorTests extends Specification {

    @Unroll
    def "Move on MoorNeighborhood 1"() {
        when:
        Board<Object> board = new BoardBordered<>(7, 10, new MoorNeighborhood<>(), WalkableCell::new)
        AtomicInteger ids = new AtomicInteger(1)
        def actor = new Actor(ids.getAndIncrement(), (c) -> c == "!")
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
        19.times {
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

    @Unroll
    def "Move on MoorNeighborhood 2"() {
        when:
        Board<Object> board = new BoardBordered<>(7, 10, new MoorNeighborhood<>(), WalkableCell::new)
        AtomicInteger ids = new AtomicInteger(1)
        def id1 = ids.getAndIncrement()
        def id2 = ids.getAndIncrement()
        def actor1 = new Actor(id1, (c) -> c.class == Actor && c.id == id2)
        def actor2 = new Actor(id2, (c) -> c.class == Actor && c.id == id1)
        board.update(3, 0, (current) -> current.value = actor1)
        board.update(4, 9, (current) -> current.value = actor2)
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim("""▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    1 ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ 
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ 2
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦      
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦""")
        when:
        19.times {
            board.nextCycle()
        }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim("""▦ ▦ ▦ . 1 2 ▦ ▦ ▦ ▦
                                                                    ▦ ▦ . ▦ ▦ ▦ . ▦ ▦ ▦
                                                                    ▦ . ▦ ▦ ▦ ▦ ▦ . ▦ ▦
                                                                    . ▦ ▦ ▦ ▦ ▦ ▦ ▦ . ▦ 
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ .
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦      
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦""")
    }


    Render render() {
        return new Render<Cell>() {
            @Override
            String draw(Cell cell) {
                if (cell.value instanceof Actor) {
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
