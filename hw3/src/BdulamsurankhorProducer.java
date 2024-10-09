// Batkhishig Dulamsurankhor
// bdulamsurankhor@hawk.iit.edu
// A20543498

package src;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import com.google.gson.JsonObject;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class BdulamsurankhorProducer {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide the topic name as an argument.");
            return;
        }

        String topicName = args[0];
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        
        int messageCount = 1000000; // Set this to the number of messages you want to generate
        String lastMessageId = "";

        try {
            for (int i = 1; i <= messageCount; i++) {
                JsonObject message = new JsonObject();
                message.addProperty("id", i);
                message.addProperty("content", "Message #" + i);

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