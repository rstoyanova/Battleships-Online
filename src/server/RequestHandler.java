package server;

import commandcenter.Controller;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler {
    private static Map<SocketChannel, String> users;
    private static Controller controller;

    private static final String FLAG_USERNAME = "--username";

    public RequestHandler() {
        users = new HashMap<>();
        controller = new Controller();
    }

    public String fulfillRequest(SocketChannel sc, String request) {
        final String LOGIN = "You are logged" + System.lineSeparator();
        if (!handleLogin(sc, request)) {
            String username = users.get(sc);
            return controller.executeCommand(request, username);
        }
        return LOGIN;
    }

    private boolean handleLogin(SocketChannel sc, String request) {
        String cmd = request.split(" ")[0];
        if (cmd.equals(FLAG_USERNAME)) {
            String username = request.split(" ")[1];
            users.put(sc, username);
            return true;
        }
        return false;
    }
}
