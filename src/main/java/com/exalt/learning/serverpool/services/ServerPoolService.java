package com.exalt.learning.serverpool.services;

import com.exalt.learning.serverpool.entities.Request;
import com.exalt.learning.serverpool.entities.Server;
import com.exalt.learning.serverpool.entities.ServerState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServerPoolService {

    static List<Server> allServers = new ArrayList<>();
    int lastServerId = 0;


    public String requestServersState() {
        return prepareOutput();
    }

    public String requestServerCapacity(Request request) throws InterruptedException {

        for(Server server : allServers) {
            if(server.getState() == ServerState.ACTIVE && server.getUnusedCapacity() >= request.getRequestedCapacity()) {
                synchronized (server) {
                    if(server.getUnusedCapacity() >= request.getRequestedCapacity())
                        allocateServer(server, request.getRequestedCapacity());
                    else
                        continue;
                }
                return "Request " + request.getId() + " was served by server " + server.getId() + prepareOutput();
            }
        }

        for(Server server : allServers) {
//            if (server.getState() == ServerState.CREATING && server.getFreeCapacity() >= request.getRequestedCapacity()) {
            if (server.getState() == ServerState.CREATING && server.getUnusedCapacity() >= request.getRequestedCapacity()) {
                synchronized (server) {
                    if(server.getUnusedCapacity() >= request.getRequestedCapacity())
                        allocateServer(server, request.getRequestedCapacity());
                    else
                        continue;
                }
                while(server.getState() == ServerState.CREATING) {
//                    request.wait();
                }
                if(server.getState() != ServerState.CREATING){
                    if(server.getUsedCapacity() == server.getMaxCapacity()) {
                        server.setState(ServerState.FULL);
                    }
//                    request.notify();
                    return "Request " + request.getId() + " was served by server " + server.getId() + prepareOutput();
                }
            }
        }

        Server server = spinNewServer(request);
//        while(request.getState() == Thread.State.WAITING){ }
        if(server.getState() != ServerState.CREATING) {
            return "Request " + request.getId() + " was served by server " + server.getId() + prepareOutput();
        }
        return "";

    }

    public void allocateServer (Server server, int capacity) {
        server.setUsedCapacity(server.getUsedCapacity() + capacity);
        server.setUnusedCapacity(server.getUnusedCapacity() - capacity);
        if(server.getUsedCapacity() == server.getMaxCapacity() && server.getState() == ServerState.ACTIVE) {
            server.setState(ServerState.FULL);
        }
    }

    public Server spinNewServer (Request request) throws InterruptedException {
        Server server = new Server();
//        synchronized (server) {
            server.setId(++lastServerId);
            server.setState(ServerState.CREATING);
            server.setMaxCapacity(100);
            server.setUsedCapacity(request.getRequestedCapacity());
            server.setUnusedCapacity(100 - request.getRequestedCapacity());
//        server.setUsedCapacity(0);
//        server.setUnusedCapacity(100);
//        server.setReservedCapacity(request.getRequestedCapacity());
//        server.setFreeCapacity(100 - request.getRequestedCapacity());

            allServers.add(server);

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            server.setState(ServerState.ACTIVE);
                        }
                    },
                    20000
            );

            while (server.getState() == ServerState.CREATING) {
//            request.wait();
            }
//        notify();
//        }

        return server;
    }

    public static String prepareOutput() {

        String output = "\n";

        for (Server server : allServers) {
            output += server.toString() + "\n";
        }

        return output;
    }

}
