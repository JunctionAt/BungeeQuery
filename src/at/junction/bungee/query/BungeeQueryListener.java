package at.junction.bungee.query;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

import com.google.gson.Gson;

import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.config.ServerInfo;

public class BungeeQueryListener implements Runnable {
	private int port;
	private BungeeQuery plugin;
	private ServerSocket incoming;

	public BungeeQueryListener(int port, BungeeQuery plugin){
		this.port = port;
		this.plugin = plugin;
	}
	public void run(){
		try { //Bind to 25566, if we can't do that, die
			incoming = new ServerSocket(port);
		} catch (IOException e) {
			plugin.getLogger().severe("BungeeQuery could not bind to port 25566 - disabling");
			return;
		}
		while(true){
			try {
				Socket clientSocket = incoming.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				Scanner in = new Scanner(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
				String query = in.nextLine();
				if (query.equals("PLAYERS")){
					HashMap<String, ArrayList<String>> PlayerMap = new HashMap<String, ArrayList<String>>();
					for (ServerInfo server : plugin.getProxy().getServers().values()){
						ArrayList<String> playerNames = new ArrayList<String>();
						for (ProxiedPlayer pl : server.getPlayers()){
							playerNames.add(pl.getDisplayName());
						}
						PlayerMap.put(server.getName(), playerNames);
						out.write((new Gson()).toJson(PlayerMap));
						out.flush();
					}
				}
				in.close();
				out.close();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}
}
