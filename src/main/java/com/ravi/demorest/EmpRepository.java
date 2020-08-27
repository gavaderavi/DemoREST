package com.ravi.demorest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpRepository {

	// List<Employee> e;
	Connection con = null;

	public EmpRepository() {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ravi", "root", "root");

		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * e = new ArrayList<>(); Employee emp = new Employee(); emp.setId(111);
		 * emp.setName("Ravi"); emp.setRating(10);
		 * 
		 * Employee emp1 = new Employee(); emp1.setId(112);
		 * emp1.setName("Jason"); emp1.setRating(20);
		 * 
		 * e.add(emp); e.add(emp1);
		 */ }

	public List<Employee> getEmp() {
		List<Employee> e = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from employee");
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt(1));
				emp.setName(rs.getString(2));
				emp.setRating(rs.getInt(3));
				e.add(emp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return e;
	}

	public Employee getEmp(int id) {

		Employee emp = new Employee();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from employee where id=" + id);
			if (rs.next()) {
				emp.setId(rs.getInt(1));
				emp.setName(rs.getString(2));
				emp.setRating(rs.getInt(3));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return emp;
	}

	public void create(Employee e1) {
		try {
			PreparedStatement st = con.prepareStatement("insert into employee values(?,?,?)");
			st.setInt(1, e1.getId());
			st.setString(2, e1.getName());
			st.setInt(3, e1.getRating());
			st.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void update(int id, Employee e1) {
		try {
			PreparedStatement st = con.prepareStatement("update employee set name=?,rating=? where id=?");
			st.setString(1, e1.getName());
			st.setInt(2, e1.getRating());
			st.setInt(3, id);
			st.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void delete(int id) {

		PreparedStatement st;
		try {
			st = con.prepareStatement("delete from employee where id=?");
			st.setInt(1, id);
			st.executeUpdate();
			System.out.println("Data deleted Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
