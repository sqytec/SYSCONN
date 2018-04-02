package com.wlb.T24.in.sys.conn.action;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wlb.T24.in.sys.conn.util.Configuration;

/**
 * Servlet implementation class MQServlet
 */
public class MQServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MQServlet() {
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
		System.out.println("------:" + request);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String MQMessage = request.getParameter("MSGBODY");
		String MQSelections = request.getParameter("MQSelections");
		System.out.println("MQMessage:" + MQMessage);
		System.out.println("MQSelections:" + MQSelections);
		Properties properties = Configuration.getProperites();
		// MQ host name
		String hostName = properties.getProperty(MQSelections + ".Host.Name");
		// MQ channel name
		String channelName = properties.getProperty(MQSelections + ".MQ.ChannelName");
		// MQ outQ qMgrName
		String outQqMgrName = properties.getProperty(MQSelections + ".MQ.outQ.qMgrName");
		// MQ send Q Name
		String sendQName = properties.getProperty(MQSelections + ".MQ.sendQ.qName");
		// MQ receive qName
		String receiveQName = properties.getProperty(MQSelections + ".MQ.recQ.qName");
		// MQ out port
		String mqPort = properties.getProperty(MQSelections + ".MQ.outQ.qPort");
		System.out.println(hostName + "," + channelName + "," + outQqMgrName + "," + sendQName + "," + receiveQName
				+ "," + mqPort);
		// doGet(request, response);
	}

}
