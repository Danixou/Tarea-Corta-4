import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.*;

import java.io.*;

public class Ventana{
    JFrame frame;
    JLabel blinker;
    JPanel panel;

    boolean activo = false;

    boolean respuestaServidor;

    Socket client;
    ServerSocket server;
    ObjectInputStream input;

    public Ventana(){

        frame = new JFrame("Tarea Programada POO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        blinker = new JLabel();
        blinker.setPreferredSize(new Dimension(100, 100));
        blinker.setOpaque(true);
        blinker.setBackground(Color.WHITE);

        panel = new JPanel(new BorderLayout());
        panel.add(blinker, BorderLayout.WEST);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);


        blink();

    }

    private void blink(){
        Thread hilo = new Thread(()->{
            while(true){
                //OJO, se necesita esta pausa
                System.out.println(activo);
                if (respuestaServidor){
                    try {
                        blinker.setBackground(Color.YELLOW);
                        Thread.sleep(500);
                        blinker.setBackground(Color.WHITE);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {   
                        e.printStackTrace();
                    }
                }
            }
        });
        hilo.start();
        while(true){
            abrirConexion();
        }

    }

    public void correrConexion(){
        while(true){
            abrirConexion();
        }
    }

    public void abrirConexion(){
        try{
            server = new ServerSocket(5555);
            client = server.accept();
            input = new ObjectInputStream(client.getInputStream());
            respuestaServidor = (Boolean)input.readObject();
            input.close();
            client.close();
            server.close();
            imprimir();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private void imprimir(){
        System.out.println(respuestaServidor);
        if(activo == true){
            activo = false;
        }
        else{
            activo = true;
        }
        
    }

}