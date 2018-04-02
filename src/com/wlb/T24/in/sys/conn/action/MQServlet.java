package com.wlb.T24.in.sys.conn.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wlb.T24.in.sys.conn.MQ.MQMsgReciver;
import com.wlb.T24.in.sys.conn.MQ.MQMsgSender;
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
		String qMgrName = properties.getProperty(MQSelections + ".MQ.outQ.qMgrName");
		// MQ send Q Name
		String qName = properties.getProperty(MQSelections + ".MQ.sendQ.qName");
		// MQ receive qName
		String receiveQName = properties.getProperty(MQSelections + ".MQ.recQ.qName");
		// MQ out port
		String port = properties.getProperty(MQSelections + ".MQ.outQ.qPort");
		int mqPort = Integer.parseInt(port);
		System.out.println(hostName + "," + channelName + "," + qMgrName + "," + qName + "," + receiveQName
				+ "," + mqPort);
		
		/*com.ibm.mq.MQEnvironment.hostname = hostName;
	    com.ibm.mq.MQEnvironment.channel = channelName;
	    com.ibm.mq.MQEnvironment.port = mqPort;

	    MQMsgSender mqMsgSender = new MQMsgSender(qMgrName, qName);
	    mqMsgSender.sendMsg(MQMessage);
	    
	    //wait 3s to get MQ response
	    try {
			Thread.sleep(3000);
			MQMsgReciver mQMsgReciver= new MQMsgReciver(qMgrName, qName);
		    mQMsgReciver.receiveMsg();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		PrintWriter out = response.getWriter();
		out.println("MQ response");
		// doGet(request, response);
	}

}
