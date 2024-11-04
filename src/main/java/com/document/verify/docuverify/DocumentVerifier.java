import java.security.MessageDigest;
import java.util.List;


public class DocumentVerifier {

    private static String hash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes("UTF-8"));
            StringBuilder  hexString = new StringBuilder();
            for(byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean verify(String document, String root, List<String> proof) {
        String hash = hash(document);
        for(String sibling : proof) {
            hash = hash + sibling;
            hash = hash(hash);
        }
        return hash.equals(root);
    }
}