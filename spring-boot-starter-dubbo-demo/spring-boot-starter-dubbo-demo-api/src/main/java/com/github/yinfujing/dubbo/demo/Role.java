package com.github.yinfujing.dubbo.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role implements Serializable {
    private String id;

    private String name;

    private String desc;

    private List<Permission> permissionList;
}
