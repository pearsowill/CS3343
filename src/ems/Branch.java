package ems;

import java.util.*;
//import java.util.zip.CheckedOutputStream;
//import javax.print.attribute.standard.PrinterMessageFromOperator;
// wfz &qr
// Refactored by Pengze Liu

public class Branch {


	private int id;
	private String name;
	private Position location;
	// private ArrayList<Branch> neighbour = new ArrayList<Branch>();
	private ArrayList<Courier> freeMan = new ArrayList<Courier>();
	private ArrayList<Courier> outMan = new ArrayList<Courier>();
	private ArrayList<Order> queuingOrders = new ArrayList<Order>();
	private ArrayList<Order> onDelivery = new ArrayList<Order>();
	// private final int capacity;
	private int robinpointer = 0;

	// Refactored by Pengze LIU 2017-Nov-3
	public Branch(int id, String name, Position loc) {
		// TODO: conflict constructor with Manager
		this.id = id;
		this.name = name;
		this.location = loc;
		queuingOrders = new ArrayList<>();
		onDelivery = new ArrayList<>();
		/*
		this.capacity = cap;
		this.neighbour = branches;
		for (int i = 0; i <= this.neighbour.size(); i++) {
			this.queuingOrders.add(new OrderCollection(1)); // we need to initiate with unique id for each neighbor
		}
		*/
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Order getOrder(int id) {
		for (Order order : this.queuingOrders) {
			if (order.getId() == id) {
				return order;
			}
		}
		for (Order order : this.onDelivery) {
			if (order.getId() == id) {
				return order;
			}
		}
		return null;
	}

	public Courier getMan(int id) {
/* TODO: implement getMan*/
		for (Courier m : this.freeMan) {
			if (m.getID() == id) {
				return m;
			}
		}
		for (Courier m : this.outMan) {
			if (m.getID() == id) {
				return m;
			}
		}

		return null;
	}

	// Added by Pengze LIU 2017-Nov-3
	public Position getLocation() {
		return location;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name;
	}

	// Deleted by Pengze LIU 2017-Nov-3
	/*public void updateNeighbour(Branch b) {
		this.neighbour.add(b);
		this.queuingOrders.add(new OrderCollection(1));// with new id
	}*/

	// Refactored by Pengze LIU 2017-Nov-3
	public void checkInOrder(Order order) {
		queuingOrders.add(order);
	}

	// Refactored by Pengze LIU 2017-Nov-3
	public ArrayList<Order> checkOutOrders(Courier courier) { // assign queuing Orders to Courier (FreeMan)
		ArrayList<Order> thingsToSend = new ArrayList<Order>();
		int weight = 1; // this.queuingOrders.get(robinpointer).peek().getWeight();
		while (weight < courier.getCapacity() && thingsToSend.size() > 0) {
			Order newOrder = thingsToSend.get(0);
			thingsToSend.add(newOrder);
			onDelivery.add(newOrder);
			thingsToSend.remove(newOrder);
		}
		this.outMan.add(courier);
		this.freeMan.remove(courier);
		return thingsToSend;
	}

	public void arrive(Courier courier) {
		this.onDelivery.remove(courier.popTopOrder());
		this.outMan.remove(courier);
		this.freeMan.add(courier);
	}

	// Pengze Liu 2017-Nov-2
	private boolean checkLastDelivery(Order order) {
		return order.hasBeenSent();
	}

	// Pengze Liu Added 2017-Nov-3
	public void reportFinished(Order order) {
		onDelivery.remove(order);
	}
}