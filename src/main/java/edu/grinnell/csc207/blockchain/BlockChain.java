package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of monetary
 * transactions.
 */
class Node {

    int initial;
    Block block;
    Node next;

    public Node(Block block) throws NoSuchAlgorithmException {
        //this.block = new Block(0,initial,null);
        this.block = block;
        this.next = null;
    }

    public Block getBlock() {
        return this.block;
    }

    public Node getNext() {
        return this.next;
    }

}

/**
 * Blockchain class
 * @author trent
 */
public class BlockChain {

    private Node first;
    private Node last;
    private int size = 0;

    /**
     * Creates a new block and relevant info
     * @param initial
     * @throws NoSuchAlgorithmException
     */
    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block newBlock = new Block(0, initial, null);
        Node node = new Node(newBlock);
        this.first = this.last = node;
        this.size++;
    }

    /**
     * Performs mining to get the nonce
     * @param amount
     * @return the nonce of a new block 
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public long mine(int amount) throws NoSuchAlgorithmException, Exception {
        Hash newHash = gethash();
        return (new Block(size, amount, newHash).getNonce()); 
    }

    /**
     * Gets the size
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * Appends a block to the blockchain
     * @param blk
     * @throws NoSuchAlgorithmException
     */
    public void append(Block blk) throws NoSuchAlgorithmException {
        if (blk.getPrevHash() != blk.gethash()) {
            throw new IllegalArgumentException("Invalid hash");
        }
        Node newNode = new Node(blk);
        last.next = newNode;
        last = newNode;
        size++;
    }

    /**
     * Removes the last block of the blockchain
     * @return true or false
     */
    public boolean removeLast() {

        if (size == 1) {
            return (false); //single block in chain
        }
        Node trav = first;

        while (trav.next != last) {
            trav = trav.next;
        }
        last = trav;
        last.next = null;
        size--;

        return (true);
    }

    /**
     * Gets the hash
     * @return hash
     * @throws Exception
     */
    public Hash gethash() throws Exception {
        if (last != null) {
            return (last.block.gethash());
        } else {
            throw new Exception("Last is null");
        }
    }

    /**
     * Checks to see if the blockchain is valid
     * @return true or false
     */
    public boolean isValidBlockChain() {

        if (first == null || last == null) {
            return false;
        }

        for (Node trav = first; trav.next != null; trav = trav.next) {
            Block currentBlock = trav.getBlock();

            try {
                String msg = currentBlock.getNum()
                        + currentBlock.getAmount()
                        + (currentBlock.getPrevHash() != null 
                        ? currentBlock.getPrevHash().toString() : "");
                byte[] calculatedHash = Block.calculateHash(msg);
                Hash computedHash = new Hash(calculatedHash);

                if (!computedHash.isValid()) {
                    return false;
                }

            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prints the balance of Alice and Bob
     */
    public void printBalance() { 
        int alice = first.getBlock().amount;
        int bob = 0;
        int aliceTemp = alice;
        int bobTemp = bob;
        Node cur = first.next;
        while (cur != null) {
            if ((bobTemp += -cur.getBlock().amount) < 0) {
                System.out.println("Invalid transaction");
                System.out.println("Alice: " + alice + ", Bob: " + bob);
                return;
            }
            if ((aliceTemp -= -cur.getBlock().amount) < 0) {
                System.out.println("Invalid transaction");
                System.out.println("Alice: " + alice + ", Bob: " + bob);
                return;
            }
            bob += -cur.getBlock().amount;
            alice -= -cur.getBlock().amount;
            cur = cur.next;
        }
        System.out.println("Alice: " + alice + ", Bob: " + bob);

    }

    /**
     * Returns the string version of the blockchain
     * @return str
     */
    @Override
    public String toString() {

        Node trav = first;
        String str = "";

        while (trav.next != null) {
            str = str.concat(trav.block.toString());
            trav = trav.next;
        }
        return (str);

    }

}
