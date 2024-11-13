package at.fhtw.swkom.paperless.services;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OCRMessage {
    private String text;
    private String minioFilename;
}
