package com.demo.chapter1.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import com.demo.chapter1.entity.Todo;
import com.demo.chapter1.service.HelloWorldLocal;

@WebServlet({ "/home", "/add", "/edit", "/delete" })
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	@EJB
	HelloWorldLocal helloWorldService;

	public MyServlet() {
		super();
		// TODO Auto-generated constructor stub

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getServletPath();
		response.setContentType("text/html;charset=UTF-8");
		if (action.equals("/home")) {
			List<Todo> todos = helloWorldService.findAll();

			request.setAttribute("todos", todos);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
			rd.forward(request, response);
		} else if (action.equals("/delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Todo todo = helloWorldService.findById(id);
			if(todo != null) {
				System.out.println("DELETE job status: " + helloWorldService.delete(todo));
			}
			
			loadData(response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getServletPath();
		response.setContentType("text/html;charset=UTF-8");
		if (action.equals("/add")) {
			String title = request.getParameter("title");
			if (title != null && title.length() > 0) {
				Todo todo = new Todo();
				todo.setTitle(request.getParameter("title"));
				System.out.println("ADD status: " + helloWorldService.add(todo));

				loadData(response);
			}
		} else if (action.equals("/edit")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String title = request.getParameter("title");
			Todo todo = new Todo();
			todo.setId(id);
			todo.setTitle(title);

			System.out.println("EDIT status: " + helloWorldService.edit(todo));

			loadData(response);
		}
	}

	private void loadData(HttpServletResponse response) throws IOException {
		List<Todo> todos = helloWorldService.findAll();
		PrintWriter out = response.getWriter();
		for (Todo o : todos) {
			out.println("<li class=\"col\">\r\n" + "					<div class=\"view\">\r\n"
					+ "						<label>" + o.getTitle()
					+ "</label> <button class=\"btn-destroy btn\" onclick=\"deleteTodo(" + o.getId() + ")\">\r\n"
					+ "						<i class=\"fa-solid fa-trash-can destroy-icon\">\r\n"
					+ "							</i>\r\n" + "						</button>\r\n"
					+ "					</div>\r\n"
					+ "							<input id=\"editId\" type=\"text\" class=\"id-edit\" value=\""
					+ o.getId() + "\" name=\"id\">\r\n"
					+ "							<input id=\"editTitle\" type=\"text\" class=\"form-control edit\"\r\n"
					+ "								value=\"" + o.getTitle()
					+ "\" onkeyup=\"event.keyCode === 13 && editTodo(this, " + o.getId() + ")\" name=\"title\">\r\n"
					+ "				</li>");
		}
	}

}
