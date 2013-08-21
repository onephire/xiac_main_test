package com.xiar.xiac_main_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class IncommingCommunicationThread implements Runnable {

	private Socket clientSocket;
	private BufferedReader input;

	public IncommingCommunicationThread(Socket socket) {
		clientSocket = socket;
		try {
			input = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()) {
			try {
				String read = input.readLine();
				
				Main.updateConversationHandler.post(new updateConversationThread(read));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
