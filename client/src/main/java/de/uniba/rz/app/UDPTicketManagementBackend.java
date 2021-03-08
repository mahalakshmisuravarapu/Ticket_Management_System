package de.uniba.rz.app;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import de.uniba.rz.entities.Constants;
import de.uniba.rz.entities.Message;
import de.uniba.rz.entities.Priority;
import de.uniba.rz.entities.Status;
import de.uniba.rz.entities.Ticket;
import de.uniba.rz.entities.TicketException;
import de.uniba.rz.entities.Type;

//Client side implementation for UDP
public class UDPTicketManagementBackend implements TicketManagementBackend {

	String ipAddress; // IP Address of the server to which the client tries to connect
	Integer port; // Port of the server to which the client tries to connect
	AtomicInteger id = new AtomicInteger(); // Atomic integer increments in atomic or thread safe manner, won't fail during concurrent increments

	public UDPTicketManagementBackend(String ipAddress, Integer port) {

		this.ipAddress = ipAddress;
		this.port = port;

	}

	// Method to send message
	public Message sendMessage(Object message) {

		System.out.println("\t [Sender]: Trying to send message '");
		Message response = null;

		// Initializing datagram socket
		try (DatagramSocket sock = new DatagramSocket(null)) {

			// Create address of the recipient
			SocketAddress serverAddress = new InetSocketAddress(ipAddress, port); // creating socket address with IP address and port of the server																			// address and port of server

			// Connect to the server
			sock.connect(serverAddress);

			// Create packet
			// Data is always sent as bytes or byte arrays across the network. 
			// Since complex objects cannot be directly converted to byte array, they have to be serialized first.
			ObjectOutput oo = new ObjectOutputStream(bStream);
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			oo.writeObject(message);
			oo.close();

			// Convert object stream to byte array
			byte[] serializedMessage = bStream.toByteArray();

			// Creating a datagram packet which is sent across the network with the data
			DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length, serverAddress);

			// Send packet
			sock.send(packet);

			// Byte array created for response data
			byte[] receiveData = new byte[8192];

			// Datagram packet created for response data
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			// Response recieved
			sock.receive(receivePacket);

			// Data is extracted from datagram packet
			byte[] recievedBytes = receivePacket.getData();

			// Same as sending data, reverse the process
			ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(recievedBytes));
			response = (Message) iStream.readObject();
			iStream.close();

			sock.close();

		} catch (IOException e) {

			// Dummy exception handling
			e.printStackTrace();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		}

		return response;

	}

	@Override
	public void triggerShutdown() {

	}

	@Override
	// Method to create new ticket
	public Ticket createNewTicket(String reporter, String topic, String description, Type type, Priority priority) throws TicketException {

		Ticket ticket = new Ticket(id.getAndIncrement(), reporter, topic, description, type, priority);
		Message Message = new Message(ticket, null, Constants.STORE_TICKET);
		sendMessage(Message);

		return ticket;

	}

	@Override
	// Method to get all tickets
	public List<Ticket> getAllTickets() throws TicketException {

		List<Ticket> tickets = new ArrayList<>();
		Message Message = new Message(null, null, Constants.GET_ALL_TICKETS);
		Message response = sendMessage(Message);
		tickets.addAll(response.getResponse());

		return tickets;

	}

	@Override
	// Method to get tickets by id
	public Ticket getTicketById(int id) throws TicketException {

		List<Ticket> tickets = getAllTickets();
		Ticket ticket = null;

		if (!tickets.isEmpty()) {

			ticket = tickets.stream().filter(t -> t.getId() == id).collect(Collectors.toList()).get(0);

		}

		return ticket;

	}

	@Override
	// Method to accept the ticket created
	public Ticket acceptTicket(int id) throws TicketException {

		Ticket ticketToModify = getTicketById(id);

		if (ticketToModify.getStatus() != Status.NEW) {

			throw new TicketException(
					"Can not reject Ticket as it is currently in status " + ticketToModify.getStatus());

		}

		Ticket ticket = new Ticket(id, null, null, null, null, null, Status.ACCEPTED);
		Message message = new Message(ticket, null, Constants.UPDATE_TICKET_STATUS);
		Message reply = sendMessage(message);

		return reply.getTicket();

	}

	@Override
	// Method to reject the ticket created
	public Ticket rejectTicket(int id) throws TicketException {

		Ticket ticketToModify = getTicketById(id);

		if (ticketToModify.getStatus() != Status.NEW) {

			throw new TicketException(
					"Can not reject Ticket as it is currently in status " + ticketToModify.getStatus());

		}

		Ticket ticket = new Ticket(id, null, null, null, null, null, Status.REJECTED);
		Message message = new Message(ticket, null, Constants.UPDATE_TICKET_STATUS);
		Message reply = sendMessage(message);

		return reply.getTicket();

	}

	@Override
	// Method to close accepted tickets
	public Ticket closeTicket(int id) throws TicketException {

		Ticket ticketToModify = getTicketById(id);

		if (ticketToModify.getStatus() != Status.ACCEPTED) {

			throw new TicketException(
					"Can not reject Ticket as it is currently in status " + ticketToModify.getStatus());

		}

		Ticket ticket = new Ticket(id, null, null, null, null, null, Status.CLOSED);
		Message message = new Message(ticket, null, Constants.UPDATE_TICKET_STATUS);
		Message reply = sendMessage(message);

		return reply.getTicket();
	}

}
