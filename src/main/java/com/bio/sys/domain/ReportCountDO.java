package com.bio.sys.domain;

import lombok.Data;

@Data
public class ReportCountDO {

    /** 所属部门ID */
    private String deptId;
    
    private String deptName;
    
    private Integer  countNumber;
}
