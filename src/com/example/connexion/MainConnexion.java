package com.example.connexion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainConnexion extends Activity implements OnClickListener {
	BufferedReader in;
	PrintWriter out;
	TextView text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_connexion);
		((Button)this.findViewById(R.id.button)).setOnClickListener(this);
		text = (TextView)this.findViewById(R.id.textView);
		text.setText("ouverture");	
		
	}


	@Override
	public void onClick(View v) {
		TCPServer server = new TCPServer();
		TCPClient client = new TCPClient();
		server.start();
		client.start();
		((Button)this.findViewById(R.id.button)).setOnClickListener(this);

	
		

	}
	
	public class TCPServer extends Thread {
		@Override 
		public void run(){
			try{
				ServerSocket s = new ServerSocket(8080,0,InetAddress.getLocalHost());
				Socket cli = s.accept();
				in = new BufferedReader(new InputStreamReader(cli.getInputStream()));
				String mes = in.readLine();
				text.setText(mes);	
				
				out = new PrintWriter(cli.getOutputStream());
				out.println("serveur du serveur");
				out.flush();
			
			s.close();
			cli.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	public class TCPClient extends Thread {
		@Override 
		public void run(){
			try {
				Socket s = new Socket(InetAddress.getLocalHost().getHostAddress(),8080);
				out = new PrintWriter(s.getOutputStream());
				out.println("client du Clent");
				out.flush();
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String mes = in.readLine();
				text.setText(mes);	
				setContentView(text);
				s.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
