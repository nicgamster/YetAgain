import java.io.*;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) throws IOException {
        DatagramSocket client = new DatagramSocket();
        InetAddress add = InetAddress.getByName("localhost");
        String str = "Hello world";
        byte[] buffer = str.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, add, 4160);
        client.send(packet);

        client.close();
    }
}
