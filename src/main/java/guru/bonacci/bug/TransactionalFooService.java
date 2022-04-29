package guru.bonacci.bug;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionalFooService {

  private final RedisService redis;
  private final KafkaProducer kafka;

  @Transactional
  public Foo txMethod(Foo foo) {
    if (!redis.proceed(foo)) {
      throw new IllegalStateException("Something wrong..");
    }
    kafka.send(foo);
    return foo;
  }
}
