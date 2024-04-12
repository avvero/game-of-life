package pw.avvero;

import java.util.List;

public interface State {
    int calculate(int current, List<int[]> neighbours);
}
