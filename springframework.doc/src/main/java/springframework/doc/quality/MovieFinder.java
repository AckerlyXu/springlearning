package springframework.doc.quality;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MovieFinder {
	@Autowired
	@Qualifier("movie")
    private Movie movie;

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}
    
	
}
