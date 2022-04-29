package guru.bonacci.bug;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

  private final KafkaTemplate<String, Foo> kafkaTemplate;

  
  public boolean send(Foo foo) {
    return sendMessage("foo", foo.getId(), foo);
  }
 
  boolean sendMessage(String topic, String key, Foo message) {
    try {
      return kafkaTemplate.send(topic, key, message).get().getRecordMetadata().hasOffset();
    } catch (Throwable t) {
      t.printStackTrace();
      return false;
    }
  }
}
