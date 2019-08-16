package org.launchcode.techjobs.console;

import com.sun.org.apache.xpath.internal.functions.FuncFalse;
import sun.font.TrueTypeFont;
import java.util.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
public class TechJobs
{

    private static Scanner in = new Scanner(System.in);

    public static void main (String[] args)
    {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");
        columnChoices.put("keyword", "Keyword");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");
        actionChoices.put("exit", "Exit");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        ///////////// loop ending variable
        boolean finish = true;
        ///////////////

        // Allow the user to search until they manually quit
        while (finish == true)
        {
            String actionChoice = getUserSelection("View jobs by:", actionChoices);
            if (actionChoice.equals("list"))
            {
                String columnChoice = getUserSelection("List", columnChoices);
                if (columnChoice.equals("all"))
                {
                    printJobs(JobData.findAll() );
                }
                else
                {
                    ArrayList<String> results = JobData.findAll(columnChoice);
                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results)
                    {
                        System.out.println(item);
                    }
                }

            }
            else if (actionChoice.equals("search"))
            { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                if (searchField.equals("all"))
                {
                   // System.out.println("Search all fields not yet implemented.");
                    printJobs(JobData.findAll());
                }
                else if(searchField.equals("keyword"))
                {
                    System.out.println("\nSearch term: ");
                    String searchTerm = in.nextLine();
                    printJobs(JobData.findByValue(searchTerm.toUpperCase()));
                }
                else
                {
                    // What is their search term?
                    System.out.println("\nSearch term: ");
                    String searchTerm = in.nextLine();
                    printJobs(JobData.findByColumnAndValue(searchField.toUpperCase(), searchTerm.toUpperCase()));
                }
            }
            else
            {
                System.out.println("Goodbye");
                finish = false;
            }
        }
    }
    // ====================== GET USER SELECTION =============================//
    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices)
    {
        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet())
        {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do
        {
            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++)
            {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }
            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length)
            {
                System.out.println("Invalid choice. Try again.");
            }
            else
            {
                validChoice = true;
            }

        } while(!validChoice);
        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs)
    {
        //System.out.println("The arraylist contains elements: \n" + someJobs + "\n");
        //someJobs.forEach(System.out::println);
        if(someJobs.size() > 0)
        {
            for(Map<String, String> jobs : someJobs){

                // get entrySet() into Set
                Set<String> setOfJobs = jobs.keySet();

                // Collection Iterator
                Iterator<String> iterator = setOfJobs.iterator();

                // variable used to format output based on number of column names in csv file
                final int NUM_OF_COLUMN_NAMES = 5;

                // Iterate using while loop after getting Iterator
                while(iterator.hasNext()){
                    System.out.println("*****");
                    for(int i = 0; i < NUM_OF_COLUMN_NAMES; i++){
                        String key = iterator.next();
                        System.out.println( key + ": " + jobs.get(key));
                    }
                    System.out.println("*****\n");
                }
            }
        }
        else
        {
            System.out.println("There aren't any jobs in your desired location");
        }
    }
}
