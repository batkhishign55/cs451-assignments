// Batkhishig Dulamsurankhor
// bdulamsurankhor@hawk.iit.edu
// A20543498

package src;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

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
        long sum = 0;
        int messageCount = 0;

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    JsonObject jsonObject = JsonParser.parseString(record.value()).getAsJsonObject();
                    int randomNumber = jsonObject.get("number").getAsInt();
                    sum += randomNumber;
                    messageCount++;

                    // Check if we've reached the last message
                    if (jsonObject.get("id").getAsString().equals("1000000")) {
                        System.out.println("Total sum of random numbers: " + sum);
                        System.out.println("Total messages processed: " + messageCount);
                        return;
                    }
                }
            }
        } finally {
            consumer.close();
        }
    }
}