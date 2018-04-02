package com.wlb.T24.in.sys.conn.MQ;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import com.ibm.mq.MQC;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.wlb.T24.in.sys.conn.in.MsgReceiver;

public class MQMsgReciver implements MsgReceiver {
	private static Logger logger = Logger.getLogger(MQMsgReciver.class);
	private String qMgrName;
	private String qName;
	private MQQueueManager qMgr;
	private MQQueue mqQueue;
	private MQGetMessageOptions pmo;
	private int openOptions;
	private String retMessage;

	public MQMsgReciver(String qMgrName, String qName) {
		this.qMgrName = qMgrName;
		this.qName = qName;
		init();
	}

	protected void init() {
		this.openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT | MQC.MQOO_INQUIRE;
		this.pmo = new MQGetMessageOptions();
		try {
			this.qMgr = new MQQueueManager(this.qMgrName, 0);
		} catch (Exception e) {
			logger.error("Error connecting to queue manager " + this.qMgrName);
			logger.error(e, e);
			FileOutputStream fos = null;
			PrintWriter pw = null;
			try {
				fos = new FileOutputStream("log/receivemessage.err");
				pw = new PrintWriter(fos);
				pw.write("Error connecting to queue manager " + this.qMgrName);
				pw.flush();
			} catch (Exception e1) {
				logger.error(e1);
			} finally {
				try {
					if (pw != null) {
						pw.close();
					}
					if (fos != null) {
						fos.close();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			logger.error(e, e);
			throw new RuntimeException("Error connecting to queue manager " + this.qMgrName);
		}
		try {
			this.mqQueue = this.qMgr.accessQueue(this.qName, this.openOptions);
		} catch (Exception e) {
			logger.error("Error connecting to queue " + this.qName);
			logger.error(e, e);
			throw new RuntimeException("Error connecting to queue " + this.qName);
		}
	}

	public String receiveMsg() {
		int i = 0;
		try {
			Thread.sleep(3 * 1000);
		} catch (InterruptedException e1) {
			logger.error(e1);
		}
		do {
			try {
				mqQueue = qMgr.accessQueue(qName, openOptions, null, null, null);
				MQMessage mqMessage = new MQMessage();
				int depth = mqQueue.getCurrentDepth();
				byte[] data;
				if (depth > 1) {
					logger.info("Some Old return message found in Q:" + qName);
					logger.info("The Q Deepth is:" + depth);
					for (int tempCT = 0; tempCT < depth - 1; tempCT++) {
						MQMessage mqMessage1 = new MQMessage();
						mqQueue.get(mqMessage1, pmo);
						byte[] data1 = new byte[mqMessage1.getDataLength()];
						mqMessage1.readFully(data1);
						String retMessage1 = new String(data1);
						logger.info("Old Q message :" + retMessage1);
					}
				}
				mqQueue.get(mqMessage, pmo);
				data = new byte[mqMessage.getDataLength()];
				mqMessage.readFully(data);
				String retMessage = new String(data);
				logger.info("retMessage:" + retMessage);
				if (retMessage.contains("<field name=\"BFE.PROCESS.CODE\" mv=\"1\" sv=\"1\">0000")) {
					FileOutputStream recSuc = new FileOutputStream("log/receivemessage.out");
					PrintWriter pwRecSuc = new PrintWriter(recSuc);
					pwRecSuc.write(retMessage);
					pwRecSuc.flush();
					pwRecSuc.close();
					recSuc.close();
				} else {
					FileOutputStream recErr = new FileOutputStream("log/receivemessage.err");
					PrintWriter pwRecErr = new PrintWriter(recErr);
					pwRecErr.write("CNAPS can't be stopped, please contact supporter.");
					pwRecErr.flush();
					pwRecErr.close();
					recErr.close();
					logger.info("CNAPS can't be stopped, please contact supporter.");
				}
				return retMessage;
			} catch (Exception e) {
				logger.error(e.toString());
				logger.error(e.getMessage());
				logger.error(e);
				try {
					Thread.sleep(30 * 1000);
					if (i == 3) {
						FileOutputStream recErr = new FileOutputStream("log/receivemessage.err");
						PrintWriter pwRecErr = new PrintWriter(recErr);
						pwRecErr.write("can't receive MQ messsage after 120 seconds. Please contact supporter.");
						logger.info("can't receive MQ messsage after 120 seconds. Please contact supporter.");
						pwRecErr.flush();
						pwRecErr.close();
						recErr.close();
					}
				} catch (Exception e1) {
					logger.error(e1);
					logger.error(e1.toString());
					logger.error(e1.getMessage());
				}
			}
			i++;
		} while (i < 4);
		return retMessage;
	}
}
