package guru.bonacci.bug;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

  private final RedisRepo repo;

  public boolean proceed(Foo foo) {
    if (repo.existsById(foo.getId())) {
      return false;
    }

    repo.save(foo);
    return true;
  }
}
