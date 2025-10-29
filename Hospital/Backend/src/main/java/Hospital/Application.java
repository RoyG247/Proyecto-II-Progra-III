package Hospital;

import Hospital.Logic.Server;

public class Application {
    public static void main(String[] args) {
        System.out.println("Hospital Application Started");
        Server server = new Server();
        server.run();
    }
}
