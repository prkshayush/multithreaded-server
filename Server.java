import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    public Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try{
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hello from Server");
                toClient.close();
                clientSocket.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        };
    }
    public static void main(String[] args) {
        int port = 8080;
        Server server = new Server();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server listening on port " + port);
            while (true) {
                Socket acceptSocket = serverSocket.accept();
                Thread thread = new Thread(()->server.getConsumer().accept(acceptSocket));
                thread.start();
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
