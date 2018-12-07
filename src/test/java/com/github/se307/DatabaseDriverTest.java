package com.github.se307;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

/**
 * @author Oscar Jones
 *
 */
public class DatabaseDriverTest {

	@Test
	public void getDBConnection() {
		Connection conn = DatabaseDriver.getConnection();
		
		assertNotNull(conn);
	}
	
	@Test
	public void pingDatabase() throws SQLException {
		Connection conn = DatabaseDriver.getConnection();
		
		Statement stmnt = conn.createStatement();
		
		stmnt.execute("SELECT * FROM genre");
		
		ResultSet results = stmnt.getResultSet();
		
		assertEquals(2, results.getMetaData().getColumnCount());
	}
}
