//import java.io.IOException;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//
//public class UDPTest {
//    EchoClient client;
//
//    //@Before
//    public void setup() throws SocketException, UnknownHostException {
//        new EchoServer().start();
//        client = new EchoClient();
//    }
//
//    //@Test
//    public void whenCanSendAndReceivePacket_thenCorrect() throws IOException {
//        String echo = client.sendEcho("hello server");
//        assertEquals("hello server", echo);
//        echo = client.sendEcho("server is working");
//        assertFalse(echo.equals("hello server"));
//    }
//
//    //@After
//    public void tearDown() throws IOException {
//        client.sendEcho("end");
//        client.close();
//    }
//}