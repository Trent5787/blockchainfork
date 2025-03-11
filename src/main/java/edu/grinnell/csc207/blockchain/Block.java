package edu.grinnell.csc207.blockchain;
import java.nio.ByteBuffer;
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
    String transaction;
    
    
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        
        mineBlock();
    }
    
    private void mineBlock() throws NoSuchAlgorithmException {
        String msg = "";
        String newNum = Integer.toString(num);
        String newAmount = Integer.toString(amount);
        msg = msg.concat(newNum);
        msg = msg.concat(newAmount);
        msg = msg.concat(transaction);
        String newPrevHash = prevHash.toString();

        if (prevHash != null) {
            msg = msg.concat(newPrevHash);
        }

        byte[] hash = calculateHash(msg);
        Hash newhash = new Hash(hash);

        int incr = 0;
        incr++;
        
        while (true) {
            if (newhash.isValid()) {
                num++;
                amount += amount;
                prevHash = newhash;
                nonce = incr;
                return;
            } 
        }

    }
    
    public static byte[] calculateHash(String msg) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("sha-256");
    md.update(msg.getBytes());
    byte[] hash = md.digest();
    return hash;
}

    
    public Block(int num, int amount, Hash prevHash, long nonce) { //is this right?
        //no mining. Just computation of hash like above.
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;
    }
    
    public int getNum() {
        return(num);
    }
    
    public int getAmount() {
        return(amount);
    }
    
    public long getNonce() {
        return(nonce);
    }
    
    public Hash getPrevHash() {
        return(prevHash);
    }
    
    public Hash gethash() {
        return(curBlock);
    }
    
    public String toString() {
        return("Block" + num + "(Amount: " + amount + ", Nonce: " + nonce + ", prevHash: " +
                prevHash + ", hash: " + curBlock + ")");
    }
    
}

