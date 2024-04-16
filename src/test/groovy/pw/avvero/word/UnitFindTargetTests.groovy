package pw.avvero.word

import pw.avvero.BoardTestDisplay
import pw.avvero.Render
import pw.avvero.board.Board
import pw.avvero.board.BoardBordered
import pw.avvero.board.Cell
import pw.avvero.board.MoorNeighborhood
import spock.lang.Specification
import spock.lang.Unroll

class UnitFindTargetTests extends Specification {

    def "Unit finds target near"() {
        when:
        Board<Object> board = new BoardBordered<>(3, 3, new MoorNeighborhood<>(), WordCell::new)
        board.update(0, 0, (current) -> current.value = new Unit((cell) -> cell.value instanceof Point && cell.value.label == "!"))
        board.update(2, 2, (current) -> current.value = new Point("!"))
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim("""U ☐ ☐ 
                                                                    ☐ ☐ ☐
                                                                    ☐ ☐ ! """)
        when:
        10.times {
            board.nextCycle()
        }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(""". ☐ ☐ 
                                                                    ☐ U ☐
                                                                    ☐ ☐ ! """)
    }

    def "Unit finds target"() {
        when:
        Board<Object> board = new BoardBordered<>(7, 10, new MoorNeighborhood<>(), WordCell::new)
        board.update(0, 0, (current) -> current.value = new Unit((cell) -> cell.value instanceof Point && cell.value.label == "!"))
        board.update(3, 8, (current) -> current.value = new Point("!"))
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim("""U ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ! ☐
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐""")
        when:
        10.times {
            board.nextCycle()
        }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(""". ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ . ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ☐ . ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ☐ ☐ . . . . U ! ☐
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐""")
    }

    def "Unit finds target in maze"() {
        when:
        Board<Object> board = new BoardBordered<>(7, 10, new MoorNeighborhood<>(), WordCell::new)
        board.update(0, 0, (current) -> current.value = new Unit((cell) -> cell.value instanceof Point && cell.value.label == "!"))
        board.update(3, 8, (current) -> current.value = new Point("!"))
        board.update(0, 1, (current) -> current.value = new Stone())
        board.update(1, 1, (current) -> current.value = new Stone())
        board.update(2, 1, (current) -> current.value = new Stone())
        board.update(4, 2, (current) -> current.value = new Stone())
        board.update(4, 3, (current) -> current.value = new Stone())
        board.update(3, 3, (current) -> current.value = new Stone())
        board.update(2, 3, (current) -> current.value = new Stone())
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim("""U ▴ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ▴ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ▴ ☐ ▴ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ☐ ☐ ▴ ☐ ☐ ☐ ☐ ! ☐
                                                                    ☐ ☐ ▴ ▴ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐""")
        when:
        10.times {
            board.nextCycle()
        }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(""". ▴ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    . ▴ ☐ . ☐ ☐ ☐ ☐ ☐ ☐
                                                                    . ▴ . ▴ . ☐ ☐ ☐ ☐ ☐
                                                                    ☐ . ☐ ▴ ☐ . . U ! ☐
                                                                    ☐ ☐ ▴ ▴ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐""")
    }

    def "Unit can't find target in maze"() {
        when:
        Board<Object> board = new BoardBordered<>(7, 10, new MoorNeighborhood<>(), WordCell::new)
        board.update(0, 0, (current) -> current.value = new Unit((cell) -> cell.value instanceof Point && cell.value.label == "!"))
        board.update(3, 8, (current) -> current.value = new Point("!"))
        board.update(0, 1, (current) -> current.value = new Stone())
        board.update(1, 1, (current) -> current.value = new Stone())
        board.update(2, 1, (current) -> current.value = new Stone())
        board.update(3, 1, (current) -> current.value = new Stone())
        board.update(3, 0, (current) -> current.value = new Stone())
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim("""U ▴ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ▴ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ▴ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ▴ ▴ ☐ ☐ ☐ ☐ ☐ ☐ ! ☐
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐""")
        when:
        10.times {
            board.nextCycle()
        }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim("""U ▴ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ▴ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ▴ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ▴ ▴ ☐ ☐ ☐ ☐ ☐ ☐ ! ☐
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                                                                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐""")
    }


    Render render() {
        return new Render<Cell>() {
            @Override
            String draw(Cell cell) {
                if (cell.value instanceof Unit) {
                    return " U"
                }
                if (cell.value instanceof Point) {
                    return " " + cell.value.label
                }
                if (cell.value instanceof Stone) {
                    return " ▴"
                }
                if (cell.value instanceof FootPrint) {
                    return " ."
                }
                if (cell.value == null) return " ☐"
                return " " + cell.value.toString()
            }
        }
    }

    def trim(String string) {
        return string.replace(" ", "")
    }
}
