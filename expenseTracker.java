import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

class expense {
    int exp_id;
    int amount;
    String category;
    String description;
    String date;

    expense() {
    }

    expense(int exp_id, int amount, String category, String description, String date) {
        this.exp_id = exp_id;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public int getExp_id() {
        return exp_id;
    }

    public int getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getString() {
        return String.format("%d,%d,%s,%s,%s", exp_id, amount, category, description, date);
    }

    public String getFormattedString() {
        return String.format("ID : %d , Amount : %d , Category : %s , Description : %s , Date : %s", exp_id, amount,
                category, description, date);
    }
}

// storing expense
class expenseStorage {

    static void storeExpense(ArrayList<expense> expObjects) {
        File myFile = new File("expense.txt");
        // checking if file exist or not
        if (myFile.exists()) {
        } else {
            try {
                myFile.createNewFile();
            } catch (Exception e) {
                System.out.println("Cannot create File! " + e.getMessage());
            }
        }

        try {
            FileWriter myFileWriter = new FileWriter("expense.txt");
            for (expense expense : expObjects) {
                myFileWriter.write(expense.getString() + "\n");
            }
            myFileWriter.close();
        } catch (Exception e) {
            System.out.println("Cannot store the data");
            e.printStackTrace();
        }
    }

    static ArrayList<expense> readExpense() {
        File myFile = new File("expense.txt");
        ArrayList<expense> expObjects = new ArrayList<expense>();
        if (myFile.exists()) {
            try {
                Scanner sc = new Scanner(myFile);
                while (sc.hasNextLine()) {
                    String str = sc.nextLine();
                    String[] str_arr = str.split("\n");
                    try {
                        for (String string : str_arr) {
                            String[] expInfo = string.split(",");
                            expense exp = new expense(Integer.valueOf(expInfo[0]), Integer.valueOf(expInfo[1]),
                                    expInfo[2], expInfo[3], expInfo[4]);
                            expObjects.add(exp);
                        }
                    } catch (Exception e) {
                        System.out.println("Cannot return expense");
                    }
                }
                sc.close();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        return expObjects;
    }

}

class categories {

    static void storeCategories(ArrayList<String> categories) {
        File categoriesFile = new File("categories.txt");
        if (categoriesFile.exists()) {
        } else {
            try {
                categoriesFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter categoriesFileWriter = new FileWriter("categories.txt");
            for (String category : categories) {
                categoriesFileWriter.write(category.toLowerCase() +  "\n");
            }
            categoriesFileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static ArrayList<String> readCategories() {
        ArrayList<String> categories = new ArrayList<>();
        File categoriesFile = new File("categories.txt");
        if (categoriesFile.exists()) {
            try {
                Scanner sc = new Scanner(categoriesFile);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] categoryArr = line.split("\n");
                    for (String string : categoryArr) {
                        categories.add(string);
                    }
                }
                sc.close();
            } catch (Exception e) {
                System.out.println("Cannot retrieve categories from file.");
            }
        }
        return categories;
    }

}

public class ExpenseTracker {
    static void waitForEnter() {
        System.out.println("\nPress enter to continue..");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        // arrayList to store categories
        ArrayList<String> categoriesList = categories.readCategories();
        // arraylist to store object of class expense
        ArrayList<expense> expObjects = expenseStorage.readExpense();
        Scanner sc = new Scanner(System.in);
        int option = 0;
        try {
            while (option != 7) {
                // code to clear screen
                clearScreen();
                // code to display name of project
                System.out.println("\t********************************");
                System.out.println(" \t\tExpense Tracker");
                System.out.println("\t********************************\n");

                // options displayed to user
                System.out.println("1)Add expense");
                System.out.println("2)Delete expense");
                System.out.println("3)Manage categories");
                System.out.println("4)View expense");
                System.out.println("5)Modify expense");
                System.out.println("6)Generate Report");
                System.out.println("7)Exit\n");

                // taking input
                System.out.print("Enter your option :");
                option = sc.nextInt();

                switch (option) {
                    case 1:
                        clearScreen();
                        System.out.println("\n***************");
                        System.out.println("Add Expense");
                        System.out.println("***************\n");
                        int exp_id = 0;
                        int amount = 0;
                        String category = new String();
                        String description = new String();
                        String date = new String();
                        try {
                            System.out.print("\nEnter expense ID :");
                            exp_id = sc.nextInt();
                            System.out.print("Enter Amount :");
                            amount = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Enter Category :");
                            category = sc.nextLine();
                            if (categoriesList.contains(category.toLowerCase())) {
                                System.out.print("Enter Description :");
                                description = sc.nextLine();
                                System.out.print("Enter Date(dd/mm/yyyy) :");
                                date = sc.nextLine();
                                expense exp = new expense(exp_id, amount, category, description, date);
                                try {
                                    expObjects.add(exp);
                                    expenseStorage.storeExpense(expObjects);
                                    System.out.println("\n\nInformation Added successfully");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("Category does not exist. First create category and try again.");
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input in one or more field. Try Again!");
                        }
                        waitForEnter();
                        break;

                    case 2:
                        clearScreen();
                        System.out.println("\n\n***************");
                        System.out.println("Delete Expense");
                        System.out.println("***************\n");
                        System.out.print("Enter Expense ID to delete expense :");
                        int deleteExpId = sc.nextInt();
                        boolean expenseDeleted = false;
                        try {
                            for (expense seperateExpense : expObjects) {
                                if (seperateExpense.getExp_id() == deleteExpId) {
                                    expObjects.remove(expObjects.indexOf(seperateExpense));
                                    System.out.println("Expense Deleted successfully");
                                    expenseDeleted = true;
                                }
                            }
                        } catch (Exception e) {
                            // System.out.println("Error while deleting expense.");
                        }
                        if (expenseDeleted == false)
                            System.out.println("Element not found");
                        expenseStorage.storeExpense(expObjects);
                        waitForEnter();
                        break;

                    case 3:
                        clearScreen();
                        System.out.println("\n*****************");
                        System.out.println("Manage Categories");
                        System.out.println("*****************\n");
                        System.out.println("1) Add category");
                        System.out.println("2) View categories");
                        System.out.println("3) Delete category");
                        System.out.println("4) Main Menu");
                        int categoryChoice = 0;
                        System.out.print("Enter your choice : ");
                        categoryChoice = sc.nextInt();
                        sc.nextLine();
                        switch (categoryChoice) {
                            case 1:
                                clearScreen();
                                System.out.println("\n**************");
                                System.out.println("Add Category");
                                System.out.println("**************\n");
                                // try catch block to manage incorrect input
                                System.out.print("\nEnter the name of the category :");
                                String categoryName = new String();
                                try {
                                    categoryName = sc.nextLine();
                                } catch (Exception e) {
                                    System.out.println("Incorrect Input. Please try again");
                                }
                                categoriesList.add(categoryName);
                                try {
                                    categories.storeCategories(categoriesList);
                                    System.out.println("\nCategory added successfully");
                                } catch (Exception e) {
                                    System.out.println("Error occured while storing categories");
                                }
                                waitForEnter();
                                break;
                            case 2:
                                clearScreen();
                                System.out.println("\n****************");
                                System.out.println("View Categories");
                                System.out.println("****************\n");
                                int i = 1;
                                System.out.println(""); // for gap between line to avoid confusion
                                for (String seperateCategory : categoriesList) {
                                    System.out.println(i + ")" + seperateCategory);
                                    i++;
                                }
                                waitForEnter();
                                break;

                            case 3:
                                clearScreen();
                                System.out.println("\n**************");
                                System.out.println("Delete Category");
                                System.out.println("**************\n");
                                System.out.println("Disclaimer :- Make sure the category name must be correct.");
                                System.out.print("\nEnter the category name to be deleted:");
                                String deleteCategory = new String();
                                try {
                                    deleteCategory = sc.nextLine();
                                } catch (Exception e) {
                                    System.out.println("Incorrect Input. Please try again");
                                }
                                boolean categoryDeleted = false;
                                try {
                                    for (String seperateCategory : categoriesList) {
                                        if (deleteCategory.toLowerCase().equalsIgnoreCase(seperateCategory)) {
                                            categoriesList.remove(categoriesList.indexOf(deleteCategory));
                                            categoryDeleted = true;
                                        }
                                    }
                                } catch (Exception e) {
                                    // System.out.println("Cannot delete category due to exception..");
                                }

                                if (categoryDeleted) {
                                    System.out.println("\nCategory deleted successfully");
                                } else {
                                    System.out.println("\nCategory not found");
                                }
                                categories.storeCategories(categoriesList);
                                waitForEnter();
                                break;

                            case 4 :
                                break;
                            default:
                                System.out.println("Invalid Choice!");
                        }
                        waitForEnter();
                        break;

                    case 4:
                        clearScreen();
                        System.out.println("\n\n***************");
                        System.out.println("View Expense");
                        System.out.println("***************\n");
                        int i = 1;
                        for (expense expense : expObjects) {
                            System.out.println(i + ") " + expense.getFormattedString());
                            i++;
                        }
                        System.out.println("\n");
                        waitForEnter();
                        break;

                    case 5:
                        clearScreen();
                        System.out.println("\n\n******************");
                        System.out.println("Modify Expense");
                        System.out.println("******************\n");
                        System.out.print("Enter Expense ID to modify expense :");
                        int modifyExpId = 0;
                        try {
                            modifyExpId = sc.nextInt();
                        } catch (Exception e) {
                            System.out.println("Incorrect input. Try again!");
                        }
                        expense modifyExpense = new expense();
                        expense tempExpense = new expense();
                        boolean expenseFound = false;
                        try {
                            for (expense seperateExpense : expObjects) {
                                if (seperateExpense.getExp_id() == modifyExpId) {
                                    modifyExpense = seperateExpense;
                                    expenseFound = true;
                                }
                            }
                        } catch (Exception e) {
                            // System.out.println("Cannot retrive expense for modification");
                        }
                        if (expenseFound) {
                            tempExpense = modifyExpense;
                            System.out.println("\nExpense Found. Enter new details:- \n");
                            System.out.print("\nEnter amount :");
                            int newAmount = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Enter category :");
                            String newCategory = sc.nextLine();
                            if (categoriesList.contains(newCategory.toLowerCase())) {
                                System.out.print("Enter description :");
                                String newDescription = sc.nextLine();
                                System.out.print("Enter date (dd/mm/yyyy) :");
                                String newDate = sc.nextLine();
                                modifyExpense.amount = newAmount;
                                modifyExpense.category = newCategory;
                                modifyExpense.description = newDescription;
                                modifyExpense.date = newDate;

                                try {
                                    expObjects.remove(expObjects.indexOf(tempExpense));
                                    expObjects.add(modifyExpense);
                                    System.out.println("\nExpense modified successfully....");
                                } catch (Exception e) {
                                    System.out.println("\nCannot modify expense...");
                                }
                            } else {
                                System.out.println("Category does not exist. First add category and try again.");
                            }
                        } else {
                            System.out.println("\nExpense not found. Try again...");
                        }
                        expenseStorage.storeExpense(expObjects);
                        waitForEnter();
                        break;

                    case 6:
                        clearScreen();
                        System.out.println("\t********************************");
                        System.out.println(" \tReport Generation");
                        System.out.println("\t********************************\n");
                        System.out.println("1) Generate report (category wise)");
                        System.out.println("2) Generate report (Date wise)");
                        int reportOption = 0;
                        System.out.print("Enter your option : ");
                        try {
                            reportOption = sc.nextInt();
                            sc.nextLine();
                        } catch (Exception e) {
                            System.out.println("Invalid input. Try again!");
                        }

                        switch (reportOption) {
                            case 1:
                            String categoryReport = new String();
                            int expCount = 0;
                            System.out.print("Enter category name to generate report :");
                            try {
                                categoryReport = sc.nextLine();
                            } catch (Exception e) {
                                System.out.println("Invalid input. Try again!");
                            }    
                            if(categoriesList.contains(categoryReport.toLowerCase())){
                                System.out.println("Report Generated.\n");
                                System.out.println("Category Name : "+ categoryReport + "\n");
                                System.out.println("************************************************************************************************************************");
                                System.out.println(" \t\t\t\t\t\tCategory Report");
                                System.out.println("************************************************************************************************************************\n");
                                for (expense reportExpense : expObjects) {
                                    if((reportExpense.getCategory()).equals(categoryReport.toLowerCase())){
                                        System.out.println(reportExpense.getFormattedString());
                                        expCount++;
                                    }
                                }
                                System.out.println("\n************************************************************************************************************************");
                                System.out.println("\nTotal no. of expense with category("+categoryReport+") :" + expCount);
                            }
                            else{
                                System.out.println("Category does not exist. Report generation not possible!");
                            }
                                break;
                            case 2 :
                            System.out.println("Currently under development....");
                                break;

                            default:
                            System.out.println("Invalid option. Try Again!");
                                break;
                        }
                        waitForEnter();
                        break;

                    case 7:
                        clearScreen();
                        System.out.println("\t********************************");
                        System.out.println(" \t\tExpense Tracker");
                        System.out.println("\t********************************\n");
                        System.out.println("Thank you for using our expense tracker, Goodbye...\n");
                        break;

                    default:
                        System.out.println("Invalid Choice. Try Again");
                        waitForEnter();
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Internal Error occured. Program terminated!");
        }
        sc.close();
    }
}