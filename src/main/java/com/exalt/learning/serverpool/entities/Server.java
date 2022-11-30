package com.exalt.learning.serverpool.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Server {

    private int id;
    private ServerState state;
    private int maxCapacity;
    private int usedCapacity;
    private int unusedCapacity;

    @Override
    public String toString() {
        return "Server " + this.getId() + " has free capacity of " + this.getUnusedCapacity();
    }


}
