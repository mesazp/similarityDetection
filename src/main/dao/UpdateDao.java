package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import util.DbUtil;

public class UpdateDao {
	/**
	 * 添加url
	 * 
	 * @param con
	 * @param url
	 * @throws Exception
	 */
	public static int Update(Connection con, String flag, String url, String table)
			throws Exception {
		String sql = "update " + table + " set flag ='"+flag+"' where url='" + url+ "'";
		System.out.println(sql);
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.addBatch(sql);
		pstmt.executeBatch();
		pstmt.close();
		JOptionPane.showMessageDialog(null, "更新成功");
		return 1;
	}
	
	public static void main(String[] args)
	{
		DbUtil dbUtil = new DbUtil();
		try {
			UpdateDao.Update(dbUtil.getCon(), "1","http://baidu.com", "urlblack");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
