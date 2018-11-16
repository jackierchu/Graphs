import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */

public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int x;
    private int y;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        x = maze.xyTo1D(sourceX, sourceY);
        y = maze.xyTo1D(targetX, targetY);
        distTo[x] = 0;
        edgeTo[x] = x;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(x);
        while (queue.size() > 0) {
            int peek = queue.peek();
            queue.remove();
            marked[peek] = true;
            announce();

            if (peek == y) {
                targetFound = true;
                return;
            }
            for (int newValue : maze.adj(peek)) {
                if (marked[newValue] == false) {
                    edgeTo[newValue] = peek;
                    announce();
                    distTo[newValue] = distTo[peek] + 1;
                    queue.add(newValue);
                }
            }
        }
    }

    @Override
    public void solve() {
        bfs();
    }
}

