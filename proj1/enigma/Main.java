package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static enigma.EnigmaException.*;



/** Enigma simulator.
 *  @author Jacqueline Chu
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME.
     * @param names is the name */
    private Scanner getInput(String names) {
        try {
            return new Scanner(new File(names));
        } catch (IOException excp) {
            throw error("could not open %s", names);
        }
    }

    /** Return a PrintStream writing to the file named NAME.
     * @param names is the name */
    private PrintStream getOutput(String names) {
        try {
            return new PrintStream(new File(names));
        } catch (IOException excp) {
            throw error("could not open %s", names);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        Machine enigma = readConfig();
        String next = _input.nextLine();

        while (_input.hasNext()) {
            String setting = next;
            if (!setting.contains("*")) {
                throw new EnigmaException("The incorrect setting format");
            }
            setUp(enigma, setting);
            next = (_input.nextLine()).toUpperCase();
            while (!(next.contains("*"))) {
                String result = enigma.convert(next.replaceAll(" ", ""));
                if (result.length() == 0) {
                    _output.println();
                } else {
                    printMessageLine(result);
                }
                if (!_input.hasNext()) {
                    next = "*";
                } else {
                    next = (_input.nextLine()).toUpperCase();
                }
            }
        }
    }


    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {

            String alpha = _config.next();
            if (alpha.contains("(") || alpha.contains(")")
                    || alpha.contains("*")) {
                throw new EnigmaException("Incorrect config format");
            }
            if (alpha.matches("[A-Z]-[A-Z]")) {
                _alphabet = new CharacterRange
                        (alpha.charAt(0), alpha.charAt(2));
            } else {
                _alphabet = new ExtendAlphabetEC(alpha);
            }

            if (!_config.hasNextInt()) {
                throw new EnigmaException("Incorrect config format");
            }
            int numRotors = _config.nextInt();
            if (!_config.hasNextInt()) {
                throw new EnigmaException("Incorrect config format");
            }
            int pawls = _config.nextInt();
            temp = (_config.next()).toUpperCase();
            while (_config.hasNext()) {
                name = temp;
                notches = (_config.next()).toUpperCase();
                _allTheRotors.add(readRotor());
            }
            return new Machine(_alphabet, numRotors, pawls, _allTheRotors);
        } catch (NoSuchElementException excp) {
            throw error("config file is truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            perm = "";
            temp = (_config.next()).toUpperCase();
            while (temp.contains("(") && _config.hasNext()) {
                perm = perm.concat(temp + " ");
                temp = (_config.next()).toUpperCase();
            }
            if (!_config.hasNext()) {
                perm = perm.concat(temp + " ");
            }

            if (notches.charAt(0) == 'M') {
                return new MovingRotor(name, new Permutation(perm, _alphabet),
                        notches.substring(1));
            } else if (notches.charAt(0) == 'N') {
                return new FixedRotor(name, new Permutation(perm, _alphabet));
            } else {
                return new Reflector(name, new Permutation(perm, _alphabet));
            }
        } catch (NoSuchElementException excp) {
            throw error("inadequate rotor description");
        }
    }


    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        String[] set = settings.split(" ");
        if (set.length - 1 < M.numRotors()) {
            throw new EnigmaException("Not enough arguments in the setting");
        }
        String[] rotors = new String[M.numRotors()];
        for (int i = 1; i < M.numRotors() + 1; i++) {
            rotors[i - 1] = set[i];
        }
        for (int i = 0; i < rotors.length - 1; i++) {
            for (int j = i + 1; j < rotors.length; j++) {
                if (rotors[i].equals(rotors[j])) {
                    throw new EnigmaException("Rotor is repeated");
                }
            }
        }

        String steck = "";
        int cycleTobeScanned = rotors.length + 2;
        if (!(cycleTobeScanned >= set.length)) {
            for (int i = rotors.length; i < set.length - 2; i++) {
                steck = steck.concat(set[i + 2] + " ");
            }
        }
        M.insertRotors(rotors);
        if (!M._rotors[0].reflecting()) {
            throw new EnigmaException("First Rotor should be reflector");
        }
        M.setRotors(set[M.numRotors() + 1]);
        M.setPlugboard(new Permutation(steck, _alphabet));
    }


    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        String output = "";
        if (msg.length() < 5) {
            _output.println(msg);
        } else if (msg.length() == 5) {
            _output.println(msg + " ");
        } else {
            int start = 0;
            while (start + 5 < msg.length()) {
                output += msg.substring(start, start + 5) + " ";
                start += 5;
            }
            output += msg.substring(start);
            output += " ";
            if (output.length() % 6 != 0) {
                output = output.trim();
            }
            _output.println(output);
        }
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of the input messages. */
    private Scanner _input;

    /** Source of the machine config. */
    private Scanner _config;

    /** File for encoded and decoded msgs. */
    private PrintStream _output;

    /** Temp string which is set to next value of _config. */
    private String temp;

    /** Name of the current rotor. */
    private String name;

    /** Notches of current rotor. */
    private String notches;

    /** ArrayList containing all the available rotors. */
    private ArrayList<Rotor> _allTheRotors = new ArrayList<>();

    /** Instance variable. */
    private String perm;


}
