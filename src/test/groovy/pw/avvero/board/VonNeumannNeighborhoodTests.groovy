package pw.avvero.board

import pw.avvero.BoardTestDisplay
import pw.avvero.Render
import spock.lang.Specification

import java.util.concurrent.atomic.AtomicInteger

class VonNeumannNeighborhoodTests extends Specification {

    def "Neighbours for 0, 0"() {
        when:
        def id = new AtomicInteger()
        Board<Integer> board = new BoardBordered<>(3, 3, new VonNeumannNeighborhood(), () -> new NoopCell(id.getAndIncrement()))
        then:
        BoardTestDisplay.toString(board, render()) == trim("""012
                                                              345
                                                              678""")
        and:
        board.neighbours(0, 0).toString().trim() == "[" +
                "[cell=3, path=[3]], " +
                "[cell=1, path=[1]], " +
                "[cell=6, path=[3, 6]], " +
                "[cell=4, path=[3, 4]], " +
                "[cell=2, path=[1, 2]], " +
                "[cell=7, path=[3, 6, 7]], " +
                "[cell=5, path=[3, 4, 5]], " +
                "[cell=8, path=[3, 6, 7, 8]]]".trim()
    }

    def "Neighbours for 2, 2"() {
        when:
        def id = new AtomicInteger()
        Board<Integer> board = new BoardBordered<>(3, 3, new VonNeumannNeighborhood(), () -> new NoopCell(id.getAndIncrement()))
        then:
        BoardTestDisplay.toString(board, render()) == trim("""012
                                                              345
                                                              678""")
        and:
        board.neighbours(2, 2).toString().trim() == "[" +
                "[cell=5, path=[5]], " +
                "[cell=7, path=[7]], " +
                "[cell=2, path=[5, 2]], " +
                "[cell=4, path=[5, 4]], " +
                "[cell=6, path=[7, 6]], " +
                "[cell=1, path=[5, 2, 1]], " +
                "[cell=3, path=[5, 4, 3]], " +
                "[cell=0, path=[5, 2, 1, 0]]]".trim()
    }

    def "Neighbours for 1, 1"() {
        when:
        def id = new AtomicInteger()
        Board<Integer> board = new BoardBordered<>(3, 3, new VonNeumannNeighborhood(), () -> new NoopCell(id.getAndIncrement()))
        then:
        BoardTestDisplay.toString(board, render()) == trim("""012
                                                              345
                                                              678""")
        and:
        board.neighbours(1, 1).toString().trim() == "[" +
                "[cell=1, path=[1]], " +
                "[cell=7, path=[7]], " +
                "[cell=3, path=[3]], " +
                "[cell=5, path=[5]], " +
                "[cell=0, path=[1, 0]], " +
                "[cell=2, path=[1, 2]], " +
                "[cell=6, path=[7, 6]], " +
                "[cell=8, path=[7, 8]]]".trim()
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
