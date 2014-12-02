package org.apache.zookeeper.leolee;

import java.io.File;
import java.io.IOException;

import javax.management.JMException;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.zookeeper.ZKTestCase;
import org.apache.zookeeper.jmx.ManagedUtil;
import org.apache.zookeeper.server.ServerCnxnFactory;
import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServer;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.persistence.FileTxnSnapLog;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig.ConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZooKeeperServerTest extends ZKTestCase{

	 private static final Logger LOG =
	        LoggerFactory.getLogger(ZooKeeperServerTest.class);
	/**
	 * @param args
	 * @throws ConfigException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ConfigException, IOException {
		// TODO Auto-generated method stub
		try {
            ManagedUtil.registerLog4jMBeans();
        } catch (JMException e) {
            LOG.warn("Unable to register log4j JMX control", e);
        }
        String conf = System.getProperty("user.dir")+File.separator+"conf"+File.separator+"zoo_sample2.cfg";
        ServerConfig config = new ServerConfig();
        config.parse(conf);
        runFromConfig(config);
        
        System.in.read();
	}
	
	/**
     * Run from a ServerConfig.
     * @param config ServerConfig to use.
     * @throws IOException
     */
    public static void runFromConfig(ServerConfig config) throws IOException {
        LOG.info("Starting server");
        FileTxnSnapLog txnLog = null;
        try {
            // Note that this thread isn't going to be doing anything else,
            // so rather than spawning another thread, we will just call
            // run() in this thread.
            // create a file logger url from the command line args
            ZooKeeperServer zkServer = new ZooKeeperServer();

            txnLog = new FileTxnSnapLog(new File(config.getDataLogDir()), new File(
                    config.getDataDir()));
            zkServer.setTxnLogFactory(txnLog);
            zkServer.setTickTime(config.getTickTime());
            zkServer.setMinSessionTimeout(config.getMinSessionTimeout());
            zkServer.setMaxSessionTimeout(config.getMaxSessionTimeout());
            ServerCnxnFactory  cnxnFactory = ServerCnxnFactory.createFactory();
            cnxnFactory.configure(config.getClientPortAddress(),
                    config.getMaxClientCnxns());
            cnxnFactory.startup(zkServer);
            cnxnFactory.join();
            if (zkServer.isRunning()) {
            	LOG.info("Starting Running");
            }
        } catch (InterruptedException e) {
            // warn, but generally this is ok
            LOG.warn("Server interrupted", e);
        } finally {
//            if (txnLog != null) {
//                txnLog.close();
//            }
        }
    }

}
