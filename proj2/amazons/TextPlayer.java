package amazons;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import static amazons.Move.mv;

/** A Player that takes input as text commands from the standard input.
 *  @author Jacqueline Chu
 */
class TextPlayer extends Player {

    /** A new TextPlayer with no piece or controller (intended to produce
     *  a template). */
    TextPlayer() {
        this(null, null);
    }

    /** A new TextPlayer playing PIECE under control of CONTROLLER. */
    private TextPlayer(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new TextPlayer(piece, controller);
    }

    private final HashSet<String> commands = new HashSet<>(Arrays.asList(
            "new","dump","seed","manual","auto", "quit"
    ));

    private final String regex1 =
            "[a-z][0-9]+[-][a-z][0-9]+[(][a-z][0-9]+[)]";
    private final String regex2 =
            "[a-z][0-9]+\\s+[a-z][0-9]+\\s+[a-z][0-9]+\\s+";

    @Override
    String myMove() {
        while (true) {
            String line = _controller.readLine();
            if (line == null) {
                return "quit";
            } else if (line.startsWith("#")) {
                continue;
            } else {
                Scanner scanner = new Scanner(line);
                if (scanner.hasNext()) {
                    String cmd = scanner.next();
                    if (commands.contains(cmd) || line.matches(regex1)
                            || line.matches(regex2)) {
                        return line.trim();
                    } else {
                        _controller.reportError("Invalid move. "
                                + "Please try again.");
                        continue;
                    }
                }
            }
        }
    }
}
