import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * @author jconnelly
 *
 */
public class ExcelReader {

    public static void main(String[] args) throws Exception {


    	String folderName = "C:/Blaze720/TESTDATA2/csvfiles";
        File folder = new File(folderName);
        int numberOfElementsCounter = 43;
    	List<String> csvListing = new ArrayList<>();
    	
    	// Enable footnotes if the user wants it
    	boolean footnotes = true;
        
    	// Get and load the file names as "folder + filename"
        String[] files = folder.list();
        for (String file : files) 
        {
        	csvListing.add(folderName + "/" + file); 
        	//System.out.println(folderName + "/" + file);
        }
    	      
        // Read the files and then parse the lines for each file found
    	List<String> speechListing = readFileAndParse(csvListing, footnotes);
    	
    	// Clean up the output of the speechListing returned from the parser
    	String layout = formatOutput(speechListing, numberOfElementsCounter);
    	
    	// Test output
        System.out.println(layout);
   
    }
    
    public static String formatOutput(List<String> speechListing, int numberOfElements) {
        
    	Random rand = new Random();
        String layout = "";
        
        for (int i = 0; i < numberOfElements; i++) {
            String phrasing = speechListing.get(rand.nextInt(speechListing.size()));            
            layout += phrasing + " ";
            if (i % 6 == 0 && i > 1) {
            	layout += "\n \r";
        	}
        }
        
        layout += "Okay? Thank you, thank you very much";
        
        return layout;
    }
    
    public static List<String> readFileAndParse(String csvFile) throws Exception {
    	
        Scanner scanner = new Scanner(new File(csvFile));
        List<String> speechListing = new ArrayList<>();
        while(scanner.hasNext()) {
        	List<String> temp = parseLine(scanner.nextLine());
        	String value = temp.get(1);
    		if (value.contains("hank you")) {
    			
    		} else {
    			//System.out.println(new StringTokenizer(value).countTokens());
    			speechListing.add(value);
    			
    		}
        }
        
        scanner.close();
        
        return speechListing;
        
    }
    
    public static List<String> readFileAndParse(List<String> csvFiles) throws Exception {
    	
    	int csvFileSize = csvFiles.size();
        List<String> speechListing = new ArrayList<>();
        
    	for (int a = 0; a < csvFileSize; a++ ) {
	        
    		Scanner scanner = new Scanner(new File(csvFiles.get(a)));
	        while(scanner.hasNext()) {
	        	List<String> temp = parseLine(scanner.nextLine());
	        	
	        	String value = temp.get(1);
	    		if (value.contains("hank you") || (new StringTokenizer(value).countTokens() < 4)) {
	    			
	    		} else {
	    			if ((!(value.contains("."))) && (!(value.contains("?"))) && (!(value.contains("!"))))  {
	    				value = value + ". ";
	    			}
	    			speechListing.add(value);
	    		}
	        }
	        
	        scanner.close();
    	}
    	
    	return speechListing;
    }
    
    public static List<String> readFileAndParse(List<String> csvFiles, boolean footnotes) throws Exception {
    	
    	int csvFileSize = csvFiles.size();
        List<String> speechListing = new ArrayList<>();
        
    	for (int a = 0; a < csvFileSize; a++ ) {
	        
    		// What file are we operating off of?
    		//System.out.println("File Name: " + csvFiles.get(a));
    		
    		Scanner scanner = new Scanner(new File(csvFiles.get(a)));
	        while(scanner.hasNext()) {
	        	List<String> temp = parseLine(scanner.nextLine());
	        	
	        	// Output the file for testing:
	        	//System.out.println("temp: " + temp.get(1));
	        	
	        	String value = temp.get(1);
	        	String id = temp.get(0);
	        	
	        	// Remove any remaining quote characters
	        	if (value.contains("\"")) {
	        		value = value.replaceAll("\"", "");
	        	}
	    		if (value.contains("hank you") ) { //|| (new StringTokenizer(value).countTokens() < 4)) {
	    			
	    		} else {
	    			if ((!(value.contains("."))) && (!(value.contains("?"))) && (!(value.contains("!"))))  {

	    				value = value + ".";
	    			}
	    				
	    			if (footnotes) {
	    				value = value + "(" + id + ") ";
	    			} else {
	    				value = value + " ";
	    			}
    		
	    			//System.out.println(new StringTokenizer(value).countTokens());
	    			speechListing.add(value);
	    		}
	        }
	        
	        scanner.close();
    	}
    	
    	return speechListing;
    }
    
    public static List<String> parseLine(String cvsLine) {

        List<String> result = new ArrayList<>();

        //if null then return
        if (cvsLine == null ) {
            return result;
        }

        // Use a regular expression to walk the csv file
        String[] tokens = cvsLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        for (String token: tokens) {
            //System.out.println(token);
            result.add(token);
        }

        return result;
    }

}