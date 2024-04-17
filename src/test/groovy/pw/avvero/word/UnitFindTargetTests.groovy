package pw.avvero.word

import pw.avvero.BoardTestDisplay
import pw.avvero.Render
import pw.avvero.board.Board
import pw.avvero.board.Cell
import spock.lang.Specification

import java.util.function.Function

import static pw.avvero.BoardTestDisplay.trim

class UnitFindTargetTests extends Specification {

    def "Unit finds target near"() {
        when:
        Board<Object> board = WordConstructor.constructFrom(schema, wordFactory)
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(schema)
        when:
        10.times { board.nextCycle() }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(result)
        where:
        schema = """♟ ☐ ☐ 
                    ☐ ☐ ☐
                    ☐ ☐ !"""
        result = """. ☐ ☐ 
                    ☐ ♟ ☐
                    ☐ ☐ !"""
    }

    def "Unit finds target"() {
        when:
        Board<Object> board = WordConstructor.constructFrom(schema, wordFactory)
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(schema)
        when:
        10.times { board.nextCycle() }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(result)
        where:
        schema = """♟ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ! ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐"""
        result = """. ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ . ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ . ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ . . . . ♟ ! ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐"""
    }

    def "Unit finds target in maze"() {
        when:
        Board<Object> board = WordConstructor.constructFrom(schema, wordFactory)
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(schema)
        when:
        10.times { board.nextCycle() }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(result)
        where:
        schema = """♟ ● ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ● ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ● ☐ ● ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ● ☐ ☐ ☐ ☐ ! ☐
                    ☐ ☐ ● ● ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐"""
        result = """. ● ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    . ● ☐ . ☐ ☐ ☐ ☐ ☐ ☐
                    . ● . ● . ☐ ☐ ☐ ☐ ☐
                    ☐ . ☐ ● ☐ . . ♟ ! ☐
                    ☐ ☐ ● ● ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐"""
    }

    def "Unit can't find target in maze"() {
        when:
        Board<Object> board = WordConstructor.constructFrom(schema, wordFactory)
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(schema)
        when:
        10.times { board.nextCycle() }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(result)
        where:
        schema = """♟ ● ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ● ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ● ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ● ● ☐ ☐ ☐ ☐ ☐ ☐ ! ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐"""
        result = """♟ ● ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ● ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ● ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ● ● ☐ ☐ ☐ ☐ ☐ ☐ ! ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐"""
    }

    def wordFactory = new Function<Character, WordObject>() {
        @Override
        WordObject apply(Character ch) {
            switch (ch) {
                case '♟': return new Pawn((cell) -> cell.value instanceof Point && cell.value.label == "!")
                case '!': return new Point("!")
                case '●': return new Stone()
                default: return null;
            }
        }
    }

    Render render() {
        return new Render<Cell>() {
            @Override
            String draw(Cell cell) {
                if (cell.value instanceof Pawn) {
                    return " ♟"
                }
                if (cell.value instanceof Point) {
                    return " " + cell.value.label
                }
                if (cell.value instanceof Stone) {
                    return " ●"
                }
                if (cell.value instanceof FootPrint) {
                    return " ."
                }
                if (cell.value == null) return " ☐"
                return " " + cell.value.toString()
            }
        }
    }
}
