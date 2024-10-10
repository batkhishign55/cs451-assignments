// Batkhishig Dulamsurankhor
// bdulamsurankhor@hawk.iit.edu
// A20543498

package src;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.time.Duration;

public class BdulamsurankhorConsumer {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide the topic name as an argument.");
            return;
        }

        String topicName = args[0];
        Properties props = new Properties();

        try {
            props.load(new FileReader("consumer.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topicName));
        long sum = 0;
        int messageCount = 0;
        running=true;

        try {
            while (running) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    JsonObject jsonObject = JsonParser.parseString(record.value()).getAsJsonObject();
                    int randomNumber = jsonObject.get("number").getAsInt();
                    sum += randomNumber;
                    messageCount++;
                    if (jsonObject.get("id").getAsString().equals("1000")) {
                        running=false;
                    }
                }
            }
            System.out.println("Total sum of random numbers: " + sum);
            System.out.println("Total messages processed: " + messageCount);
        } finally {
            consumer.close();
        }
    }
}