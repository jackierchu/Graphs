import java.util.Observable;
/**
 *  @author Josh Hug
 */

public class MazeDepthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;


    public MazeDepthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    private void dfs(int x) {
        marked[x] = true;
        announce();
        if (x == t) {
            targetFound = true;
        }
        if (targetFound) {
            return;
        }
        for (int w : maze.adj(x)) {
            if (!marked[w]) {
                edgeTo[w] = x;
                announce();
                distTo[w] = distTo[x] ++;
                dfs(w);
                if (targetFound) {
                    return;
                }
            }
        }
    }

    @Override
    public void solve() {
        dfs(s);
    }
}

