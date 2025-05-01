package com.sidecar.demo.entity;

import lombok.Data;

@Data
public class TodoItem {
    private Long id;
    private String task;
    private boolean completed;
}
