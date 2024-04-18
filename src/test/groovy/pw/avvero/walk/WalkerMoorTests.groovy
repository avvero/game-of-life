package pw.avvero.walk

import pw.avvero.BoardTestDisplay
import pw.avvero.Render
import pw.avvero.board.Board
import pw.avvero.board.Cell
import pw.avvero.board.MoorNeighborhood
import pw.avvero.word.*
import spock.lang.Specification

import java.util.function.Function

import static pw.avvero.BoardTestDisplay.trim

class WalkerMoorTests extends Specification {

    def "Unit finds target"() {
        when:
        Board<Object> board = WordConstructor.constructFrom(schema, new MoorNeighborhood<>(), wordFactory)
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(schema)
        when:
        10.times { board.nextCycle() }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(result)
        where:
        schema = """1 ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ !"""
        result = """. ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ . ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ . ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ . ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ . ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ . ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ . . 1 !"""
    }

    def "Unit finds target 2"() {
        when:
        Board<Object> board = WordConstructor.constructFrom(schema, new MoorNeighborhood<>(), wordFactory)
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(schema)
        when:
        10.times { board.nextCycle() }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(result)
        where:
        schema = """☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    2 ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ 3
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐"""
        result = """☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    . . . . 2 3 . . . .
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐"""
    }

    def "Unit finds target 3"() {
        when:
        Board<Object> board = WordConstructor.constructFrom(schema, new MoorNeighborhood<>(), wordFactory)
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(schema)
        when:
        10.times { board.nextCycle() }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(result)
        where:
        schema = """☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    2 ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ 3
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐"""
        result = """☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    . ☐ . ☐ 2 ☐ . ☐ . ☐
                    ☐ . ☐ . ☐ 3 ☐ . ☐ .
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐"""
    }

    def "Unit finds target 4"() {
        when:
        Board<Object> board = WordConstructor.constructFrom(schema, new MoorNeighborhood<>(), wordFactory)
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(schema)
        when:
        10.times { board.nextCycle() }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(result)
        where:
        schema = """1 ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ !
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    1 ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ !
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ 
                    1 ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ !
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                    1 ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ !"""
        result = """. . . . . . . . 1 !
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐
                    . . . . . . . . 1 !
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ 
                    . . . . . . . . 1 !
                    ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐      
                    . . . . . . . . 1 !"""
    }

    def wordFactory = new Function<Character, WordObject>() {
        @Override
        WordObject apply(Character ch) {
            switch (ch) {
                case '1': return new Pawn(1, (cell) -> cell.value instanceof Point && cell.value.label == "!")
                case '2': return new Pawn(2, (cell) -> cell.value instanceof Pawn && cell.value.value == 3)
                case '3': return new Pawn(3, (cell) -> cell.value instanceof Pawn && cell.value.value == 2)
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
                if (cell.value instanceof Pawn && cell.value.value == 1) {
                    return " 1"
                }
                if (cell.value instanceof Pawn && cell.value.value == 2) {
                    return " 2"
                }
                if (cell.value instanceof Pawn && cell.value.value == 3) {
                    return " 3"
                }
                if (cell.value instanceof Point) {
                    return " " + cell.value.label
                }
                if (cell.value instanceof Stone) {
                    return " ●"
                }
                if (cell.value instanceof pw.avvero.word.FootPrint) {
                    return " ."
                }
                if (cell.value == null) return " ☐"
                return " " + cell.value.toString()
            }
        }
    }
}
