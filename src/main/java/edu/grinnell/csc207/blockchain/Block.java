package edu.grinnell.csc207.blockchain;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A single block of a blockchain.
 */
public class Block {

    int num = 0;
    int amount;
    Hash prevHash = null;
    long nonce = 0;
    Hash curBlock;

    /**
     * Constructor for block
     * @param num
     * @param amount
     * @param prevHash
     * @throws NoSuchAlgorithmException
     */
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;

        mineBlock();
    }
    
    /**
    * Mines the block
    * 
    */
    private void mineBlock() throws NoSuchAlgorithmException {
        String msg = "";
        String newNum = Integer.toString(num);            
        String newAmount = Integer.toString(amount);
        msg = msg.concat(newNum);
        msg = msg.concat(newAmount);


        if (prevHash != null) {
            String newPrevHash = prevHash.toString();
            msg = msg.concat(newPrevHash);
        }
        msg = msg.concat(Long.toString(nonce));

        byte[] hash = calculateHash(msg);
        Hash newhash = new Hash(hash);

        //  int incr = 0;
        while (true) {
            if (newhash.isValid()) {
                return;
            }
            nonce++;
            String newNonce = Long.toString(nonce);
            String msg2 = "";
            String newNum2 = Integer.toString(num);
            String newAmount2 = Integer.toString(amount);
            msg2 = msg2.concat(newNum2);
            msg2 = msg2.concat(newAmount2);
            msg2 = msg2.concat(newNonce);

            byte[] hash2 = calculateHash(msg2);
            newhash = new Hash(hash2);
        }

    }

    /**
     * Calculates and returns the hash
     * @param msg
     * @return hash
     * @throws NoSuchAlgorithmException
     */
    public static byte[] calculateHash(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        md.update(msg.getBytes());
        byte[] hash = md.digest();
        return hash;
    }

    /**
     * Constructor for Block
     * @param num
     * @param amount
     * @param prevHash
     * @param nonce
     * @return void
     */
    public Block(int num, int amount, Hash prevHash, long nonce) {
        //no mining. Just computation of hash like above.
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;
    }

    /**
     * Gets num
     * @return num
     */
    public int getNum() {
        return (num);
    }

    /**
     * Gets the amount
     * @return amount
     */
    public int getAmount() {
        return (amount);
    }

    /**
     * Gets the nonce
     * @return nonce
     */
    public long getNonce() {
        return (nonce);
    }

    /**
     * Gets prevHash
     * @return prevHash
     */
    public Hash getPrevHash() {
        return (prevHash);
    }

    /**
     * Gets curBlock (the hash of that block)
     * @return curBlock
     */
    public Hash gethash() {
        return (curBlock);
    }

    /**
     * prints out a string of info
     * @return string
     */
    @Override
    public String toString() {
        return ("Block" + num + "(Amount: " + amount + ", Nonce: " + nonce + ", prevHash: "
                + prevHash + ", hash: " + curBlock + ")");
    }

}
