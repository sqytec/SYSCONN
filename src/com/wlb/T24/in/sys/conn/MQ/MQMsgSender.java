package com.wlb.T24.in.sys.conn.MQ;

import org.apache.log4j.Logger;

import com.ibm.mq.MQC;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.wlb.T24.in.sys.conn.in.MsgSender;

public class MQMsgSender implements MsgSender{

	private static Logger logger = Logger.getLogger(MQMsgSender.class);
	  private String qMgrName;
	  private String qName;
	  private MQQueueManager qMgr;
	  private MQQueue mqQueue;
	  private MQPutMessageOptions pmo;
	  private int openOptions;

	  public MQMsgSender(String qMgrName, String qName)
	  {
	    this.qMgrName = qMgrName;
	    this.qName = qName;
	    init();
	  }

	  protected void init()
	  {
		this.openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT | MQC.MQOO_INQUIRE;

	    this.pmo = new MQPutMessageOptions();
	    try {
	      this.qMgr = new MQQueueManager(this.qMgrName, 0);
	    } catch (Exception e) {
		    logger.error("Error connecting to queue manager " + this.qMgrName);
		    logger.error(e, e);
	      throw new RuntimeException("Error connecting to queue manager " + this.qMgrName);
	    }
	    try
	    {
	      this.mqQueue = this.qMgr.accessQueue(this.qName, this.openOptions, null, null, null);
	      logger.info("Connected to queue " + this.qMgrName + "/" + this.qName);
	    } catch (Exception e) {
	    	logger.error("Error connecting to queue " + this.qName);
	        logger.error(e, e);
	      throw new RuntimeException("Error connecting to queue " + this.qName);
	    }
	  }

	  private void sendBytes(byte[] msgBytes) throws Exception
	  {
	    MQMessage mqMessage = new MQMessage();
	    mqMessage.expiry = -1;
	    mqMessage.persistence = 1;
	    mqMessage.format = "MQSTR   ";
	    mqMessage.write(msgBytes);
	    this.mqQueue.put(mqMessage, this.pmo);
	  }

	  public void sendMsg(String req)
	  {
	    logger.info("Send message:[" + req + "]");
	    System.out.println("sendMessage:"+req);
	    try {
	      String hostMsg = req;
	      byte[] msgBytes = hostMsg.getBytes();
	      sendBytes(msgBytes);
	    }
	    catch (Exception e1)
	    {
	      logger.error(e1, e1);
	      try {
	        try {
	          this.mqQueue.close();
	          this.qMgr.close();
	          this.qMgr.disconnect();
	        } catch (Exception e) {
	          logger.error(e, e);
	        }
	        init();
	      } catch (Exception e2) {
	        logger.error("Failed putting message into queue " + this.qMgrName + "/" + this.qName + ".\n" + req);
	        logger.error(e2, e2);
	        throw new RuntimeException("Failed putting message into queue " + this.qMgrName + "/" + this.qName + ".\n" + req);
	      }
	    }
	  }

}
