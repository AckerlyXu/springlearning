package springframework.doc.scan;

import javax.management.MXBean;

import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import springframework.doc.dao.JpaItemDao;
import springframework.doc.dao.impl.JpaItemDaoImpl;
import springframework.doc.quality.Movie;
import springframework.doc.quality.MovieFinder;
import springframework.doc.service.impl.PetStoreServiceImpl;

@Configuration
@ComponentScan(basePackageClasses= {FactoryMethodComponent.class},scopedProxy=ScopedProxyMode.TARGET_CLASS)
public class FactoryMethodComponent {
	 @Bean 
	public MovieFinder movieFinder() {
		 return new MovieFinder();
	}
	  @Bean
	  @Qualifier("movie")
	 public Movie movie() {
		 Movie movie = new Movie();
		 movie.setName("movie");
		 movie.setPrice(123);
		 return movie;
	 }
	  
	  @Bean @Scope(value="prototype")
	  
	    public JpaItemDaoImpl prototypeInstance() {
	       JpaItemDaoImpl item = new  JpaItemDaoImpl();
	       
	       return item;
	    }
	  @Bean  @Scope(value="singleton")
	    public  PetStoreServiceImpl petStoreService() {
	    	  PetStoreServiceImpl petStoreServiceImpl = new PetStoreServiceImpl() {
	    		  @Override
	    		  public JpaItemDao getItemDao() {
	    			  return prototypeInstance();
	    		  };
	    	  } ;
	    	
	    	  return petStoreServiceImpl;
	    }

}
