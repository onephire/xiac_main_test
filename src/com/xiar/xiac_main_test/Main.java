package com.xiar.xiac_main_test;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main extends Activity {

	public static final int PORT = 4;
	public static final String FIRST_PEER = "firstpeer";

	public SharedPreferences prefs;
	public SharedPreferences.Editor editor;

	public static String defaultIP;
	public static ServerSocket serverSocket;
	public static List<Socket> peers;
	public static Handler updateConversationHandler;

	private Thread mainThread = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	private void init() {
		prefs = getSharedPreferences("Prefs", MODE_PRIVATE);
		editor = prefs.edit();

		promptFirstPeer();

		this.mainThread = new Thread(new MainIncommingThread());
	}

	private void promptFirstPeer() {
		Builder builder = new Builder(this,
				AlertDialog.THEME_DEVICE_DEFAULT_DARK);
		builder.setTitle("First Peer");
		builder.setMessage("Is this the first peer?");

		builder.setPositiveButton("Yes", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				editor.putBoolean(FIRST_PEER, true).commit();
			}
		});

		builder.setNegativeButton("No", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				editor.putBoolean(FIRST_PEER, false).commit();
				promptDefaultIP();
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void promptDefaultIP() {
		Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.prompt_default_ip_layout);
		dialog.setTitle("Default IP");

		EditText ipInput = (EditText) dialog.findViewById(R.id.defaultIPText);

		Button ipButton = (Button) dialog
				.findViewById(R.id.defaultIPDialogButton);

		ipButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
