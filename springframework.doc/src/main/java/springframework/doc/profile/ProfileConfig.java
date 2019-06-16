package springframework.doc.profile;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import springframework.doc.quality.Movie;
import springframework.doc.quality.MovieFinder;

@Configurable
//给environment添加properties
@PropertySource("classpath:datasource.properties")
public class ProfileConfig {
   @Bean
   @Profile("test")
	 public Movie testMovie() {
		   Movie movie = new Movie();
		    movie.setName("test");
		    movie.setPrice(123);
		    return movie;
	 }
   
   @Bean
   @Profile("dev")
	 public Movie devMovie() {
		   Movie movie = new Movie();
		    movie.setName("development");
		    movie.setPrice(167);
		    return movie;
	 }
   @Bean
   //如果没有一个profile处于激活状态，那么default profile就是激活的，可以为程序提供一个默认的bean
   @Profile("default")
   public MovieFinder finder() {
	   return new MovieFinder();
	   
   }
}
