package com.kan.registry.health.dist.to;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentTO extends TransactionObject{


    private MultipartFile file;

    private String type;

    private String description;

    private boolean newContent;

    private Long customerId;
}
