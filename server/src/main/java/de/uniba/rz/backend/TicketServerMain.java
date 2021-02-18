package de.uniba.rz.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.NamingException;

import de.uniba.rz.UDPRemoteAccess;

public class TicketServerMain {

	public static void main(String[] args) throws IOException, NamingException {
		TicketStore simpleTestStore = new SimpleTicketStore();

		List<RemoteAccess> remoteAccessImplementations = getAvailableRemoteAccessImplementations(args);

		// Starting remote access implementations
		for (RemoteAccess implementation : remoteAccessImplementations) {
			implementation.prepareStartup(simpleTestStore);
			new Thread(implementation).start();
		}

		try (BufferedReader shutdownReader = new BufferedReader(new InputStreamReader(System.in))) {
			System.out.println("Press enter to shutdown system.");
			shutdownReader.readLine();
			System.out.println("Shutting down...");

			// Shutting down all remote access implementations
			for (RemoteAccess implementation : remoteAccessImplementations) {
				implementation.shutdown();
			}
			System.out.println("completed. Bye!");
		}
	}

	private static List<RemoteAccess> getAvailableRemoteAccessImplementations(String[] args) {
		List<RemoteAccess> implementations = new ArrayList<>();

		switch (args[0]) {

		case "udp":
			return Arrays.asList(new UDPRemoteAccess(Integer.parseInt(args[1])));

		case "gRPC":
			return Arrays.asList(new GRPCRemoteAccess(Integer.parseInt(args[1])));

		}

		return implementations;

	}
}
