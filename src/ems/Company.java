package ems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.*;

public class Company {

	private HashMap<Integer, Manager> managerList = new HashMap<Integer, Manager>();

	private int branchId = 1;
	private HashMap<Integer, Branch> branchList = new HashMap<Integer, Branch>();
	private OrderPool orderPool;
	private Date companyClock;


	/**
	 * while company's init, create a user with full priviledges.
	 */
	private Company() {
		this.orderPool = OrderPool.getInstance();

		Manager superuser = new Manager(0, "superuser", "123456", "nil", 0);
		this.managerList.put(superuser.getId(), superuser);
		this.companyClock = new Date();

	}

	private static Company instance = new Company();

	public static Company getInstance() {
		return instance;
	}


	public void addNewManager(int id, String name, String password, String gender, int status) {
		Manager m1 = new Manager(id, name, password, gender, status);

		this.managerList.put(m1.getId(), m1);
	}

	public void changeManagerStatus(int id, int status) {
		return;
	}


	public Manager getManagerById(int id) {
		return this.managerList.get(id);
	}

	// Pengze Liu 2017-Nov-3
	public int createOrder(String itemName, Customer sender, Customer receiver) {
		// TODO generate path
		ArrayList<Position> path = new ArrayList<>();
		// TODO check whether ID is correctly assigned
		int ID = orderPool.getInstance().getCurrentOrder() + 1;
		return OrderPool.getInstance().addOrderToList(
				new Order(ID, itemName, sender, receiver, path));
	}

	// TODO implement this method (Pengze Liu 2017-Nov-3)
	public static Branch getBranchByLocation(Position position) {
		return null;
	}

	public Branch addBranch(String name) {
		int id = this.branchId++;
		Branch branch = new Branch(id, name, null); // TODO implement location
		return branchList.put(id, branch);
	}

	public Branch removeBranch(int id) {
		return branchList.remove(id);
	}

	public Order searchOrder(int id) {
		return orderPool.getOrderById(id);
	}

	public Order searchOrder(String name) {
		return orderPool.getOrderByName(name);
	}

	/**
	 * @return type:long, return the time after the company is created(in millisecond)
	 */
	public long getTime() {
		return new Date().getTime() - this.companyClock.getTime();
	}
}