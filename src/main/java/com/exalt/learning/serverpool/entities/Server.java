package com.exalt.learning.serverpool.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Server {

    int id;
    ServerState state;
    int maxCapacity;
    int usedCapacity;
    int unusedCapacity;
    int reservedCapacity;
    int freeCapacity;

    public String toString() {
        return "Server " + this.getId() + " has free capacity of " + this.getUnusedCapacity();
    }


}
