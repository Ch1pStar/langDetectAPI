package com.langDetect;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;

import org.apache.nutch.util.NutchConfiguration;

import org.apache.nutch.analysis.lang.LanguageIdentifier;



public class LanguageServlet extends HttpServlet
{
	private static LanguageIdentifier _identifier;

	public void init() throws ServletException
	{
		_identifier = new LanguageIdentifier(new Configuration());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.execute(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.execute(request, response);
	}

	protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type","text/xml");

		String query = request.getParameter("text");
		String language = "unknown";

		if ( query != null && query.length() > 0 )
		{
			language = _identifier.identify(query);
		}

		StringBuffer b = new StringBuffer();
		b.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		b.append("<languagequery guess=\""+language+"\">\n");
		b.append("</languagequery>\n");

		response.getWriter().write(b.toString());
	}
}
