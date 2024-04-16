package pw.avvero.board

import pw.avvero.BoardTestDisplay
import pw.avvero.Render
import spock.lang.Specification

import java.util.concurrent.atomic.AtomicInteger

class AStarNeighborhoodTests extends Specification {

    def "Neighbours for 0, 0"() {
        when:
        def id = new AtomicInteger()
        Board<Integer> board = new BoardBordered<>(3, 3, new MoorNeighborhood<>(), () -> new NoopCell(id.getAndIncrement()))
        then:
        BoardTestDisplay.toString(board, render()) == trim("""012
                                                              345
                                                              678""")
        and:
        board.neighbours(0, 0).toString().trim() == "[" +
                "[cell=1, path=[0, 1, 1]], " +
                "[cell=3, path=[0, 3, 3]], " +
                "[cell=4, path=[0, 4, 4]]]".trim()
    }

    Render render() {
        return new Render() {
            @Override
            String draw(Object value) {
                return value
            }
        }
    }

    def trim(String string) {
        return string.replace(" ", "")
    }

}
