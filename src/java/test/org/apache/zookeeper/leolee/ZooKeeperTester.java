package org.apache.zookeeper.leolee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.ACL;
import org.junit.Test;

public class ZooKeeperTester {
	MyWatcher watcher = new MyWatcher();
	MyStringCallback string_callback = new MyStringCallback();
	
	String hostPort   = "192.168.2.45:2181";
	ZooKeeper zk;
	public class MyWatcher implements Watcher {

		@Override
		public void process(WatchedEvent event) {
			// TODO Auto-generated method stub
			info(" event:" + event.getState() + " "
	                + event.getType() + " " + event.getPath());
		}
	}
	
	public class MyStringCallback implements AsyncCallback.StringCallback {

		@Override
		public void processResult(int rc, String path, Object ctx, String name) {
			// TODO Auto-generated method stub
			info(String.format("rc:%s,path:%s;ctx:%s;name:%s", rc,path,ctx,name));
		}
		
	}
	
	@Test
	public void connect(){
		try {
			zk  = new ZooKeeper(hostPort, 3000, watcher);
			zk.register(watcher);
			
			System.out.println(zk.getState());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void create(){
		if(zk==null)
			connect();
		try {
			zk.create("/mg/tester/tester1", new byte[0], Ids.OPEN_ACL_UNSAFE,
			        CreateMode.EPHEMERAL);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pause();
	}
	
	
	public void pause(){
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void info(String log){
		System.out.println(log);
	}
	
	public void err(String log){
		System.err.println(log);
	}
}
