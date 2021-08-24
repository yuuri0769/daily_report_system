package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsIineServlet
 */
@WebServlet("/reports/iine")
public class ReportsIineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsIineServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())){
            EntityManager em = DBUtil.createEntityManager();

            Report r = em.find(Report.class, Integer.valueOf(request.getParameter("report_id")));

            int i = r.getiine_count();
            i++;

            r.setiine_count(i);
            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", r.getEmployee().getName()+ "さんの日報にいいね！しました。");
            response.sendRedirect(request.getContextPath() + "/reports/index");
        }
	}

}
