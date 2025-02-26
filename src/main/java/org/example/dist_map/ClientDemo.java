package org.example.dist_map;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class ClientDemo {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar yourJarFile.jar <clientName> [hazelcastAddress]");
            System.exit(1);
        }

        String clientName = args[0];
        String hazelcastAddress = args.length > 1 ? args[1] : "127.0.0.1:5701";

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setInstanceName(clientName);
        clientConfig.getNetworkConfig().addAddress(hazelcastAddress);

        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        IMap<String, Integer> distributedMap = client.getMap("demoMap");

        System.out.println("Client " + clientName + " connected to " + hazelcastAddress);

        // Creating 1000 values in map
        String key = "key";
        distributedMap.putIfAbsent(key, 0);
        for (int i = 0; i < 1000; i++) {
            distributedMap.put(key, i);
        }

        // Without locking
//        distributedMap.putIfAbsent(key, 0);
//        for ( int k = 0; k < 10_000; k++ ) {
//            int value = distributedMap.get(key);
//            value++;
//            distributedMap.put(key, value);
//        }

        // Optimistic locking
//        distributedMap.putIfAbsent(key, 0);
//        for (int k = 0; k < 1000; k++) {
//            if (k % 10 == 0) System.out.println("At: " + k);
//            for (; ; ) {
//                int oldValue = distributedMap.get(key);
//                int newValue = oldValue;
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                newValue++;
//                if (distributedMap.replace(key, oldValue, newValue))
//                    break;
//            }
//        }

        // Pessimistic locking
        distributedMap.putIfAbsent(key, 0);
        for (int k = 0; k < 10_000; k++) {
            distributedMap.lock(key);
            int value = distributedMap.get(key);
            value++;
            distributedMap.put(key, value);
        }

        System.out.println(clientName + " has written 1000 values");

        client.shutdown();
    }
}
