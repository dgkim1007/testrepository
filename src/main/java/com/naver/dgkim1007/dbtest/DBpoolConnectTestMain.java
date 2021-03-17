package com.naver.dgkim1007.dbtest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBpoolConnectTestMain {

	public static void main(String[] args) {
		DBConnectionManager db = DBConnectionManager.getInstance();
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT count(*) FROM member";
		try {
			cn = db.getConnection();
			ps = (PreparedStatement) cn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getInt(1));
			}
		} catch (Exception e) {
			System.out.println("error:----->" + e.getMessage());
		}
	}
}
