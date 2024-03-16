package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Question;

@WebServlet(urlPatterns = {"/addQuestions","/submitAnswer"})
public class AddQuestion extends HttpServlet{
	

	private static List<Question> questionList=new LinkedList<>();
    static ArrayList<Question> list=null;
	private Connection connection;
	
	@Override
	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet","root","sql123");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path=req.getServletPath();
		switch (path) {
		case "/addQuestions":
			String button=req.getParameter("operation");
			
			if(button.equals("ADD QUESTIONS")) {
				addQuestions(req, resp);
			}else if(button.equals("SUBMIT PAPER")){
				if(questionList.size()>0) {
					addQuestionToTheDataBase(req, resp);
					questionList.removeAll(questionList);
				}
			}else {
				try {
					displayQuestion(req,resp);
				} catch (SQLException | ServletException | IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case "/submitAnswer":
			checkAnswer(req,resp);
			break;
		}
		
		
	}
	
	private void checkAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int count=0;
		ArrayList<String> userAns=new ArrayList<String>();
		for(Question data:list) {
			String userAnswer=req.getParameter(data.getQuestionId()+"");
			userAns.add(userAnswer);
			if(userAnswer.equals(data.getAnswer())) {	
				count++;
			}
		}
		RequestDispatcher rd=req.getRequestDispatcher("Result.jsp");
		req.setAttribute("displayList", list);
		req.setAttribute("userAns", userAns);
		System.out.println(count);
		rd.forward(req, resp);
	}

	private void displayQuestion(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
		String displayQuery="select * from question_stack";
		Statement stmt=connection.createStatement();
		ResultSet rs=stmt.executeQuery(displayQuery);
	    list=new ArrayList<Question>();
		Question question;
		while(rs.next()) {
			question=new Question();
			question.setQuestionId(rs.getInt(1));
			question.setQuestionPaperTitle(rs.getString(2));
			question.setQuestion(rs.getString(3));
			question.setOption1(rs.getString(4));
			question.setOption2(rs.getString(5));
			question.setOption3(rs.getString(6));
			question.setOption4(rs.getString(7));
			question.setAnswer(rs.getString(8));
			question.setDifficultyLevel(rs.getString(9));
			list.add(question);
		}
		RequestDispatcher rd=req.getRequestDispatcher("Display.jsp");
		req.setAttribute("QuestionList", list);
		rd.forward(req, resp);
	}

	private void addQuestions(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String questionPaperTitle=req.getParameter("QuestionPaperTitle");
		String question=req.getParameter("Question");
		String option1=req.getParameter("Option1");
		String option2=req.getParameter("Option2");
		String option3=req.getParameter("Option3");
		String option4=req.getParameter("Option4");
		String difficultyLevel=req.getParameter("difficulty-level");
		String answerKey=req.getParameter("AnswerKey");
	
		
		questionList.add(new Question(questionPaperTitle,question,option1,option2,option3,option4,answerKey,difficultyLevel));
		
		resp.setContentType("text/html");
		PrintWriter pw=resp.getWriter();
		RequestDispatcher rd=req.getRequestDispatcher("AddQuestions.html");
		
		pw.print("<div align=center>");
		pw.print("<h5 style='color:green;'>QUESTION ADDED SUCCESSFULLY</h5>");
		pw.print("<div>");
		rd.include(req, resp);
		
		System.out.println(questionList.size());
	}

	private void addQuestionToTheDataBase(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String insertQuery="insert into question_stack values(?,?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement pstm=connection.prepareStatement(insertQuery);
			
			for(Question temp:questionList) {
				pstm.setInt(1, 0);
				
				pstm.setString(2, temp.getQuestionPaperTitle());
				pstm.setString(3, temp.getQuestion());
				pstm.setString(4, temp.getOption1());
				pstm.setString(5, temp.getOption2());
				pstm.setString(6, temp.getOption3());
				pstm.setString(7, temp.getOption4());
				pstm.setString(8, temp.getAnswer());
				pstm.setString(9, temp.getDifficultyLevel());
				
				pstm.addBatch();
			}
			
			int[] count=pstm.executeBatch();
			
			resp.setContentType("text/html");
			PrintWriter pw=resp.getWriter();
			RequestDispatcher rd=req.getRequestDispatcher("AddQuestions.html");
			
			pw.print("<div align=center>");
			pw.print("<h5 style='color:green;'>"+count.length+" QUESTION SAVED SUCCESSFULLY</h5>");
			pw.print("</div>");
			rd.include(req, resp);
			
			System.out.println(questionList.size());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
