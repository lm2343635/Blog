package edu.kit.limeng.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FileServlet")
public class FileServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private String task;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		task=request.getParameter("task");
		switch (task)
		{
		case "download":
			download(request, response);
			break;

		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}
	
	private void download(HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		String rootPath=getServletConfig().getServletContext().getRealPath("/");
		String filePath=rootPath+"file";
		String fileName=request.getParameter("fileName");
		download(filePath, fileName, response);
	}
	
	private void download(String filePath,String fileName,HttpServletResponse response) throws UnsupportedEncodingException 
	{
		FileInputStream in=null;
		ServletOutputStream out=null;
		response.setContentType("application/x-msdownload; charset=UTF-8");
		response.setHeader("Content-disposition","attachment; filename="+new String(fileName.getBytes("UTF-8"),"iso8859-1"));
		try
		{
			in=new FileInputStream(filePath+"/"+fileName);
			out=response.getOutputStream();
			out.flush();
			int aRead=0;
			while((aRead=in.read())!=-1&in!=null)
				out.write(aRead);
			out.flush();
			in.close();
			out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}

