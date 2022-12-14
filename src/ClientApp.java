import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp{

    private boolean haveConnect = false;
    UdpUnicastServer server;
    UdpUnicastClient client;

    ExecutorService executorService;
    private JPanel mainPanel;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField dataFieldTextField;
    private JButton translateButton;
    private JButton connectionButton;
    private JLabel resultLabel;
    private JLabel connectLabel;


    public ClientApp() {
        connectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (haveConnect)
                {
                    connectLabel.setText("СВЯЗИ НЕТ");
                    executorService.shutdownNow();
                    server.interrupt();
                    client.interrupt();
                    haveConnect = false;

                    translateButton.setEnabled(false);

                }
                else {
                    connectLabel.setText("СВЯЗЬ ЕСТЬ");
                    translateButton.setEnabled(true);
                    int port = 50001;
                    server = null;
                    try {
                        server = new UdpUnicastServer(port);
                    } catch (SocketException ex) {
                        throw new RuntimeException(ex);
                    }
                    client = new UdpUnicastClient(port);

                    executorService = Executors.newFixedThreadPool(2);
                    executorService.submit(client);
                    executorService.submit(server);

                    haveConnect = true;

                }

            }
        });
        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = dataFieldTextField.getText();

                float num = Float.parseFloat(message);

                char method = 0;

                Object selectedItem = comboBox2.getSelectedItem();
                if ("мм".equals(selectedItem)) {
                    num *= 0.001;
                } else if ("см".equals(selectedItem)) {
                    num *= 0.01;
                } else if ("м".equals(selectedItem)) {
                    num *= 1;
                } else if ("км".equals(selectedItem)) {
                    num *= 1000;
                } else if ("д".equals(selectedItem)) {
                    num *= 0.0254;
                }

                selectedItem = comboBox1.getSelectedItem();
                if ("мм".equals(selectedItem)) {
                    method = '1';
                } else if ("см".equals(selectedItem)) {
                    method = '2';
                } else if ("м".equals(selectedItem)) {
                    method = '3';
                } else if ("км".equals(selectedItem)) {
                    method = '4';
                } else if ("д".equals(selectedItem)) {
                    method = '5';
                }

                message = String.valueOf(num);
                message = makeMessage(message, method);

                client.sendMessage(message);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                resultLabel.setText(client.messageToShow);
            }
        });

        int port = 50001;
        server = null;
        try {
            server = new UdpUnicastServer(port);
        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        }
        client = new UdpUnicastClient(port);

        executorService = Executors.newFixedThreadPool(2);
        executorService.submit(client);
        executorService.submit(server);

        haveConnect = true;
    }

    private String makeMessage(String num, char method){

        num = method + num;

        return num;

    }

    public static void main(String[] args) {
        //Просто чтобы всё работало

        JFrame frame = new JFrame("App");

        frame.setSize(400,250);
        frame.setUndecorated(true);

        frame.setContentPane(new ClientApp().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}


