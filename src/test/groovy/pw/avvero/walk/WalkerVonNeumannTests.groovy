package pw.avvero.walk

import pw.avvero.BoardTestDisplay
import pw.avvero.Render
import pw.avvero.board.*
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.atomic.AtomicInteger

class WalkerVonNeumannTests extends Specification {

    @Unroll
    def "Move on VonNeumannNeighborhood 1"() {
        when:
        Board<Object> board = new BoardBordered<>(7, 10, new VonNeumannNeighborhood<>(), WalkableCell::new)
        AtomicInteger ids = new AtomicInteger(1)
        def actor = new Hound(ids.getAndIncrement(), (c) -> c == "!")
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
        trim(BoardTestDisplay.toString(board, render())) == trim(""". ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    . . ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ . . . . . . . ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ . ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ . ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ . ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ . 1 !""")
    }

    @Unroll
    def "Move on VonNeumannNeighborhood 2"() {
        when:
        Board<Object> board = new BoardBordered<>(7, 10, new VonNeumannNeighborhood<>(), WalkableCell::new)
        AtomicInteger ids = new AtomicInteger(1)
        def id1 = ids.getAndIncrement()
        def id2 = ids.getAndIncrement()
        def actor1 = new Hound(id1, (c) -> c.class == Hound && c.id == id2)
        def actor2 = new Hound(id2, (c) -> c.class == Hound && c.id == id1)
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
        trim(BoardTestDisplay.toString(board, render())) == trim("""▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦
                                                                    . ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ 2 
                                                                    1 ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ .
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦      
                                                                    ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦ ▦""")
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

    def trim(String string) {
        return string.replace(" ", "")
    }
}
