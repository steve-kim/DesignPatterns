import java.util.*;

public class BinaryTreeCanonical {

  private static Hashtable<Integer,BinaryTreeNode> flyweights = new Hashtable<Integer,BinaryTreeNode>();

  static class BinaryTreeNode {
    int key;
    BinaryTreeNode left, right;
    Integer cachedHash; // can speed up hash function computation hugely

    public BinaryTreeNode(int k, BinaryTreeNode l, BinaryTreeNode r) {
      this.key = k;
      this.left = l;
      this.right = r;
      this.cachedHash = hashCode();

    }

    @Override
    public boolean equals(Object o) {
       return true;
    }

    @Override
    public int hashCode() {
        //System.out.println("hashCode function called");
        int totalIntegerValues = Integer.MAX_VALUE;
        int hash = 0;
        int leftHash = 0;
        int rightHash = 0;

        hash = key * 31;

        if (left != null)
            leftHash = left.hashCode();

        if (right != null)
            rightHash = right.hashCode();

        return hash + leftHash + rightHash;
    }

  }

  static BinaryTreeNode getCanonical(BinaryTreeNode n) {
    BinaryTreeNode temp = null;
    int hashedCode = n.hashCode();
    if (flyweights.isEmpty()) {
        System.out.println("Flyweight list is empty");
        flyweights.put(hashedCode,n);
    }


    if (flyweights.containsKey(n.hashCode()))
        temp = flyweights.get(hashedCode);
    else {
        System.out.println("Adding another flyweight...");
        flyweights.put(n.hashCode(), n);
        temp = flyweights.get(hashedCode);
    }

    return temp;
  } 

  static int numberOfFlyweightNodes() {
    return flyweights.size();
  }

}
