package com.xiar.xiac_main_test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainIncommingThread implements Runnable {

	@Override
	public void run() {
		Socket socket;
		try {
			Main.serverSocket = new ServerSocket(Main.PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (!Thread.currentThread().isInterrupted()) {
			try {
				socket = Main.serverSocket.accept();
				Main.peers.add(socket); // instead of using
										// a list to store
										// peer sockets,just
										// add the new peers
										// ip to a list
										// if his user
										// signature is new,
										// else update
										// previous entry
										// for same user
				IncommingCommunicationThread commthread = new IncommingCommunicationThread(
						socket);
				new Thread(commthread).start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
