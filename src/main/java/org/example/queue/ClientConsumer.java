package org.example.queue;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.collection.IQueue;
import com.hazelcast.core.HazelcastInstance;

public class ClientConsumer {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: ClientConsumer <clientName>");
            return;
        }
        String clientName = args[0];

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setInstanceName(clientName);

        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        IQueue<Integer> queue = client.getQueue("queue");

        while (true) {
            int item = queue.take();
            System.out.println(clientName + " Consumed: " + item);
            if (item == -1) {
                queue.put(-1);
                break;
            }
        }
        System.out.println(clientName + " Consumer Finished!");
        client.shutdown();
    }
}
