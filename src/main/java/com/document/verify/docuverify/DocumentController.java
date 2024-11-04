package com.document.verify.docuverify;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    @Autowired
    private DocumentService documentService;
    @GetMapping({"","/"})
    public String home() {
        return "hello world";
    }
    @PostMapping("/initialize")
    public String initializeMerkleTree(@RequestBody List<String> documents) {
        return documentService.initializeTree(documents);
    }
    @GetMapping("/proof/{index}")
    public List<String> getProof(@PathVariable int index) {
        return documentService.getProofForDocument(index);
    }
    @PostMapping("/verify")
    public boolean verifyDocument(@RequestParam String document,
                                   @RequestParam String rootHash,
                                     @RequestBody List<String> proof) {
    return documentService.verifyDocument(document,proof,rootHash);
                                     }
}