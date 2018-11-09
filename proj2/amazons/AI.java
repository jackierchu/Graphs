package amazons;
import java.util.ArrayList;
import java.util.Iterator;
import static amazons.Piece.*;

/** A Player that automatically generates moves.
 *  @author Jacqueline Chu
 */
class AI extends Player {

    /** A position magnitude indicating a win (for white if positive, black
     *  if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** A new AI with no piece or controller (intended to produce
     *  a template). */
    AI() {
        this(null, null);
    }

    /** A new AI playing PIECE under control of CONTROLLER. */
    AI(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new AI(piece, controller);
    }

    @Override
    String myMove() {
        Move move = findMove();
        _controller.reportMove(move);
        return move.toString();
    }

    /** Return a move for me from the current position, assuming there
     *  is a move. */
    private Move findMove() {
        Board b = new Board(board());
        if (_myPiece == WHITE) {
            findMove(b, maxDepth(b),
                    true, 1, -INFTY, INFTY);
        } else {
            findMove(b, maxDepth(b),
                    true, -1, -INFTY, INFTY);
        }
        return _lastFoundMove;
    }

    /** The move found by the last call to one of the ...FindMove methods
     *  below. */
    private Move _lastFoundMove;

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _lastFoundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _lastMoveFound. */
    private int findMove(Board board, int depth, boolean saveMove,
                         int sense, int alpha, int beta) {
        if (depth == 0 || board.winner() != null) {
            return staticScore(board);
        }

        ArrayList<Move> moves = new ArrayList<>();
        Iterator<Move> newMoves = board.legalMoves();
        while (newMoves.hasNext()) {
            Move current = newMoves.next();
            if (current != null) {
                moves.add(current);
            }
        }

        if (sense == 1) {
            int bestSofarAlpha = -INFTY;
            Move bestMove = moves.get(0);
            for (Move m: moves) {
                board.makeMove(m);
                int res = findMove(board, depth - 1,
                        saveMove, -1, alpha, beta);
                bestSofarAlpha = Math.max(res, bestSofarAlpha);
                if (res > bestSofarAlpha) {
                    bestMove = m;
                }
                board.undo();
                alpha = Math.max(alpha, bestSofarAlpha);
                if (beta <= alpha) {
                    break;
                }
            }
            _lastFoundMove = bestMove;
            return bestSofarAlpha;
        } else {
            int bestSofarBeta = INFTY;
            Move bestMove = moves.get(0);
            for (Move m: moves) {
                board.makeMove(m);
                int res = findMove(board, depth - 1,
                        saveMove, -1, alpha, beta);
                bestSofarBeta = Math.min(res, bestSofarBeta);
                if (res < bestSofarBeta) {
                    bestMove = m;
                }
                board.undo();
                beta = Math.min(beta, bestSofarBeta);
                if (beta <= alpha) {
                    break;
                }
            }
            _lastFoundMove = bestMove;
            return bestSofarBeta;
        }
    }

    /** Return a heuristically determined maximum search depth
     *  based on characteristics of BOARD. */
    private int maxDepth(Board board) {
        int N = board.numMoves();
        if (N > 5) {
            return 2;
        }
        return 1;
    }


    /** Return a heuristic value for BOARD. */
    private int staticScore(Board board) {
        Piece winner = board.winner();
        if (winner == BLACK) {
            return -WINNING_VALUE;
        } else if (winner == WHITE) {
            return WINNING_VALUE;
        }

        int whiteNumMoves = numSideMoves(board, WHITE);
        int blackNumMoves = numSideMoves(board, BLACK);

        return whiteNumMoves - blackNumMoves;
    }

    /** Implemented Helper function method for number of side moves.
     * @return Returns the number of side moves.
     * @param board  Board
     * @param side Side */
    private int numSideMoves(Board board, Piece side) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int col = 0; col < board.SIZE; col++) {
            for (int row = 0; row < board.SIZE; row++) {
                if (board.get(col, row) == side) {
                    Piece newPiece = board.get(col, row);
                    Iterator<Move> newMoves =
                            board.legalMoves(newPiece);
                    while (newMoves.hasNext()) {
                        Move current = newMoves.next();
                        if (current != null) {
                            moves.add(newMoves.next());
                        }
                    }
                }
            }
        }
        return moves.size();
    }

}
