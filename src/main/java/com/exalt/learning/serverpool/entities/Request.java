package com.exalt.learning.serverpool.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Request {

    private long id;
    private int requestedCapacity;
}
