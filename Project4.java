import java.io.PrintStream;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Project4
{
	private static final int TEST_COUNT = 20;
	private static final int HEAP_SIZE  = 100;

	private static Scanner       console = new Scanner(System.in);
	private static PrintStream   printer = System.out;
	private static Heap<Integer> heap    = new Heap<>();

	public static void main(String[] args)
	{
		while(!runCommand());

		console.close();
	}

	private static boolean runCommand()
	{
		boolean is_valid;
		boolean quit = false;

		do {
			is_valid = true;

			printer.print(
				"=====================================================================\n" +
				"Please select how to test the program:\n" +
				"(1) 20 sets of 100 randomly generated integers\n" +
				"(2) Fixed integer values 1-100\n" +
				"Enter command: "
			);

			String line = console.nextLine();
			Scanner line_scanner = new Scanner(line);
			int command = 0;

			if(!line_scanner.hasNextInt()) is_valid = false;
			else {
				command = line_scanner.nextInt();
				is_valid = !line_scanner.hasNext();
			}

			switch(command) {
				case 1: {
					runTest1();
				} break;

				case 2: {
					runTest2();
				} break;

				case 3: {
					quit = true;
				} break;

				default: {
					is_valid = false;
				} break;
			}

			line_scanner.close();

			if(!is_valid) printer.println("Invalid command: '" + line + "'");
		} while(!is_valid);

		return quit;
	}

	private static void runTest1()
	{
		Random r = new Random();
		int sequential_total = 0;
		int optimal_total = 0;

		for(int i = 1; i <= TEST_COUNT; ++i) {
			ArrayList<Integer> values = new ArrayList<>();

			for(int j = 1; j <= HEAP_SIZE; ++j) values.add(j);

			for(int j = 0; j < HEAP_SIZE; ++j) {
				Collections.swap(values, j, r.nextInt(HEAP_SIZE));
			}

			heap.setValues(values);
			sequential_total += heap.buildSequential();

			heap.setValues(values);
			optimal_total += heap.buildOptimal();
		}

		printer.println(
			"\nAverage swaps for series of insertions: " + (sequential_total / TEST_COUNT) +
			"\nAverage swaps for optimal method: " + (optimal_total / TEST_COUNT)
		);
	}

	private static void runTest2()
	{
		ArrayList<Integer> values = new ArrayList<>();
		int sequential_swaps;
		int optimal_swaps;

		for(int i = 1; i <= HEAP_SIZE; ++i) values.add(i);

		heap.setValues(values);
		sequential_swaps = heap.buildSequential();

		printer.println(
			"\nHeap built using series of insertions: " + heap.asString(10) + "...\n" +
			"Number of swaps: " + sequential_swaps
		);

		for(int i = 1; i <= 10; ++i) heap.deleteRoot();

		printer.println("Heap after 10 removals: " + heap.asString(10) + "...");

		heap.setValues(values);
		optimal_swaps = heap.buildOptimal();

		printer.println(
			"\nHeap built using optimal method: " + heap.asString(10) + "...\n" +
			"Number of swaps: " + optimal_swaps
		);

		for(int i = 1; i <= 10; ++i) heap.deleteRoot();

		printer.println("Heap after 10 removals: " + heap.asString(10) + "...");
	}
}
