package amazons;

import ucb.gui2.Pad;

import java.io.IOException;

import java.util.concurrent.ArrayBlockingQueue;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;

import static amazons.Piece.*;
import static amazons.Square.sq;

/** A widget that displays an Amazons game.
 *  @author Jacqueline
 */
class BoardWidget extends Pad {

    /* Parameters controlling sizes, speeds, colors, and fonts. */

    /** Colors of empty squares and grid lines. */
    static final Color
        SPEAR_COLOR = new Color(64, 64, 64),
        LIGHT_SQUARE_COLOR = new Color(238, 207, 161),
        DARK_SQUARE_COLOR = new Color(205, 133, 63);

    /** Locations of images of white and black queens. */
    private static final String
        WHITE_QUEEN_IMAGE = "wq4.png",
        BLACK_QUEEN_IMAGE = "bq4.png";

    /** Size parameters. */
    private static final int
        SQUARE_SIDE = 30,
        BOARD_SIDE = SQUARE_SIDE * 10;

    /** A graphical representation of an Amazons board that sends commands
     *  derived from mouse clicks to COMMANDS.  */
    BoardWidget(ArrayBlockingQueue<String> commands) {
        _commands = commands;
        setMouseHandler("click", this::mouseClicked);
        setPreferredSize(BOARD_SIDE, BOARD_SIDE);

        try {
            _whiteQueen = ImageIO.read(Utils.getResource(WHITE_QUEEN_IMAGE));
            _blackQueen = ImageIO.read(Utils.getResource(BLACK_QUEEN_IMAGE));
        } catch (IOException excp) {
            System.err.println("Could not read queen images.");
            System.exit(1);
        }
        _acceptingMoves = false;
    }

    /** Draw the bare board G.  */
    private void drawGrid(Graphics2D g) {
        g.setColor(LIGHT_SQUARE_COLOR);
        g.fillRect(0, 0, BOARD_SIDE, BOARD_SIDE);

        boolean isLightColor;
        for(int i = 0; i < Board.SIZE; i++){
            isLightColor = i % 2 == 0? true : false;
            for(int j = 0; j < Board.SIZE; j++){
                g.setColor(isLightColor? LIGHT_SQUARE_COLOR : DARK_SQUARE_COLOR);
                g.fillRect(SQUARE_SIDE * i, SQUARE_SIDE * j, SQUARE_SIDE, SQUARE_SIDE);
                isLightColor = !isLightColor;
            }
        }

        for (int k = 0; k <= BOARD_SIDE; k += SQUARE_SIDE) {
            g.drawLine(cx(k), cy(0), cx(k), cy(BOARD_SIDE));
        }
        for (int k = 0; k <= BOARD_SIDE; k += SQUARE_SIDE) {
            g.drawLine(cx(0), cy(k), cx(BOARD_SIDE), cy(k));
        }
    }

    @Override
    public synchronized void paintComponent(Graphics2D g) {
        drawGrid(g);
        for(int i = 0; i < Board.SIZE; i++){
            for(int j = 0; j < Board.SIZE; j++){
                Piece piece = _board.get(i,j);
                if(piece == WHITE || piece == BLACK) {
                    drawQueen(g, sq(i, j), piece);
                }
                else if(piece == SPEAR){
                    g.setColor(SPEAR_COLOR);
                    g.fillRect(cx(i), cy(j), SQUARE_SIDE, SQUARE_SIDE);
                }
            }
        }
    }

    /** Draw a queen for side PIECE at square S on G.  */
    private void drawQueen(Graphics2D g, Square s, Piece piece) {
        g.drawImage(piece == WHITE ? _whiteQueen : _blackQueen,
                    cx(s.col()) + 2, cy(s.row()) + 4, null);
    }

    /** Handle a click on S.*/
    private void click(Square s) {
        Piece current = _board.turn();
        if(_numClicked % 3 == 1){
            if(_board.get(s) == current){
                _from = s;
            }
            else{
                if(_numClicked > 0) _numClicked--;
                warn("Invalid from position for this queen. Please try again");
            }
        }
        else if(_numClicked % 3 == 2){
            _to = s;
            if(_board.isLegal(_from, _to)){
                _to = s;
            }
            else{
                if(_numClicked > 0) _numClicked--;
                warn("Invalid to position for this queen. Please try again");
            }
        }
        else{
            _spear = s;
            if(_board.isLegal(_from, _to, _spear)){
                _spear = s;
                _commands.add(_from + " " + _to + " " + _spear);
                repaint();
                _numClicked = 0;
            }
            else{
                if(_numClicked > 0) _numClicked--;
                warn("Invalid spear position. Please try again");
            }
        }
    }

    private static void warn(String message){
        JOptionPane.showMessageDialog(null, message, "warning", JOptionPane.INFORMATION_MESSAGE);
    }


    /** Handle mouse click event E. */
    private synchronized void mouseClicked(String unused, MouseEvent e) {
        int xpos = e.getX(), ypos = e.getY();
        int x = xpos / SQUARE_SIDE,
            y = (BOARD_SIDE - ypos) / SQUARE_SIDE;
        if (_acceptingMoves
            && x >= 0 && x < Board.SIZE && y >= 0 && y < Board.SIZE) {
            click(sq(x, y));
        }
    }

    /** Revise the displayed board according to BOARD. */
    synchronized void update(Board board) {
        _board.copy(board);
        repaint();
    }

    /** Turn on move collection iff COLLECTING, and clear any current
     *  partial selection.   When move collection is off, ignore clicks on
     *  the board. */
    void setMoveCollection(boolean collecting) {
        _acceptingMoves = collecting;
        repaint();
    }

    /** Return x-pixel coordinate of the left corners of column X
     *  relative to the upper-left corner of the board. */
    private int cx(int x) {
        return x * SQUARE_SIDE;
    }

    /** Return y-pixel coordinate of the upper corners of row Y
     *  relative to the upper-left corner of the board. */
    private int cy(int y) {
        return (Board.SIZE - y - 1) * SQUARE_SIDE;
    }

    /** Return x-pixel coordinate of the left corner of S
     *  relative to the upper-left corner of the board. */
    private int cx(Square s) {
        return cx(s.col());
    }

    /** Return y-pixel coordinate of the upper corner of S
     *  relative to the upper-left corner of the board. */
    private int cy(Square s) {
        return cy(s.row());
    }

    /** Queue on which to post move commands (from mouse clicks). */
    private ArrayBlockingQueue<String> _commands;
    /** Board being displayed. */
    private final Board _board = new Board();

    /** Image of white queen. */
    private BufferedImage _whiteQueen;
    /** Image of black queen. */
    private BufferedImage _blackQueen;

    /** True iff accepting moves from user. */
    private boolean _acceptingMoves;

    private int _numClicked = 0;
    private Square _from;
    private Square _to;
    private Square _spear;
}
