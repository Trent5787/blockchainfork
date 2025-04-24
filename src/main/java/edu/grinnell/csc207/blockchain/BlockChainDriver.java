package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {

    /**
     * Help template
     */
    public static void helpTemplate() {
        System.out.println("Valid commands:");
        System.out.println("    mine: discovers the nonce for a given transaction");
        System.out.println("    append: appends a new block onto the end of the chain");
        System.out.println("    remove: removes the last block from the end of the chain");
        System.out.println("    check: checks that the block chain is valid");
        System.out.println("    report: reports the balances of Alice and Bob");
        System.out.println("    help: prints this list of commands");
        System.out.println("    quit: quits the program");
    }

    /**
     * The main entry point for the program.
     *
     * @param args the command-line arguments
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, Exception {
        //checks and setting of intial value
        if (args.length > 1) {
            System.out.println("Only one command line argument: the initial dollar amount");
            return;
        }
        int initial = Integer.parseInt(args[0]);
        if (initial < 0) {
            System.out.println("Initial value cannot be less than 0");
            return;
        }

        BlockChain newBlockChain = new BlockChain(initial);
        Scanner scanner = new Scanner(System.in);

        //Printing and looking for command input
        while (true) {
            System.out.println(newBlockChain.toString());
            System.out.print("Command? ");
            String input = scanner.nextLine();

            if ("mine".equals(input)) {
                System.out.print("Amount transferred? ");
                int amount = Integer.parseInt(scanner.nextLine());
                long nonceVal;
                nonceVal = newBlockChain.mine(amount);
                System.out.println("amount = " + amount + ", nonce = " + nonceVal);

            } else if ("appends".equals(input)) {
                System.out.print("Amount transferred?");
                int amount = Integer.parseInt(scanner.nextLine());
                System.out.print("Nonce?");
                int nonce = Integer.parseInt(scanner.nextLine());
                Block blk = new Block(newBlockChain.getSize(), amount, 
                        newBlockChain.gethash(), nonce);
                newBlockChain.append(blk);

            } else if ("remove".equals(input)) {
                newBlockChain.removeLast();

            } else if ("check".equals(input)) {
                System.out.println(newBlockChain.isValidBlockChain());

            } else if ("report".equals(input)) {
                newBlockChain.printBalance();

            } else if ("help".equals(input)) {
                helpTemplate();

            } else if ("quit".equals(input)) {
                return;

            }
        }
    }
}
