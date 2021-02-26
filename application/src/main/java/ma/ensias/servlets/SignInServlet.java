package ma.ensias.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import ma.ensias.beans.User;
import ma.ensias.forms.SignInForm;

/**
 * Servlet implementation class SingIn
 */

public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String USER_SESSION = "userSession";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignInServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SignInForm form = new SignInForm();
		
		User user = form.connectUser(request);
		
		HttpSession session = request.getSession();
		
		session.setAttribute(USER_SESSION, user);
		
		String message = new Gson().toJson(form.getResult());
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(message);
		out.flush();
	}

}
