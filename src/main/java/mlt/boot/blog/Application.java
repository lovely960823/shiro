package mlt.boot.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("mlt.boot.blog.mapper")
public class Application{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
