import java.util.Observable;
/**
 *  @author Josh Hug
 */

public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private Maze maze;
    private boolean[] newStack;
    private int cycle = -1;
    private int[] newEdgeTo;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        newStack = new boolean[m.V()];
        for ( boolean x : newStack) {
            x = false;
        }
    }

    @Override
    public void solve() {
        announce();
        depthSearch(0);

        for(int i=0; i < maze.V(); i++) {
            marked[i] = false;
        }

        if (cycle == -1) {
            return;
        }

        int itr = edgeTo[cycle];
        while(itr != cycle){
            marked[itr] = true;
            itr = edgeTo[itr];
        }

        marked[cycle] = true;
        announce();
    }

    private void depthSearch(int item) {
        newStack[item] = true;
        marked[item] = true;
        for(int value : maze.adj(item)) {
            if(newStack[value] == true && edgeTo[item] != value) {
                edgeTo[value] = item;
                cycle = value;
                return;
            }

            if (marked[value] == false) {
                edgeTo[value] = item;
                depthSearch(value);
            }
        }
        newStack[item] = false;
    }
}

