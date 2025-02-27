import java.io.*;
import java.net.*;
import java.util.*;

public class Application {
    private static final int PORT = 8080;
    private static final List<String> users = new ArrayList<>(10);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Сервер запущен на порту " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleRequest(clientSocket);
        }
    }

    private static void handleRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String request = in.readLine();
            if (request == null) return;
            System.out.println("Запрос: " + request);

            if (request.startsWith("GET /users")) {
                sendResponse(out, users.toString());
            } else if (request.startsWith("POST /users?name=")) {
                String name = request.split("=")[1].split(" ")[0];
                users.add(name);
                sendResponse(out, "User added: " + name);
            } else {
                sendResponse(out, "404 Not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendResponse(PrintWriter out, String body) {
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/plain");
        out.println("Content-Length: " + body.length());
        out.println();
        out.println(body);
    }
}
