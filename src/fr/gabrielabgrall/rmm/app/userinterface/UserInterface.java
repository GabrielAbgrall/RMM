package fr.gabrielabgrall.rmm.app.userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import fr.gabrielabgrall.rmm.app.userinterface.listener.UserInterfaceListener;
import fr.gabrielabgrall.rmm.command.AuthCommand;
import fr.gabrielabgrall.rmm.command.SubscribeCommand;
import fr.gabrielabgrall.rmm.network.Client;
import fr.gabrielabgrall.rmm.utils.Debug;

public class UserInterface {

    public static final String VERSION = "0.1";
    
    protected Client client;

    protected JFrame win;
    protected Container top;
    protected Container center;
    protected Container left;
    protected Container right;
    protected Container bottom;

    protected JLabel errLabel;

    protected XYSeries cpuDataset = new XYSeries("CPU Load");

    public UserInterface() {
        displayApp();
    }

    public void displayApp() {
        win = new JFrame("RMM - User Interface");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(1920, 1080);
        
        initTop();
        initCenter();
        initLeft();
        initRight();
        initBottom();
        
        win.getContentPane().add(top, BorderLayout.NORTH);
        win.getContentPane().add(center, BorderLayout.CENTER);
        win.getContentPane().add(left, BorderLayout.LINE_START);
        win.getContentPane().add(right, BorderLayout.LINE_END);
        win.getContentPane().add(bottom, BorderLayout.SOUTH);
        
        win.setVisible(true);
    }

    public void initTop() {
        top = new Container();
        top.setLayout(new GridLayout(1, 3));

        JPanel errPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        errLabel = new JLabel();
        errLabel.setForeground(Color.RED);


        JPanel connPanel = new JPanel();
        JLabel hostLabel = new JLabel("Host: ");
        JTextField hostField = new JTextField(12);
        JLabel portLabel = new JLabel("Port: ");
        JTextField portField = new JTextField(6);
        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    String host = hostField.getText();
                    int port = Integer.parseInt(portField.getText());
                    connect(host, port);
                    hideError();
                } catch (NumberFormatException err) {
                    displayError("Wrong port format");
                    connPanel.repaint();
                } 
            }
        });

        connPanel.add(hostLabel);
        connPanel.add(hostField);
        connPanel.add(portLabel);
        connPanel.add(portField);
        connPanel.add(connectButton);

        errPanel.add(errLabel);

        top.add(new JPanel());
        top.add(connPanel);
        top.add(errPanel);
    }

    public void initCenter() {
        center = new Container();
    }

    public void initLeft() {
        left = new Container();
        left.setLayout(new GridBagLayout());
    }

    public void initRight() {
        right = new Container();
    }

    public void initBottom() {
        bottom = new Container();
    }

    public void displayConnectedClients(String[] clients) {
        left.removeAll();
        left.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.gridx = 0;
        for (int i=0; i<clients.length; i++) {
            String c = clients[i];
            JButton btn = new JButton(c);
            btn.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    subscribeToClientData(c);
                }
            });
            constraints.gridy = i;
            left.add(btn, constraints);
        }
        left.revalidate();
        win.repaint();
    }

    public void subscribeToClientData(String clientLogin) {
        clearCpuDataset();
        client.sendCommand(new SubscribeCommand(clientLogin));
        Debug.log("Subscribed to ", clientLogin, "'s data");
    }

    public void registerCpuLoad(long time, Double cpuLoad) {
        cpuDataset.add(time, cpuLoad*100);
    }

    public void clearCpuDataset() {
        cpuDataset.clear();
    }

    public void refreshGraph() {
        center.removeAll();
        center.setLayout(new FlowLayout());

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(cpuDataset);

        JFreeChart chart = ChartFactory.createXYLineChart(
            "CPU Load",
            "Time (ms)",
            "CPU Load (%)",
            dataset
        );

        center.add(new ChartPanel(chart));

        center.revalidate();
        win.repaint();
    }

    public void connect(String host, int port) {
        if(client != null && client.isConnected()) client.disconnect();

        this.client = new Client("UserInterface", host, port);

        client.getEventManager().registerListener(new UserInterfaceListener(this));
        client.start();

        client.connect();
        client.sendCommand(new AuthCommand(client.getName(), "bonjour", Client.VERSION));
    }

    public Client getClient() {
        return client;
    }

    public void displayError(String error) {
        errLabel.setText(error);
        errLabel.repaint();
    }

    public void hideError() {
        displayError("");
    }
}
