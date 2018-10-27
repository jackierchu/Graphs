package amazons;

// NOTICE:
// This file is a SUGGESTED skeleton.  NOTHING here or in any other source
// file is sacred.  If any of it confuses you, throw it out and do it your way.

import java.util.Collections;
import java.util.Iterator;
import java.util.Formatter;
import java.util.Stack;
import java.util.NoSuchElementException;
import static java.lang.Math.abs;
import static java.lang.Math.max;

import static amazons.Piece.*;
import static amazons.Move.mv;


/** The state of an Amazons Game.
 *  @author
 */
class Board {

    /** The number of squares on a side of the board. */
    static final int SIZE = 10;

    /** Initializes a game board with SIZE squares on a side in the
     *  initial position. */
    Board() {
        init();
    }

    /** Initializes a copy of MODEL. */
    Board(Board model) {
        copy(model);
    }

    /** Copies MODEL into me. */
    void copy(Board model) {
        _moveHistory = model.moveHistory();
        _turn = model.turn();
        _winner = model.winner();
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                board[i][j] = model.board[i][j];
            }
        }
    }

    /** Clears the board to the initial position. */
    void init() {
        board = new Piece[SIZE][SIZE];
        _moveHistory = new Stack<>();
        _turn = WHITE;
        _winner = EMPTY;

        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                board[i][j] = EMPTY;
            }
        }
        board[0][3] = WHITE;
        board[3][0] = WHITE;
        board[6][0] = WHITE;
        board[9][3] = WHITE;
        board[0][6] = BLACK;
        board[3][9] = BLACK;
        board[6][9] = BLACK;
        board[9][6] = BLACK;
    }

    /** Return the moveHistory. */
    Stack<Move> moveHistory() {
        return _moveHistory;
    }

    /** Return the Piece whose move it is (WHITE or BLACK). */
    Piece turn() {
        return _turn;
    }

    /** Return the number of moves (that have not been undone) for this
     *  board. */
    int numMoves() {
        return _moveHistory.size();
    }

    /** Return the winner in the current position, or null if the game is
     *  not yet finished. */
    Piece winner() {
        if(_winner == EMPTY){
            return null;
        }
        else {
            return _winner;
        }
    }

    /** Return the contents the square at S. */
    final Piece get(Square s) {
        int row = s.row();
        int col = s.col();
        return get(col, row);
    }

    /** Return the contents of the square at (COL, ROW), where
     *  0 <= COL, ROW < 9. */
    final Piece get(int col, int row) {
        return board[col][row];
    }

    /** Return the contents of the square at COL ROW. */
    final Piece get(char col, char row) {
        return get(col - 'a', row - '1');
    }

    /** Set square S to P. */
    final void put(Piece p, Square s) {
        put(p, s.col(),s.row());
    }

    /** Set square (COL, ROW) to P. FIXED. */
    final void put(Piece p, int col, int row) {
        board[col][row] = p;
        /** TO DO: Check whether p win this game after this put. */
        _winner = EMPTY;
    }

    /** Set square COL ROW to P. */
    final void put(Piece p, char col, char row) {
        put(p, col - 'a', row - '1');
    }

    /** Return true iff FROM - TO is an unblocked queen move on the current
     *  board, ignoring the contents of ASEMPTY, if it is encountered.
     *  For this to be true, FROM-TO must be a queen move and the
     *  squares along it, other than FROM and ASEMPTY, must be
     *  empty. ASEMPTY may be null, in which case it has no effect. */
    boolean isUnblockedMove(Square from, Square to, Square asEmpty) {
        if(asEmpty == null) {
            if(!from.isQueenMove(to)) {
                return false;
            }

            int dir = from.direction(to);
            int steps = max(abs(from.col() - to.col()), abs(from.row() - to.row()));
            for(int i = 1; i <= steps; i++) {
                Square temp = from.queenMove(dir, i);
                if(board[temp.col()][temp.row()] != EMPTY) {
                    return false;
                }
            }
            return true;
        }
        else {
            if(isUnblockedMove(asEmpty, from, null)) {
                if(asEmpty == to) {
                    return true;
                }
                else {
                    return isUnblockedMove(from, to,null);
                }
            }
            return false;
        }
    }

    /** Return true iff FROM is a valid starting square for a move. */
    boolean isLegal(Square from) {
        int col = from.col();
        int row = from.row();
        Piece current = turn();
        return board[col][row] == current;
    }

    /** Return true iff FROM-TO is a valid first part of move, ignoring
     *  spear throwing. */
    boolean isLegal(Square from, Square to) {
        return isUnblockedMove(from, to, null);
    }

    /** Return true iff FROM-TO(SPEAR) is a legal move in the current
     *  position. */
    boolean isLegal(Square from, Square to, Square spear) {
        return isUnblockedMove(from, to, spear);
    }

    /** Return true iff MOVE is a legal move in the current
     *  position. */
    boolean isLegal(Move move) {
        return isLegal(move.from(), move.to(), move.spear());
    }

    /** Move FROM-TO(SPEAR), assuming this is a legal move. */
    void makeMove(Square from, Square to, Square spear) {
        board[from.col()][from.row()] = EMPTY;
        board[to.col()][to.row()] = this.turn();
        board[spear.col()][spear.row()] = SPEAR;
    }

    /** Move according to MOVE, assuming it is a legal move. */
    void makeMove(Move move) {
        _moveHistory.push(move);
        makeMove(move.from(),move.to(),move.spear());
    }

    /** Undo one move.  Has no effect on the initial board. */
    void undo() {
        if(_moveHistory.empty()) return;
        Move move = _moveHistory.peek();
        board[move.from().col()][move.from().row()] = this.turn();
        board[move.to().col()][move.to().row()] = EMPTY;
        board[move.spear().col()][move.spear().row()] = EMPTY;
        _moveHistory.pop();
    }

    /** Return an Iterator over the Squares that are reachable by an
     *  unblocked queen move from FROM. Does not pay attention to what
     *  piece (if any) is on FROM, nor to whether the game is finished.
     *  Treats square ASEMPTY (if non-null) as if it were EMPTY.  (This
     *  feature is useful when looking for Moves, because after moving a
     *  piece, one wants to treat the Square it came from as empty for
     *  purposes of spear throwing.) */
    Iterator<Square> reachableFrom(Square from, Square asEmpty) {
        return new ReachableFromIterator(from, asEmpty);
    }

    /** Return an Iterator over all legal moves on the current board. */
    Iterator<Move> legalMoves() {
        return new LegalMoveIterator(_turn);
    }

    /** Return an Iterator over all legal moves on the current board for
     *  SIDE (regardless of whose turn it is). */
    Iterator<Move> legalMoves(Piece side) {
        return new LegalMoveIterator(side);
    }

    /** An iterator used by reachableFrom. */
    private class ReachableFromIterator implements Iterator<Square> {

        /** Iterator of all squares reachable by queen move from FROM,
         *  treating ASEMPTY as empty. */
        ReachableFromIterator(Square from, Square asEmpty) {
            _from = from;
            _dir = -1;
            _steps = 0;
            _asEmpty = asEmpty;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return _dir < 8;
        }

        @Override
        public Square next() {
            if(hasNext()) {
                Square nextVisited = _from.queenMove(_dir, _steps);
                if (nextVisited == null) {
                    toNext();
                    return next();
                }

                if (nextVisited != _asEmpty && board[nextVisited.col()][nextVisited.row()] != EMPTY) {
                    toNext();
                    return next();
                }

                _steps++;
                return nextVisited;
            }
            return null;
        }

        /** Advance _dir and _steps, so that the next valid Square is
         *  _steps steps in direction _dir from _from. FIXED */
        private void toNext() {
            _steps = 1;
            _dir ++;
        }

        /** Starting square. */
        private Square _from;
        /** Current direction. */
        private int _dir;
        /** Current distance. */
        private int _steps;
        /** Square treated as empty. */
        private Square _asEmpty;
    }

    /** An iterator used by legalMoves. */
    private class LegalMoveIterator implements Iterator<Move> {

        /** All legal moves for SIDE (WHITE or BLACK). */
        LegalMoveIterator(Piece side) {
            _startingSquares = Square.iterator();
            _spearThrows = NO_SQUARES;
            _pieceMoves = NO_SQUARES;
            _fromPiece = side;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return _startingSquares.hasNext();
        }

        @Override
        public Move next() {
            if(!_spearThrows.hasNext()){
                toNext();
            }
            Square spear = _spearThrows.next();
            return mv(_start, _nextSquare, spear);
        }

        /** Advance so that the next valid Move is
         *  _start-_nextSquare(sp), where sp is the next value of
         *  _spearThrows. */
        private void toNext() {
            if(!_pieceMoves.hasNext()){
                while(_startingSquares.hasNext()){
                    Square startSquare = _startingSquares.next();
                    if(board[startSquare.col()][startSquare.row()] == _fromPiece){
                        _start = startSquare;
                        _hasNext = true;
                        break;
                    }
                }

                if(!_startingSquares.hasNext()){
                    _hasNext = false;
                }

                _pieceMoves = new ReachableFromIterator(_start, null);
            }

            _nextSquare = _pieceMoves.next();
            _spearThrows = new ReachableFromIterator(_nextSquare, _start);
        }

        /** Color of side whose moves we are iterating. */
        private Piece _fromPiece;
        /** Current starting square. */
        private Square _start;
        /** Remaining starting squares to consider. */
        private Iterator<Square> _startingSquares;
        /** Current piece's new position. */
        private Square _nextSquare;
        /** Remaining moves from _start to consider. */
        private Iterator<Square> _pieceMoves;
        /** Remaining spear throws from _piece to consider. */
        private Iterator<Square> _spearThrows;
        /** Has next variable. */
        private boolean _hasNext;
    }

    @Override
    public String toString() {
        Formatter boardFormatter = new Formatter();
        for(int row = SIZE - 1; row >= 0; row--) {
            boardFormatter.format(" ");
            boardFormatter.format(" ");
            for(int col = 0; col < SIZE; col++){
                boardFormatter.format(" " + get(col, row).toString());
            }
            boardFormatter.format("\n");
        }
        return boardFormatter.toString();
    }

    /** An empty iterator for initialization. */
    private static final Iterator<Square> NO_SQUARES =
        Collections.emptyIterator();

    /** Piece whose turn it is (BLACK or WHITE). */
    private Piece _turn;
    /** Cached value of winner on this board, or EMPTY if it has not been
     *  computed. */
    private Piece _winner;
    /** Number of moves that have happened during the game */
    private int _numMoves;
    /** 2D array that have the pieces for every location that is on board */
    private Piece [][] board;
    /** Stack that holds the history of moves */
    private Stack<Move> _moveHistory;

}
