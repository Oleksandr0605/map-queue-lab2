package org.example.dist_map;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.io.InputStream;

public class HazelcastNode {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar yourJarFile.jar <nodeName>");
            System.exit(1);
        }
        String nodeName = args[0];

        InputStream configStream = HazelcastNode.class.getResourceAsStream("/hazelcast.xml");
        if (configStream == null) {
            throw new RuntimeException("There is no config file");
        }

        Config config = new XmlConfigBuilder(configStream).build();
        config.setInstanceName(nodeName);

        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        System.out.println("Node " + nodeName + " is running: " + instance.getCluster().getLocalMember());

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
