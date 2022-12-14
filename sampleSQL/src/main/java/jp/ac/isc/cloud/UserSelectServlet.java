package jp.ac.isc.cloud;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UserSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// DB接続に使用するクラス
		Connection users = null;
		try {

			// MySQL用のJDBCドライバーのクラスをロードする
			users = DBConnection.openConnection();

			// レコードを管理する配列用意
			ArrayList<Member> list = new ArrayList<Member>();

			// SQLを実行するためのクラスを用意
			Statement state = users.createStatement();// ← ①

			// SELECTした結果を入れるクラスを用意
			ResultSet result = state.executeQuery("SELECT * FROM user_table");// ← ②

			while (result.next()) {// ← ③
				String id = result.getString("id");
				String name = result.getString("name");
				String picture = result.getString("picture");

				// Memberクラスに1件ずつレコードを登録
				list.add(new Member(id, name, picture)); // ← ④
			}
			result.close(); // SQLの結果を受け取ったバッファを閉じる //← ⑤
			DBConnection.closeConnection(users, state);
			request.setAttribute("list", list);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/select.jsp");
			rd.forward(request, response);

			// SQL実行時エラーが発生したら、エラーを表示
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
