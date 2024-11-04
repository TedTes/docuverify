package com.document.verify.docuverify;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class MerkleTree {
    private List<String> leaves;
    public MerkleTree(List<String>  documents) {
        this.leaves = new ArrayList<String>();
      for(String doc: documents) {
        leaves.add(hash(doc));
      }
       
    }
   private String hash(String data) {
    try {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for(byte b: hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    } catch(Exception e ) {
       throw new RuntimeException(e);
    }
   }
   public String getRootHash() {
     List<String> currentLevel = this.leaves;
     while(currentLevel.size() > 1) {
        currentLevel = buildNextLevel(currentLevel);
     }
     return currentLevel.get(0);
   }

   private List<String> buildNextLevel(List<String> nodes) {
    List<String> nextLevel = new ArrayList<String>();
    for(int i=0;i<nodes.size();i+=2) {
        if(i + 1 < nodes.size()) {
            nextLevel.add(hash(nodes.get(i) + nodes.get(i+1)));
        } else {
            nextLevel.add(nodes.get(i));
        }
    }
    return nextLevel;
   }
  public List<String> getProof(int index) {
        List<String> proof = new ArrayList<>();
        int currentIndex = index;
        List<String> currentLevel = this.leaves;
        
        while (currentLevel.size() > 1) {
            List<String> nextLevel = buildNextLevel(currentLevel);
            int pairIndex = (currentIndex % 2 == 0) ? currentIndex + 1 : currentIndex - 1;
            if (pairIndex < currentLevel.size()) {
                proof.add(currentLevel.get(pairIndex));
            }
            currentIndex /= 2;
            currentLevel = nextLevel;
        }
        
        return proof;
    }
}