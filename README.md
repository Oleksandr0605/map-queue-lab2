# Lab2 Hazelcast
### Ivaniuk Oleksandr

## 1. Distributed Map
I have such structure:  
![img_16.png](images/img_16.png)  
It has done using argument of command line:  
![img_17.png](images/img_17.png)  

### 1.1 Data saving
It is the result of writing 1000 numbers into map on 3 nodes.  
![img.png](images/img.png)  
As we can see it is almost evenly distributed.  
  
Also this is safe terminating of nodes which resulting in saving data on other nodes.  
![img_1.png](images/img_1.png)
![img_2.png](images/img_2.png)  

Here is the unsafe terminating which results in big data leaks.  

![img_3.png](images/img_3.png)

![img_7.png](images/img_7.png)

![img_6.png](images/img_6.png)

### 1.2 One element rewriting
It is the result of 3 clients incremented one element in map. Reult is not 30000.  
![img_8.png](images/img_8.png)

pessimistic locking (few seconds)  
works fine

![img_12.png](images/img_12.png)
![img_9.png](images/img_9.png)

Optimistic locking (2 minutes)  
This example is bad because all clients is changing one element, in most cases it works fine with distributed requests. But result is correct.

![img_11.png](images/img_11.png)
![img_10.png](images/img_10.png)


## 2. Bounded queue
I have enabled the bounded queue.  
![img_18.png](images/img_18.png)

The following is the result of 2 consumers reading the data from one producer.
![img_15.png](images/img_15.png)

![img_13.png](images/img_13.png)

![img_14.png](images/img_14.png)  
We can see that consumers almost divided all data evenly.  
When consumers is not reading producer just stop writing to queue after 10 entries.

