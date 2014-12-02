package org.apache.zookeeper.leolee;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class MyWatcher implements Watcher {

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		System.out.println(" event:" + event.getState() + " "
                + event.getType() + " " + event.getPath());
	}
	
	public static void main(String[] args) throws IOException{
		try {
			ZooKeeper zk  = new ZooKeeper("192.168.2.104:2181", 3000, new MyWatcher());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.in.read();
	}

}
