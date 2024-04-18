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
        schema                            | moves || result
        // knight vs knight
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ⚒""" | 1     || """. ⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ⚒ ."""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ⚒""" | 3     || """. . . ⚔ ☐ ☐ ☐ ☐ ☐ ☐ ⚒ . . ."""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ⚒""" | 6     || """. . . . . . ⚔ ⚒ . . . . . .""" // meet
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ⚒""" | 11    || """. . . . . . ⚔ ⚒ . . . . . .""" // almost
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ⚒""" | 12    || """. . . . . . † † . . . . . .""" // done
        """⚔ ☐ ● ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ⚒""" | 12    || """⚔ ☐ ● ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ⚒""" // done
        // knight vs archer
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ↑""" | 1     || """. ⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ↑ ."""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ↑""" | 2     || """. . ⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ↑ . ."""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ↑""" | 3     || """. . . ⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ↑ . ."""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ↑""" | 4     || """. . . . ⚔ ☐ ☐ ☐ ☐ ☐ ☐ ↑ . ."""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ↑""" | 5     || """. . . . . ⚔ ☐ ☐ ☐ ☐ ☐ ↑ . ."""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ↑""" | 6     || """. . . . . . ⚔ ☐ ☐ ☐ ☐ ↑ . ."""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ↑""" | 7     || """. . . . . . . ⚔ ☐ ☐ ☐ ↑ . ."""
        """⚔ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ↑""" | 8     || """. . . . . . . † ☐ ☐ ☐ ↑ . ."""
        """⚔ ☐ ● ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ⚒""" | 1     || """⚔ ☐ ● ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ⚒"""
        """⚔ ☐ ● ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ↑""" | 1     || """⚔ ☐ ● ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ☐ ↑"""
    }

    def wordFactory = new Function<Character, WordObject>() {
        @Override
        WordObject apply(Character ch) {
            switch (ch) {
                case '⚔': return new Knight("red", Aligned.findEnemyAndFight("red"))
                case '⚒': return new Knight("green", Aligned.findEnemyAndFight("green"))
                case '↑': return new Archer("green", Aligned.findEnemyAndFight("green"))
                case '●': return new Stone()
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
                if (cell.value instanceof Archer && cell.value.allegiance == "green") {
                    return " ↑"
                }
                if (cell.value instanceof FootPrint) {
                    return " ."
                }
                if (cell.value instanceof Tomb) {
                    return " †"
                }
                if (cell.value instanceof Stone) {
                    return " ●"
                }
                if (cell.value == null) return " ☐"
                return " " + cell.value.toString()
            }
        }
    }
}
