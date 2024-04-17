package pw.avvero.word

import pw.avvero.BoardTestDisplay
import pw.avvero.Render
import pw.avvero.board.Board
import pw.avvero.board.Cell
import spock.lang.Specification
import spock.lang.Unroll

import java.util.function.Function

import static pw.avvero.BoardTestDisplay.trim

class UnitsFightsTests extends Specification {

    @Unroll
    def "Unit finds target near"() {
        when:
        Board<Object> board = WordConstructor.constructFrom(schema, wordFactory)
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(schema)
        when:
        moves.times { board.nextCycle() }
        then:
        trim(BoardTestDisplay.toString(board, render())) == trim(result)
        where:
        schema                    | moves || result
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ⚒""" | 1     || """. ⚔ ☐ ☐ ☐ ☐ ☐ ☐ ⚒ ."""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ⚒""" | 3     || """. . . ⚔ ☐ ☐ ⚒ . . ."""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ⚒""" | 4     || """. . . . ⚔ ⚒ . . . .""" // after last move
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ⚒""" | 5     || """. . . . ⚔ ⚒ . . . .""" // after hit
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ⚒""" | 6     || """. . . . † † . . . ."""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ⚒""" | 7     || """. . . . † † . . . ."""
    }

    def wordFactory = new Function<Character, WordObject>() {
        @Override
        WordObject apply(Character ch) {
            switch (ch) {
                case '⚔': return new Knight(1, "red", Aligned.findEnemyAndFight("red"))
                case '⚒': return new Knight(1, "green", Aligned.findEnemyAndFight("green"))
                default: return null;
            }
        }
    }

    Render render() {
        return new Render<Cell>() {
            @Override
            String draw(Cell cell) {
                if (cell.value instanceof Knight && cell.value.allegiance == "red") {
                    return " ⚔"
                }
                if (cell.value instanceof Knight && cell.value.allegiance == "green") {
                    return " ⚒"
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
