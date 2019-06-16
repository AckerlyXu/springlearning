package springframework.doc.profile;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import springframework.doc.quality.Movie;
import springframework.doc.quality.MovieFinder;

@Configurable
//��environment���properties
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
   //���û��һ��profile���ڼ���״̬����ôdefault profile���Ǽ���ģ�����Ϊ�����ṩһ��Ĭ�ϵ�bean
   @Profile("default")
   public MovieFinder finder() {
	   return new MovieFinder();
	   
   }
}
