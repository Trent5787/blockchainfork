package edu.grinnell.csc207.blockchain;

import java.util.Arrays;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {
    private byte[] hash;

    public Hash(byte[] data) {
        this.hash = data.clone();
    }
    
    public byte[] getData() {
        return(hash.clone());
    }
    
    public boolean isValid() {
        if ((hash[0] == 0) && (hash[1] == 0) && (hash[2] == 0)) {
            return (true);
        } else {
            return (false);
        }
    }
    
    public String toString() {
     //convert byte arr into unsigned int
     StringBuilder sb = new StringBuilder();
     for(int i = 0; i < hash.length; i++) {
         byte cur = hash[i];
         sb.append(String.format("%02x",Byte.toUnsignedInt(cur)));
     }
     return(sb.toString());
    }
    
    public boolean equals(Object other) {
        if (other instanceof Hash) {
            Hash o = (Hash) other;
            return(Arrays.equals(this.hash,o.hash)); 
        }
        return false;
    }
}
