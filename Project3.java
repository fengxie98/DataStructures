import java.io.PrintStream;
import java.util.Scanner;
import java.util.ArrayList;

public class Project3
{
	private static Scanner      console;
	private static PrintStream  printer;
	private static BST<Integer> bst;

	static
	{
		console = new Scanner(System.in);
		printer = System.out;
	}

	public static void main(String[] args)
	{
		printer.println("Please enter the initial sequence of values:");

		bst = new BST<Integer>(getValues());

		printer.println(
			bst.getPreOrder() + "\n" +
			bst.getInOrder() + "\n" +
			bst.getPostOrder()
		);

		while(!runCommand());

		printer.println("Thank you for using my program!");

		console.close();
	}

	private static boolean runCommand()
	{
		String command = "";
		boolean is_valid;
		boolean quit = false;

		do {
			Scanner line;
			int value = Integer.MIN_VALUE;

			is_valid = true;

			printer.print("Command? ");
			line = new Scanner(console.nextLine());

			if(line.hasNext())    command = line.next();
			if(line.hasNextInt()) value   = line.nextInt();

			if(command.length() != 1) is_valid = false;
			else {
				switch(command) {
					case "I":
					case "D":
					case "P":
					case "S": {
						if(value == Integer.MIN_VALUE) is_valid = false;
						else {
							String result = "";

							if(command.equals("I"))      result = bst.insertNode(value);
							else if(command.equals("D")) result = bst.deleteNode(value);
							else if(command.equals("P")) result = bst.getPredecessor(value);
							else if(command.equals("S")) result = bst.getSuccessor(value);

							printer.println(result);
						}
					} break;

					case "E": {
						quit = true;
					} break;

					case "H": {
						printer.println(
							"I  Insert a value\n" +
							"D  Delete a value\n" +
							"P  Find predecessor\n" +
							"S  Find successor\n" +
							"E  Exit the program\n" +
							"H  Display this message"
						);
					} break;

					default: {
						is_valid = false;
					} break;
				}
			}

			line.close();

			if(!is_valid) printer.println("Invalid command or bad syntax!");
		} while(!is_valid);

		return quit;
	}

	private static ArrayList<Integer> getValues()
	{
		Scanner line = new Scanner(console.nextLine());
		ArrayList<Integer> values = new ArrayList<>();

		while(line.hasNextInt()) values.add(line.nextInt());

		line.close();

		return values;
	}
}
