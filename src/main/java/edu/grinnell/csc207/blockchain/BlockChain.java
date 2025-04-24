package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of
 * monetary transactions.
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

public class BlockChain {

    private Node first;
    private Node last;
    private int size = 0;

    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block newBlock = new Block(0,initial,null);
        Node node = new Node(newBlock);
        this.first = this.last = node;
        this.size++;
    }
    
    public long mine(int amount) throws NoSuchAlgorithmException, Exception {
        Hash newHash = gethash();
        return(new Block(size,amount,newHash).getNonce()); //I guess this just returns the nonce
    }
    
    public int getSize() {
        return size;
    }
    
    public void append(Block blk) throws NoSuchAlgorithmException {
        if (blk.getPrevHash() != blk.gethash()) {
            throw new IllegalArgumentException("Invalid hash");
        }
        Node newNode = new Node(blk);
        last.next = newNode;
        last = newNode;
        size++;
    }
    
    public boolean removeLast() {
        
        if (size == 1)
            return(false); //single block in chain
        
        Node trav = first;
        
        while(trav.next != last) {
            trav = trav.next;
        }
        last = trav;
        last.next = null;
        size--;
        
        return(true);
    }
    
    public Hash gethash() throws Exception {
        if (last != null) {
        return(last.block.gethash());
        } else {
            throw new Exception("Last is null");
        }
    }
    
    public boolean isValidBlockChain() {
        
        if (first == null || last == null) {
            return false;
        }
        
        for(Node trav = first; trav.next != null; trav = trav.next) {
           Block currentBlock = trav.getBlock();
           
             try {
                String msg = currentBlock.getNum() + 
                             currentBlock.getAmount() + 
                             (currentBlock.getPrevHash() != null ? currentBlock.getPrevHash().toString() : "");
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
    
    
    public void printBalance() { //traversal to calculate these values
        int Alice = first.getBlock().amount;
        int Bob = 0;
        int AliceTemp = Alice;
        int BobTemp = Bob;
        Node cur = first.next;
        while (cur != null) {
            if ((BobTemp += -cur.getBlock().amount) < 0) {
                System.out.println("Invalid transaction");
               System.out.println("Alice: " + Alice + ", Bob: " + Bob);
                return;
            }
            if ((AliceTemp -= -cur.getBlock().amount) < 0) {
                System.out.println("Invalid transaction");
                System.out.println("Alice: " + Alice + ", Bob: " + Bob);
                return;
            }
            Bob += -cur.getBlock().amount;
            Alice -= -cur.getBlock().amount;
            cur = cur.next;
        }
        System.out.println("Alice: " + Alice + ", Bob: " + Bob);

    }
    
    public String toString() {
        
        Node trav = first;
        String str = "";
        
        while(trav.next != null) {
            str = str.concat(trav.block.toString());
            trav = trav.next;
        }
        return(str);
  
    }
    
    
    
}
