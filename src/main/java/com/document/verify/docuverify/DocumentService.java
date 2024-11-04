import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DocumentService {
    private MerkleTree merkleTree;
    private List<String> documents;

    public String initializeTree(List<String> documentData) {
        this.documents = documentData;
        this.merkelTree = new MerkelTree(documents);
        return merkelTree.getRootHash();
    }
    public List<String> getProofForDocument(int index) {
        return merkelTree.getProof(index);
    }
    public boolean verifyDocument(String document , List<String> proof, String rootHash) {
        return DocumentVerifier.verify(document, rootHash,proof);
    }
}