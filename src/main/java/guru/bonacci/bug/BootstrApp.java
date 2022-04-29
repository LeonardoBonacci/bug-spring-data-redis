package guru.bonacci.bug;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BootstrApp {

	public static void main(String[] args) {
		SpringApplication.run(BootstrApp.class, args);
	}
	
  @Bean
  CommandLineRunner demo(TransactionalFooService serv) {
    return args -> {
      var foo = Foo.builder().id("unique").bar("bar").build();
      System.out.println(serv.txMethod(foo));
    };
  }
}
