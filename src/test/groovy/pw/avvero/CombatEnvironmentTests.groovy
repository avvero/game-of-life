package pw.avvero

import pw.avvero.board.BoardBordered
import pw.avvero.board.Cell
import pw.avvero.seed.DirectSeed

import spock.lang.Specification
import spock.lang.Unroll

class CombatEnvironmentTests extends Specification {

    // 0 0 | ⛶ > * 0 0
    // 0 0 0 0 0 0 0 0
    // 0 0 0 0 0 0 0 0
    // 0 0 0 0 0 0 0 0
    // 0 0 0 0 0 0 0 0
    // 0 0 0 0 0 0 0 0
    // 0 0 0 0 0 0 0 0
    @Unroll
    def "Test"() {
        when:
        def board = new BoardBordered(8, 8, new NoopState())
        new DirectSeed().initialize(board, 0, board.value().length, 0, board.value()[0].length, () -> zero())
        board.set(0, 2, () -> Cell.of(2, RoleFactory.FGTR))
        board.set(0, 3, () -> Cell.of(2, RoleFactory.TANK))
        board.set(0, 4, () -> Cell.of(2, RoleFactory.RANG))
        board.set(0, 5, () -> Cell.of(2, RoleFactory.MAGE))
        and:
        board.set(i, j, () -> Cell.of(1, RoleFactory.FGTR))
        def current = board.get(i, j)
        CombatEnvironment combatEnvironment = CombatEnvironment.calculate(current, board.neighbours(i, j))
        then:
        combatEnvironment.enemies?.size() == enemies
        combatEnvironment.closeEnemyGroups.get(2)?.size() == closed
        combatEnvironment.rangedEnemyGroups.get(2)?.size() == range
        where:
        i | j || enemies | closed | range
        1 | 0 || 0       | null   | null
        1 | 1 || 2       | 1      | 1
        1 | 2 || 4       | 2      | 2
    }

}
