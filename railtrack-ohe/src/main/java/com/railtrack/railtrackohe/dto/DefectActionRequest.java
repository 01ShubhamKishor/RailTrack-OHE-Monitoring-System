package com.railtrack.railtrackohe.dto;
import lombok.Data;

@Data
public class DefectActionRequest {
    private Long actionById; // kaun action le raha hai
    private Long assignedToId; // kisko assign karna hai (assign ke liye)
    private String remark; // kyu reject/resolve kiya
}
