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
   // String transaction;
    
    
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        
        mineBlock();
    }
    
    private void mineBlock() throws NoSuchAlgorithmException {
        String msg = "";
        String newNum = Integer.toString(num);             //Duplication of code here  //Also, need to convert to bytes, instead of ints or longs
        String newAmount = Integer.toString(amount);
        msg = msg.concat(newNum);
        msg = msg.concat(newAmount);
        //msg = msg.concat(transaction);

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
    
    public static byte[] calculateHash(String msg) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("sha-256");
    md.update(msg.getBytes());
    byte[] hash = md.digest();
    return hash;
}

    
    public Block(int num, int amount, Hash prevHash, long nonce) { 
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

