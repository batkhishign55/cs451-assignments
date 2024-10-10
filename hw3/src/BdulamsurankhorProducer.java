// Batkhishig Dulamsurankhor
// bdulamsurankhor@hawk.iit.edu
// A20543498

package src;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.google.gson.JsonObject;

public class BdulamsurankhorProducer {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide the topic name as an argument.");
            return;
        }

        String topicName = args[0];
        Properties props = new Properties();

        try {
            props.load(new FileReader("producer.properties"));
        } catch (IOException e) {
                       e.printStackTrace();
                    }

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        int messageCount = 1000000;
        String lastMessageId = "";

        try {
            for (int i = 1; i <= messageCount; i++) {
                JsonObject message = new JsonObject();
                Random random = new Random();
                int randomInt = random.nextInt(1000);
                long timestamp = System.currentTimeMillis();

                message.addProperty("id", i);
                message.addProperty("number", randomInt);
                message.addProperty("timestamp", timestamp);

                ProducerRecord<String, String> record = new ProducerRecord<>(topicName, message.toString());
                RecordMetadata metadata = producer.send(record).get();

                if (i == messageCount) {
                    lastMessageId = message.get("id").getAsString();
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            producer.close();
            System.out.println("Last Message ID: " + lastMessageId);
        }
    }
}