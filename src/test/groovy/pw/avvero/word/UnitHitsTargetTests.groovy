package pw.avvero.word

import pw.avvero.BoardTestDisplay
import pw.avvero.Render
import pw.avvero.board.Board
import pw.avvero.board.Cell
import spock.lang.Specification
import spock.lang.Unroll

import java.util.function.Function

import static pw.avvero.BoardTestDisplay.trim

class UnitHitsTargetTests extends Specification {

    @Unroll
    def "Unit hits target"() {
        when:
        Board<Object> board = WordConstructor.constructFrom(schema, wordFactory)
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(schema)
        when:
        moves.times { board.nextCycle() }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(result)
        where:
        schema                          | moves || result
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ 9 ☐ ☐ ☐ 9""" | 6     || """. . . . . . ⚔ ☐ 9 ☐ ☐ ☐ 9"""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ 9 ☐ ☐ ☐ 9""" | 7     || """. . . . . . . ⚔ 9 ☐ ☐ ☐ 9"""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ 9 ☐ ☐ ☐ 9""" | 8     || """. . . . . . . ⚔ 8 ☐ ☐ ☐ 9"""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ 9 ☐ ☐ ☐ 9""" | 9     || """. . . . . . . ⚔ 7 ☐ ☐ ☐ 9"""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ 9 ☐ ☐ ☐ 9""" | 15    || """. . . . . . . ⚔ 1 ☐ ☐ ☐ 9"""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ 9 ☐ ☐ ☐ 9""" | 16    || """. . . . . . . ⚔ 0 ☐ ☐ ☐ 9"""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ 9 ☐ ☐ ☐ 9""" | 17    || """. . . . . . . ⚔ † ☐ ☐ ☐ 9"""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ 9 ☐ ☐ ☐ 9""" | 18    || """. . . . . . . . ⚔ ☐ ☐ ☐ 9"""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ 9 ☐ ☐ ☐ 9""" | 19    || """. . . . . . . . . ⚔ ☐ ☐ 9"""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ 9 ☐ ☐ ☐ 9""" | 20    || """. . . . . . . . . . ⚔ ☐ 9"""
    }

    def wordFactory = new Function<Character, WordObject>() {
        @Override
        WordObject apply(Character ch) {
            switch (ch) {
                case '⚔': return new Knight(1, "red", (cell) -> cell.value instanceof Pell)
                case '!': return new Point("!")
                case '●': return new Stone()
                case '9': return new Pell(9)
                case '†': return new Tomb()
                default: return null;
            }
        }
    }

    Render render() {
        return new Render<Cell>() {
            @Override
            String draw(Cell cell) {
                if (cell.value instanceof Knight) {
                    return " ⚔"
                }
                if (cell.value instanceof Point) {
                    return " " + cell.value.label
                }
                if (cell.value instanceof Stone) {
                    return " ●"
                }
                if (cell.value instanceof Pell) {
                    return " " + cell.value.health
                }
                if (cell.value instanceof FootPrint) {
                    return " ."
                }
                if (cell.value instanceof Tomb) {
                    return " †"
                }
                if (cell.value == null) return " ☐"
                return " " + cell.value.toString()
            }
        }
    }
}
