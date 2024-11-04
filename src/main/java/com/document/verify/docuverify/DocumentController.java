import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @PostMapping("/initialize")
    public String initializeMerkleTree(@RequestBody List<String> documents) {
        return documentService.initializeTree(documents);
    }
    @GetMapping("/proof/{index}")
    public List<String> getProof(@pathVariable int index) {
        return documentService.getProofForDocument(index);
    }
    @PostMapping("/verify")
    public boolean verifyDocument(@RequestParam String document,
                                   @RequestParam String rootHash,
                                     @RequestBody List<String> proof) {
    return documentService.verifyDocument(document,proof,rootHash);
                                     }
}