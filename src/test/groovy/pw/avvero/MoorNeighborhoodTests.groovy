package pw.avvero

import pw.avvero.board.Board
import pw.avvero.board.BoardBordered
import pw.avvero.board.NoopCell
import spock.lang.Specification

import java.util.concurrent.atomic.AtomicInteger

class MoorNeighborhoodTests extends Specification {

    def "Neighbours"() {
        when:
        def id = new AtomicInteger()
        Board<Integer> board = new BoardBordered<>(3, 3, () -> new NoopCell(id.getAndIncrement()))
        then:
        BoardTestDisplay.toString(board, render()) == trim("""012
                                                              345
                                                              678""")
        and:
        board.neighbours(0, 0).toString() == "[Neighbour[level=1, cell=1, path=[1]], Neighbour[level=1, cell=3, path=[3]], Neighbour[level=1, cell=4, path=[4]], Neighbour[level=2, cell=2, path=[1, 2]], Neighbour[level=2, cell=5, path=[1, 5]], Neighbour[level=2, cell=6, path=[3, 6]], Neighbour[level=2, cell=7, path=[3, 7]], Neighbour[level=2, cell=8, path=[4, 8]]]"
    }

    def "Neighbours2"() {
        when:
        def id = new AtomicInteger()
        Board<Integer> board = new BoardBordered<>(3, 3, () -> new NoopCell(id.getAndIncrement()))
        then:
        BoardTestDisplay.toString(board, render()) == trim("""012
                                                              345
                                                              678""")
        and:
        board.neighbours(2, 2).toString() == "[Neighbour[level=1, cell=4, path=[4]], Neighbour[level=1, cell=5, path=[5]], Neighbour[level=1, cell=7, path=[7]], Neighbour[level=2, cell=0, path=[4, 0]], Neighbour[level=2, cell=1, path=[4, 1]], Neighbour[level=2, cell=2, path=[4, 2]], Neighbour[level=2, cell=3, path=[4, 3]], Neighbour[level=2, cell=6, path=[4, 6]]]"
    }

    def "Neighbours3"() {
        when:
        def id = new AtomicInteger()
        Board<Integer> board = new BoardBordered<>(3, 3, () -> new NoopCell(id.getAndIncrement()))
        then:
        BoardTestDisplay.toString(board, render()) == trim("""012
                                                              345
                                                              678""")
        and:
        board.neighbours(1, 1).toString() == "[Neighbour[level=1, cell=0, path=[0]], Neighbour[level=1, cell=1, path=[1]], Neighbour[level=1, cell=2, path=[2]], Neighbour[level=1, cell=3, path=[3]], Neighbour[level=1, cell=5, path=[5]], Neighbour[level=1, cell=6, path=[6]], Neighbour[level=1, cell=7, path=[7]], Neighbour[level=1, cell=8, path=[8]]]"
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
