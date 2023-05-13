
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

import javax.swing.JFrame;

import java.io.*;

public class Cliente implements ActionListener{
    //INSTANCIAS
    JFrame frame;
    JLabel blinker;
    JPanel panel;
    JButton button;

    boolean activo = false;

    boolean respuestaServidor = false;

    Socket client;
    ObjectOutputStream output;

    public Cliente(){

        frame = new JFrame("Tarea Programada POO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button = new JButton("Enceder");
        button.addActionListener(this);
        button.setActionCommand("encender");

        panel = new JPanel(new BorderLayout());
        panel.add(button, BorderLayout.EAST);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }

    public void conectar(){
        if(respuestaServidor == true){
            respuestaServidor = false;
        }
        else{
            respuestaServidor = true;
        }
        try{
            client = new Socket("127.0.0.1", 5555);
            output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(respuestaServidor);
            output.flush();
            
            output.close();
            client.close();
            if(respuestaServidor == true){
                System.out.println("Label parpadeando!");
            }
            else{
                System.out.println("Label apagado!");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("encender")){
            button.setText("apagar");
            button.setActionCommand("apagar");
            activo = true;
            conectar();
        }
        else{
            button.setText("encender");
            button.setActionCommand("encender");
            activo = false;
            conectar();
        }
    }

}
