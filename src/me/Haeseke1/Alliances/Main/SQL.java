package me.Haeseke1.Alliances.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;

public class SQL {
	
	public static Connection connection;
	
	public static boolean SQL;
	
	public static String ip;
	public static String username;
	public static String password;
	public static String dbName;
	
	public synchronized static void openConnection(){
		if(!SQL)return;
		try{
			connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + dbName, username,password);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void forceCloseConnection(){
		try {
			if(connection != null && !connection.isClosed()){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeConnection(){
		try {
			if(connection != null && !connection.isClosed()){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public synchronized static boolean tableContainsData(String table, String column, String value){
		if(!SQL)return false;
		openConnection();
		try{
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + column + "=" + value + ";");
			ResultSet resultset = sql.executeQuery();
			boolean result = resultset.next();
			sql.close();
			resultset.close();
			closeConnection();
			return result;
		}catch (Exception e) {
			e.printStackTrace();
		}
		closeConnection();
		return false;
	}
	
	public synchronized static ResultSet tableGetData(String table, String column, String value){
		if(!SQL)return null;
		openConnection();
		try{
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + column + "=" + value + ";");
			ResultSet resultset = sql.executeQuery();
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				
				@Override
				public void run() {
					try {
						resultset.close();
						sql.close();
						closeConnection();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					
				}
			},20);
			return resultset;
		}catch (Exception e) {
			e.printStackTrace();
		}
		closeConnection();
		return null;
	}
	
	public synchronized static void setDataToTable(String table, String column, String value, String checkColumn, String CheckValue){
		if(!SQL)return;
		openConnection();
		try{
			PreparedStatement sql = connection.prepareStatement("UPDATE " + table +  " SET " + column + "=" + value + " WHERE " + checkColumn + "=" + CheckValue + ";");
			sql.execute();
			sql.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		closeConnection();
	}
	
	public synchronized static void addDataToTable(String table, String value){
		if(!SQL)return;
		openConnection();
		try{
			PreparedStatement sql = connection.prepareStatement("INSERT INTO " + table +  " values (" + value + ");");
			sql.execute();
			sql.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		closeConnection();
	}
	
	public synchronized static void removeTable(String table){
		if(!SQL)return;
		openConnection();
		try{
			PreparedStatement sql = connection.prepareStatement("TRUNCATE TABLE " + table);
			sql.execute();
			sql.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		closeConnection();
	}
	
	public synchronized static ResultSet getTable(String table){
		if(!SQL)return null;
		openConnection();
		try{
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM " + table + ";");
			ResultSet resultset = sql.executeQuery();
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				
				@Override
				public void run() {
					try {
						resultset.close();
						sql.close();
						closeConnection();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					
				}
			},20);
			return resultset;
		}catch (Exception e) {
			e.printStackTrace();
		}
		closeConnection();
		return null;
	}
	
}
