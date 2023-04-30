package main;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {
	Scanner scanner;
	DatabaseData databaseData;
	ControlData controlData;
	
	public App(DatabaseData databasedata) {
		databaseData = databasedata;
		controlData = new ControlData(databaseData);
		scanner = new Scanner(System.in);
	}
	
	private int scanInt() {
		int choice;
		String inputString = scanner.nextLine(); 
		if (inputString.strip().charAt(0) == '.') {return 0;}
		try {
			choice = Integer.parseInt(inputString);
		} catch (NumberFormatException e) {
			return -1;
		}
		return choice;
	}
	
	private int scanYN() {
		String inputString = scanner.nextLine(); 
		if (inputString.strip().length() > 0) {
			if (inputString.strip().toLowerCase().charAt(0) == 'y') {return 1;}
			if (inputString.strip().toLowerCase().charAt(0) == 'n') {return 0;}
		}
		return -1;
	}
	
	private int parseInt(String stringToParse) {
		int parsedInt;
		try {
			parsedInt = Integer.parseInt(stringToParse);
		} catch (NumberFormatException e) {
			return -1;
		}
		return parsedInt;
	}
	
	public void run() {
		boolean display = true;
		while (true) {
			if (display) {
				System.out.println(
						"\n< Movie Database App >\n" +
						"1 ...... add a new movie\n" +
						"2 ...... search for a movie\n" +
						"3 ...... search for an actor\n" +
						"4 ...... search for an animator\n" +
						"5 ...... show all stored movies\n" +
						"6 ...... show actors who starred in >1 movie\n" +
						"7 ...... show animators who starred in >1 movie\n" +
						"8 ...... save and exit\n" +
						"Enter a number: ");
			}
			display = true;
			switch (scanInt()) {
			case -1:
				System.out.println("Input is not a number!");
				display = false;
		        break;
			case 0:
				display = false;
		        break;
		    case 1:
		    	addMovieMenu();
		        break;
		    case 2:
		    	searchMovieMenu();
		        break;
		    case 3:
		    	searchPerformer(true);
		        break;
		    case 4:
		    	searchPerformer(false);
		        break;
		    case 5:
		    	System.out.println("< Listing of all movies stored in the database >");
		    	System.out.println("Live movies:");
		    	for (MovieLive movie : databaseData.getMoviesLive()) {
		    		System.out.println(movie);
		    		System.out.println(getMoviePerformers(movie, true));
		    	}
		    	System.out.println("Animated movies:");
		    	for (MovieAnimated movie : databaseData.getMoviesAnimated()) {
		    		System.out.println(movie);
		    		System.out.println(getMoviePerformers(movie, true));
		    	}
		        break;
		    case 6:
		    	ArrayList<Performer> foundActors = searchForPerformersStarredInMoreThanOneMovie(true);
		    	System.out.println((foundActors.size() > 0) ? "There are " + foundActors.size() + " actors who starred in more than one movie:" : "There are no such actors.");
		    	for (Performer performer : foundActors) {
		    		System.out.println(performer.getName() + " " + performer.getSurname());
		    	}
		        break;
		    case 7:
		    	ArrayList<Performer> foundAnimators = searchForPerformersStarredInMoreThanOneMovie(false);
		    	System.out.println((foundAnimators.size() > 0) ? "There are " + foundAnimators.size() + " animators who starred in more than one movie:" : "There are no such animators.");
		    	for (Performer performer : foundAnimators) {
		    		System.out.println(performer.getName() + " " + performer.getSurname());
		    	}
		        break;
		    case 8:
		    	return;
		    default:
		        System.out.println("Invalid choice. Please try again.");
		        break;
			}
		}
	}
	
	private ArrayList<Performer> searchForPerformersStarredInMoreThanOneMovie(boolean performerType) {
		ArrayList<Performer> foundPerformers = new ArrayList<Performer>();
		var searchedPerformers = (performerType ? databaseData.getActors() : databaseData.getAnimators());
    	for (ArrayList<Performer> performers : searchedPerformers) {
    		for (var performer : performers) {
    			if (foundPerformers.contains(performer)) {continue;}
	    		if (controlData.actorStarrs(performer.getName(), performer.getSurname()).size() > 1) {
	    			foundPerformers.add(performer);
	    		}
	    	}
    	}
    	return foundPerformers;
	}
	
	private String getMoviePerformers(AbstractMovie movie, boolean oneDimension) {
		String separator = (oneDimension) ? " " : "\n";
	    StringBuilder sb = new StringBuilder();
	    sb.append((movie instanceof MovieLive) ? "Actors:" + separator : "Animators:" + separator);
	    ArrayList<Performer> performers;
	    if (movie instanceof MovieLive) {
	        MovieLive movieLive = (MovieLive) movie;
	        performers = controlData.getMovieLiveActors(movieLive);
	    } else {
	        MovieAnimated movieAnimated = (MovieAnimated) movie;
	        performers = controlData.getMovieAnimatedAnimators(movieAnimated);
	    }
	    for (Performer performer : performers) {
	        sb.append(performer.getName()).append(" ").append(performer.getSurname()).append(",").append(separator);
	    }
	    if (performers.size() > 0) {
	        sb.delete(sb.length() - 2, sb.length());
	    } else {
	        sb.append("No performer added.");
	    }
	    return sb.toString();
	}
	
	private void addMovieMenu() {
		boolean display = true;
		while (true) {
			if (display) {
			System.out.println(
					"\n< Add a new movie >\n" +
					"1 ...... Live movie\n" +
					"2 ...... Animated movie\n" +
					"0 ...... Back");
			}
			display = true;
			switch (scanInt()) {
				case -1:
					System.out.println("Input is not a number!");
					display = false;
			        break;
				case 0:
			        return;
			    case 1:
			    	addMovieLive();
			    	break;
			    case 2:
			    	addMovieAnimated();
			    	break;
			    default:
			        System.out.println("Invalid choice. Please try again.");
			        break;
			}
		}
	}
	
	private void addMovieLive() {
		boolean display = true;
		boolean inputOk = false;
		while (!inputOk) {
			if (display) {
			System.out.println(
					"\n< Adding a new Live movie >\n" +
					"Enter the movie in following format:\n" +
					"Name ; Director ; Year ; Review ; Stars\n" +
					"Use ';' to separate it's atributes.\n" +
					"Enter '.' to return."
					);
			}
			display = true;
			String[] movieInput = scanner.nextLine().split(";");
			if (movieInput.length > 0) {
				if (movieInput[0].strip().length() > 0) {
					if (movieInput[0].strip().charAt(0) == '.') {return;}
				}
			}
			if (movieInput.length >= 5) {
				String movieName = movieInput[0].strip();
				String movieDirector = movieInput[1].strip();
				String movieYear = movieInput[2].strip();
				String movieReview = movieInput[3].strip();
				String movieStars = movieInput[4].strip();
				
				inputOk = true;
				
				if (movieName.length() < 1 || movieName.length() > 128) {
					System.out.println("Movie name must be from 1 to 128 characters.");
					inputOk = display = false;
				}
				if (movieDirector.length() < 3 || movieDirector.length() > 64) {
					System.out.println("Director length must be from 1 to 128 characters.");
					inputOk = display = false;
				}
				if (parseInt(movieYear) == -1) {
					System.out.println("Invalid year number");
					inputOk = display = false;
				} else if (parseInt(movieYear) > 2100 || parseInt(movieYear) < 1800) {
					System.out.println("Year can not go beyond 2100 or below 1800.");
					inputOk = display = false;
				}
				if (movieReview.length() < 3 || movieReview.length() > 128) {
					System.out.println("Director length must be from 3 to 128 characters.");
					inputOk = display = false;
				}
				if (parseInt(movieStars) == -1) {
					System.out.println("Invalid stars number");
					inputOk = display = false;
				} else if (parseInt(movieStars) > 5 || parseInt(movieStars) < 0) {
					System.out.println("Stars can not go beyond 5 or below 0.");
					inputOk = display = false;
				}
				if (inputOk) {
					System.out.println(
							"\n< Adding a new Live movie >\n" +
							"Review the following details:\n" +
							"Name: " + movieName + "\n" +
							"Director: " + movieDirector + "\n" +
							"Year: " + movieYear + "\n" +
							"Review: " + movieReview + "\n" +
							"Stars: " + movieStars + "\n" +
							"Add to database? (y/n)"
							);
					if (scanYN() == 1) {
						if (controlData.addMovieLive(movieName, movieDirector, 
								parseInt(movieYear), movieReview, parseInt(movieStars))) {
							System.out.println("Live movie added successfully.");
						}
						else {
							System.out.println("Could not add a new live movie.\n" +
									"It is already in the database.");
						}
					}
				}
			}
			else {
				System.out.println("Invalid input. Please try again.");
			}
		}
	}
	
	private void addPerformer(AbstractMovie movie) {
		String performerType = ((movie instanceof MovieLive) ? "Actor" : "Animator");
		boolean display = true;
		boolean inputOk = false;
		while (!inputOk) {
			if (display) {
			System.out.println(
					"\n< Adding a new " + performerType +" >\n" +
					"Enter the " + performerType + " in following format:\n" +
					"Name ; Surname\n" +
					"Use ';' to separate it's atributes.\n" +
					"Enter '.' to return."
					);
			}
			display = true;
			String[] performerInput = scanner.nextLine().split(";");
			if (performerInput.length > 0) {
				if (performerInput[0].strip().length() > 0) {
					if (performerInput[0].strip().charAt(0) == '.') {return;}
				}
			}
			if (performerInput.length >= 2) {
				String performerName = performerInput[0].strip();
				String performerSurname = performerInput[1].strip();
				
				inputOk = true;
				
				if (performerName.length() < 1 || performerName.length() > 64) {
					System.out.println(performerType + "'s name length must be from 1 to 64 characters.");
					inputOk = display = false;
				}
				
				if (performerSurname.length() < 1 || performerSurname.length() > 64) {
					System.out.println(performerType + "'s surname length must be from 1 to 64 characters.");
					inputOk = display = false;
				}

				if (inputOk) {
					if (movie instanceof MovieLive) {
						if (controlData.addMovieLiveActor(
								controlData.getMovieLiveIndex((MovieLive)movie), performerName, performerSurname)) {
							System.out.println("New " + performerType + " added successfully.");
						}
						else {
							System.out.println("Could not add a new  "+ performerType +".\n" +
									"They are already added.");
						}
					}
					else {
						if (controlData.addMovieAnimatedAnimator(
								controlData.getMovieAnimatedIndex((MovieAnimated)movie), performerName, performerSurname)) {
							System.out.println("New " + performerType + " added successfully.");
						}
						else {
							System.out.println("Could not add a new  "+ performerType +".\n" +
									"They are already added.");
						}
					}
						
				}
			}
			else {
				System.out.println("Invalid input. Please try again.");
				display = false;
			}
		}
	}
	
	private void deletePerformer(AbstractMovie movie) {
		String performerType = ((movie instanceof MovieLive) ? "Actor" : "Animator");
		boolean display = true;
		boolean inputOk = false;
		while (!inputOk) {
			if (display) {
			System.out.println(
					"\n< Deleting " + performerType +" >\n" +
					"Enter the " + performerType + " in following format:\n" +
					"Name ; Surname\n" +
					"Use ';' to separate it's atributes.\n" +
					"Enter '.' to return."
					);
			}
			display = true;
			String[] performerInput = scanner.nextLine().split(";");
			if (performerInput.length > 0) {
				if (performerInput[0].strip().length() > 0) {
					if (performerInput[0].strip().charAt(0) == '.') {return;}
				}
			}
			if (performerInput.length >= 2) {
				String performerName = performerInput[0].strip();
				String performerSurname = performerInput[1].strip();
				
				inputOk = true;
				
				if (performerName.length() < 1 || performerName.length() > 64) {
					System.out.println(performerType + "'s name length must be from 1 to 64 characters.");
					inputOk = display = false;
				}
				
				if (performerSurname.length() < 1 || performerSurname.length() > 64) {
					System.out.println(performerType + "'s surname length must be from 1 to 64 characters.");
					inputOk = display = false;
				}

				if (inputOk) {
					if (movie instanceof MovieLive) {
						if (controlData.deleteMovieLiveActor(
								controlData.getMovieLiveIndex((MovieLive)movie), performerName, performerSurname)) {
							System.out.println(performerType + " '" + performerName + " " + performerSurname +"' deleted successfully.");
						}
						else {
							System.out.println("Could not delete "+ performerType +".\n" +
									"They are not added.");
						}
					}
					else {
						if (controlData.addMovieAnimatedAnimator(
								controlData.getMovieAnimatedIndex((MovieAnimated)movie), performerName, performerSurname)) {
							System.out.println(performerType + " '" + performerName + " " + performerSurname +"' deleted successfully.");
						}
						else {
							System.out.println("Could not delete "+ performerType +".\n" +
									"They are not added.");
						}
					}	
				}
			}
			else {
				System.out.println("Invalid input. Please try again.");
				display = false;
			}
		}
	}
	
	private void addMovieAnimated() {
		boolean display = true;
		boolean inputOk = false;
		while (!inputOk) {
			if (display) {
			System.out.println(
					"\n< Adding a new Animated movie >\n" +
					"Enter the movie in following format:\n" +
					"Name ; Director ; Year ; Review ; Rating ; Age\n" +
					"Use ';' to separate it's atributes.\n" +
					"Enter '.' to return."
					);
			}
			display = true;
			String[] movieInput = scanner.nextLine().split(";");
			if (movieInput.length > 0) {
				if (movieInput[0].strip().length() > 0) {
					if (movieInput[0].strip().charAt(0) == '.') {return;}
				}
			}
			if (movieInput.length >= 6) {
				String movieName = movieInput[0].strip();
				String movieDirector = movieInput[1].strip();
				String movieYear = movieInput[2].strip();
				String movieReview = movieInput[3].strip();
				String movieRating = movieInput[4].strip();
				String movieAge = movieInput[5].strip();
				
				inputOk = true;
				
				if (movieName.length() < 1 || movieName.length() > 128) {
					System.out.println("Movie name must be from 1 to 128 characters.");
					inputOk = display = false;
				}
				if (movieDirector.length() < 3 || movieDirector.length() > 64) {
					System.out.println("Director length must be from 1 to 128 characters.");
					inputOk = display = false;
				}
				if (parseInt(movieYear) == -1) {
					System.out.println("Invalid year number");
					inputOk = display = false;
				} else if (parseInt(movieYear) > 2100 || parseInt(movieYear) < 1800) {
					System.out.println("Year can not go beyond 2100 or below 1800.");
					inputOk = display = false;
				}
				if (movieReview.length() < 3 || movieReview.length() > 128) {
					System.out.println("Director length must be from 3 to 128 characters.");
					inputOk = display = false;
				}
				if (parseInt(movieRating) == -1) {
					System.out.println("Invalid stars number");
					inputOk = display = false;
				} else if (parseInt(movieRating) > 10 || parseInt(movieRating) < 0) {
					System.out.println("Stars can not go beyond 5 or below 0.");
					inputOk = display = false;
				}
				if (parseInt(movieAge) == -1) {
					System.out.println("Invalid age number");
					inputOk = display = false;
				} else if (parseInt(movieAge) > 10 || parseInt(movieAge) < 0) {
					System.out.println("Age can not go beyond 99 or below 0.");
					inputOk = display = false;
				}
				if (inputOk) {
					System.out.println(
							"\n< Adding a new Live movie >\n" +
							"Review the following details:\n" +
							"Name: " + movieName + "\n" +
							"Director: " + movieDirector + "\n" +
							"Year: " + movieYear + "\n" +
							"Review: " + movieReview + "\n" +
							"Rating: " + movieRating + "\n" +
							"Age: " + movieRating + "\n" +
							"Add to database? (y/n)"
							);
					if (scanYN() == 1) {
						if (controlData.addMovieAnimated(movieName, movieDirector, 
								parseInt(movieYear), movieReview, parseInt(movieRating), parseInt(movieAge))) {
							System.out.println("Animated movie added successfully.");
						}
						else {
							System.out.println("Could not add a new animated movie.\n" +
									"It is already in the database.");
						}
					}
				}
			}
			else {
				System.out.println("Invalid input. Please try again.");
			}
		}
	}

	private void searchMovieMenu() {
		boolean display = true;
		while (true) {
			if (display) {
			System.out.println(
					"\n< Search for a movie >\n" +
					"1 ...... Live movie\n" +
					"2 ...... Animated movie\n" +
					"0 ...... Return");
			}
			display = true;
			switch (scanInt()) {
				case -1:
					System.out.println("Input is not a number!");
					display = false;
			        break;
				case 0:
			        return;
			    case 1:
			    	searchMovie(true);
			    	break;
			    case 2:
			    	searchMovie(false);
			    	break;
			    default:
			        System.out.println("Invalid choice. Please try again.");
			        break;
			}
		}
	}
	
	private void searchMovie(boolean movieType) {
		String movieTypeName = movieType ? "Live" : "Animated";
		boolean display = true;
		boolean inputOk = false;
		while (!inputOk) {
			if (display) {
			System.out.println(
					"\n< Searching for a " + movieTypeName + " movie >\n" +
					"Enter movie name:\n" +
					"Or enter '.' to return."
					);
			}
			display = true;
			String nameInput = scanner.nextLine();
			if (nameInput.strip().length() > 0) {
				if (nameInput.strip().charAt(0) == '.') {return; }
			}
			if (nameInput.length() > 0) {
				inputOk = true;
			}
			if (inputOk) {
				System.out.println("\n< Searching for a " + movieTypeName + " movie >");
				if (movieType) {
					MovieLive movie = controlData.findMovieLive(nameInput);
					if (movie != null) {
						movieSelected(movie);
					}
					else {
						System.out.println("Could not find a movie with name '"+ nameInput +"'.\n");
					}
				}
				else {
					MovieAnimated movie = controlData.findMovieAnimated(nameInput);
					if (movie != null) {
						movieSelected(movie);
					}
					else {
						System.out.println("Could not find a movie with name '"+ nameInput +"'.\n");
					}
				}
			}
			else {
				System.out.println("Invalid input. Please try again.");
				display = false;
			}
		}
	}
	
	private void searchPerformer(boolean performerType) {
		String performerTypeName = performerType ? "Actor" : "Animator";
		boolean display = true;
		boolean inputOk = false;
		while (!inputOk) {
			if (display) {
			System.out.println(
					"\n< Searching for an " + performerTypeName +" >\n" +
					"Enter the " + performerTypeName + " in following format:\n" +
					"Name ; Surname\n" +
					"Use ';' to separate it's atributes.\n" +
					"Enter '.' to return."
					);
			}
			display = true;
			String[] performerInput = scanner.nextLine().split(";");
			if (performerInput.length > 0) {
				if (performerInput[0].strip().length() > 0) {
					if (performerInput[0].strip().charAt(0) == '.') {return;}
				}
			}
			if (performerInput.length >= 2) {
				String performerName = performerInput[0].strip();
				String performerSurname = performerInput[1].strip();
				
				inputOk = true;
				
				if (performerName.length() < 1 || performerName.length() > 64) {
					System.out.println(performerTypeName + "'s name length must be from 1 to 64 characters.");
					inputOk = display = false;
				}
				
				if (performerSurname.length() < 1 || performerSurname.length() > 64) {
					System.out.println(performerTypeName + "'s surname length must be from 1 to 64 characters.");
					inputOk = display = false;
				}

				if (inputOk) {
					if (performerType) {
						ArrayList<MovieLive> resultingMovies = controlData.actorStarrs(performerName, performerSurname);
						if (resultingMovies == null || resultingMovies.isEmpty()) {
								System.out.println("Could not find any movies where " + performerTypeName + " '"+ performerName +
										" " + performerSurname + "' starred in.\n");
						}
						else {
							System.out.println("Found "+ resultingMovies.size() + " movies where "+ performerTypeName +" '"+ performerName +
									" " + performerSurname + "' starred in:");
							for (MovieLive movie : resultingMovies) {
								System.out.println(movie.getName());
							}
						}
					}
					else {
						ArrayList<MovieAnimated> resultingMovies = controlData.animatorStarrs(performerName, performerSurname);
						if (resultingMovies == null || resultingMovies.isEmpty()) {
							System.out.println("Could not find any movies where " + performerTypeName + " '"+ performerName +
									" " + performerSurname + "' starred in.\n");
						}
						else {
							System.out.println("Found "+ resultingMovies.size() + " movies where "+ performerTypeName +" '"+ performerName +
									" " + performerSurname + "' starred in:");
							for (MovieAnimated movie : resultingMovies) {
								System.out.println(movie.getName());
							}
						}
					}	
				}
			}
			else {
				System.out.println("Invalid input. Please try again.");
				display = false;
			}
		}
	}
	
	private void movieSelected(AbstractMovie movie)  {
		String performerType = (movie instanceof MovieLive) ? "Actors" : "Animators";
		String movieType = (movie instanceof MovieLive) ? "Live" : "Animated";
		boolean display = true;
		while (true) {
			if (display) {
				System.out.println(
						"< < " + movieType + " Movie Selected >\n" +
						"\n" +
						"Name: " + movie.getName() + "\n" +
						"Director: " + movie.getDirector() + "\n" +
						"Year: " + movie.getYear() + "\n" +
						"Review: " + movie.getReview());
				if (movie instanceof MovieLive) {
					MovieLive movieLive = (MovieLive) movie;
					System.out.println("Stars: " + movieLive.getStars());
					System.out.println(getMoviePerformers(movie, false));
				} else {
					MovieAnimated movieAnimated = (MovieAnimated) movie;
					System.out.println(
							"Rating: " + movieAnimated.getRating() + "\n" +
							"Age: " + movieAnimated.getAge());
					System.out.println(getMoviePerformers(movie, false));
				}
				System.out.println(
							"\n1 ...... Edit selected movie\n" +
							"2 ...... Edit selected movie's " + performerType + " \n" +
							"3 ...... Delete selected movie\n" +
							"4 ...... Save to a file\n" +
							"0 ...... Return");
			}
			display = true;
			switch (scanInt()) {
				case -1:
					System.out.println("Input is not a number!");
					display = false;
			        break;
				case 0:
			        return;
				case 1:
					movieSelectedEdit(movie);
			        break;
			    case 2:
			    	movieSelectedEditPerformers(movie);
			    	break;
			    case 3:
			    	if (movieDeleteSelected(movie)) {return; }
			    	return;
			    case 4:
			    	if (saveMovie(movie)) {return; }
			    	break;
			    default:
			        System.out.println("Invalid choice. Please try again.");
			        break;
			}
		}
	}

	public boolean saveMovie(AbstractMovie movie) {
		boolean success = true;
		String extension = (movie instanceof MovieLive) ? ".mvl" : ".mva";
	    StringBuilder movieDetails = new StringBuilder();
	    StringBuilder performers = new StringBuilder();
	    String path = movie.getName().replaceAll("[\\\\/:*?\"<>|]", "_") + extension;
	    ArrayList<Performer> moviePerformers;
	    movieDetails.append(movie.getName()).append(";")
	        .append(movie.getDirector()).append(";")
	        .append(movie.getYear()).append(";")
	        .append(movie.getReview()).append(";");
	    if (movie instanceof MovieLive) {
	        MovieLive movieLive = (MovieLive) movie;
	        movieDetails.append(movieLive.getStars());
	        moviePerformers = controlData.getMovieLiveActors((MovieLive)movie);
	    } else {
	        MovieAnimated movieAnimated = (MovieAnimated) movie;
	        movieDetails.append(movieAnimated.getRating()).append(";")
	            .append(movieAnimated.getAge());
	        moviePerformers = controlData.getMovieAnimatedAnimators((MovieAnimated)movie);
	    }
	    if (moviePerformers != null && !moviePerformers.isEmpty()) {
	    	for (var performer : moviePerformers) {
	    		performers.append(performer.getName()).append(";").append(performer.getSurname()).append("\n");
	    	}
	    }
	    try {
	        FileWriter writer = new FileWriter(path);
	        writer.write(movieDetails.append("\n").append(performers).toString());
	        writer.close();
	        System.out.println("Successfully saved the movie to a file.");
	        System.out.println("Path: " + path);
	      } catch (IOException e) {
	        System.out.println("Could not save the movie.");
	        e.printStackTrace();
	        success = false;
	      }
	    return success;
	}

	
	private void movieSelectedEditPerformers(AbstractMovie movie) {
		String performerType = ((movie instanceof MovieLive) ? "Actor" : "Animator");
		String movieType = (movie instanceof MovieLive) ? "Live" : "Animated";
		boolean display = true;
		while (true) {
			if (display) {
				System.out.println(
						"\n< Editing selected " + movieType + " movie's " + performerType + "s" + " >\n" +
						"Name: " + movie.getName() + "\n");
				System.out.println(getMoviePerformers(movie, false));
				System.out.println(
						"1 ...... Add a new " + performerType + " \n" +
						"2 ...... Delete " + performerType + " \n" +
						"3 ...... Delete all " + performerType + "s \n" +
						"0 ...... Return \n"
						);
			}
		int option = scanInt();
		switch (option) {
			case -1:
				System.out.println("Input is not a number!");
				display = false;
		        break;
			case 0:
		        return;
		    case 1:
		    	addPerformer(movie);	
		    	break;
		    case 2:
		    	if (controlData.hasMovieAnyPerformers(movie)) {
		    		deletePerformer(movie);
		    	}
		    	else {
		    		System.out.println("Movie has no " + performerType + "s added." );
		    		display = false;
		    	}
		    	break;
		    case 3:
		    	if (controlData.hasMovieAnyPerformers(movie)) {
		    		System.out.println("Delete all this movie's " + performerType + "s (y/n)?" );
		    		if (scanYN() == 1) {
		    			if (controlData.deleteMoviePerformers(movie)) {
		    				System.out.println("All " + performerType + "s succesfuly deleted.");
		    			}
		    		}
		    	}
		    	else {
		    		System.out.println("Movie has no " + performerType + "s added." );
		    		display = false;
		    	}
		    	break;
		    default:
		        System.out.println("Invalid choice. Please try again.");
		        display = false;
		        break;
			}
		}
	}
	
	private void movieSelectedEdit(AbstractMovie movie)  {
		boolean display = true;
		while (true) {
			if (display) {
				String movieType = (movie instanceof MovieLive) ? "Live" : "Animated";
				System.out.println(
						"< Editing selected " + movieType + " Movie >\n" +
						"\n" +
						"Name: " + movie.getName() + "\n" +
						"Director: " + movie.getDirector() + "\n" +
						"Year: " + movie.getYear() + "\n" +
						"Review: " + movie.getReview());
				if (movie instanceof MovieLive) {
					MovieLive movieLive = (MovieLive) movie;
					System.out.println("Stars: " + movieLive.getStars());
				} else {
					MovieAnimated movieAnimated = (MovieAnimated) movie;
					System.out.println(
							"Rating: " + movieAnimated.getRating() + "\n" +
							"Age: " + movieAnimated.getAge());
				}
				System.out.println(
							"\n" +
							"1 ...... Name\n" +
							"2 ...... Director\n" +
							"3 ...... Year\n" +
							"4 ...... Review"
							);
				if (movie instanceof MovieLive) {
					System.out.println("5 ...... Stars: ");
				} else {
					System.out.println(
							"5 ...... Rating: " + "\n" +
							"6 ...... Age: ");
				}
				System.out.println("0 ...... Return");
			}
			display = true;
			int option = scanInt();
			if (movie instanceof MovieLive && option == 6) {option = 7;}
			switch (option) {
				case -1:
					System.out.println("Input is not a number!");
					display = false;
			        break;
				case 0:
			        return;
			    case 1:
			    	System.out.println("Enter new name: ");
			    	while (true) {
			    		String newName = scanner.nextLine();
			    		if (newName.length() < 1 || newName.length() > 128) {
							System.out.println("Movie name must be from 1 to 128 characters.");
						}
			    		else {
			    			movie.setName(newName);
			    			System.out.println("Name successfuly changed to '" + newName + "'.");
			    			break; 
		    			}
			    	}
			    	break;
			    case 2:
			    	System.out.println("Enter new director: ");
			    	while (true) {
			    		String newDirector = scanner.nextLine();
			    		if (newDirector.length() < 1 || newDirector.length() > 64) {
							System.out.println("Movie director must be from 1 to 64 characters.");
						}
			    		else {
			    			movie.setDirector(newDirector);
			    			System.out.println("Director successfuly changed to '" + newDirector + "'.");
			    			break; 
		    			}
			    	}
			    	break;
			    case 3:
			    	System.out.println("Enter new year: ");
			    	while (true) {
			    		String newYear = scanner.nextLine();
			    		if (parseInt(newYear) == -1) {
							System.out.println("Invalid year number");
						} else if (parseInt(newYear) > 2100 || parseInt(newYear) < 1800) {
							System.out.println("Year can not go beyond 2100 or below 1800.");
						} else {
							movie.setYear(parseInt(newYear));
			    			System.out.println("Year successfuly changed to '" + newYear + "'.");
			    			break; 
						}
			    	}
			    	break;
			    case 4:
			    	System.out.println("Enter new review: ");
			    	while (true) {
			    		String newReview = scanner.nextLine();
			    		if (newReview.length() < 0 || newReview.length() > 128) {
							System.out.println("Movie review must be from 1 to 128 characters.");
						}
			    		else {
			    			movie.setReview(newReview);
			    			System.out.println("Review successfuly changed to '" + newReview + "'.");
			    			break; 
		    			}
			    	}
			    	break;
			    case 5:
			    	String rateType = (movie instanceof MovieLive) ? "stars" : "rating";
			    	System.out.println("Enter new " + rateType + ": ");
			    	while (true) {
			    		if (movie instanceof MovieLive) {
			    			String newStars = scanner.nextLine();
				    		if (parseInt(newStars) == -1) {
								System.out.println("Invalid stars number");
							} else if (parseInt(newStars) > 5 || parseInt(newStars) < 0) {
								System.out.println("Stars can not go beyond 5 or below 0.");
							} else {
								MovieLive movieLive = (MovieLive) movie;
								movieLive.setStars(parseInt(newStars));
				    			System.out.println("Stars successfuly changed to '" + newStars + "'.");
				    			break; 
							}
			    		}
			    		else {
			    			String newRating = scanner.nextLine();
				    		if (parseInt(newRating) == -1) {
								System.out.println("Invalid rating number");
							} else if (parseInt(newRating) > 10 || parseInt(newRating) < 0) {
								System.out.println("Rating can not go beyond 5 or below 0.");
							} else {
								MovieAnimated movieAnimated = (MovieAnimated) movie;
								movieAnimated.setRating(parseInt(newRating));
				    			System.out.println("Rating successfuly changed to '" + newRating + "'.");
				    			break; 
							}
			    		}
			    	}
			    	break;
			    case 6:
			    	System.out.println("Enter new age: ");
			    	while (true) {
			    		String newAge = scanner.nextLine();
			    		if (parseInt(newAge) == -1) {
							System.out.println("Invalid age number");
						} else if (parseInt(newAge) > 99 || parseInt(newAge) < 0) {
							System.out.println("Age can not go beyond 2100 or below 1800.");
						} else {
							MovieAnimated movieAnimated = (MovieAnimated) movie;
							movieAnimated.setAge(parseInt(newAge));
			    			System.out.println("Age successfuly changed to '" + newAge + "'.");
			    			break; 
						}
			    	}
			    	break;
			    default:
			        System.out.println("Invalid choice. Please try again.");
			        break;
			}
		}
	}
	
	private boolean movieDeleteSelected(AbstractMovie movie) {
		while (true) {
			String movieType = (movie instanceof MovieLive) ? "Live" : "Animated";
			System.out.println(
					"< Deleting selected " + movieType + " Movie >\n" +
					"\n" +
					"Name: " + movie.getName() + "\n" +
					"Are you sure you want to delete this movie? (y/n)");
			int choice = scanYN();
			switch (choice) {
			case -1:
				System.out.println("Invalid input!");
		        break;
			case 0:
		        return false;
		    case 1:
		    	String name = movie.getName();
		    	if (movie instanceof MovieLive) {
		    		MovieLive liveMovie = (MovieLive)movie;
		    		controlData.deleteMovieLive(liveMovie);
		    	}
		    	else {
		    		MovieAnimated MovieAnimated = (MovieAnimated)movie;
		    		controlData.deleteMovieAnimated(MovieAnimated);
		    	}
		    	System.out.println("Movie '" + name + "' deleted successfuly.");
		    	return true;
		    default:
		        System.out.println("Invalid choice. Please try again.");
		        break;
			}
		}
	}
}
