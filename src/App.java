import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter full file path: ");
        String path = sc.nextLine();
        System.out.print("Enter Salary: ");
        Double salaryMed = sc.nextDouble();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            
            List<Employee> list = new ArrayList<>();

            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Employee(fields[0], fields[1], Double.parseDouble(fields[2])));
                line = br.readLine();
            }

            // Email em ordem Alfabetica & salary > salaryMed
            
            Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

            List<String> emails = list.stream() 
                    .filter(p -> p.getSalary() > salaryMed) // Filtra as pessoas acima do salaryMed
                    .map(Employee::getEmail) // Transforma o objeto Employee na string
                    .sorted(comp) // Ordena em Ordem Alfabetica
                    .collect(Collectors.toList()); // Pega os emails da Lista
            
            System.out.println("Email of people whose salary is more than " + salaryMed + ":");
            emails.forEach(System.out::println);

            Double salaryNamesWithM = list.stream()
                    .filter(e -> e.getName().startsWith("M")) // Filtra apenas as Pessoas com inicial M
                    .map(Employee::getSalary) // Pega o salary
                    .reduce(0.0, (x, y) -> x + y); // Realiza os calculos

            System.out.println("Sum of Salary of people whose name starts with 'M': " + String.format("%.2f", salaryNamesWithM));
            
        } catch (IOException e) {
            System.out.println("Error");
        }
        sc.close();
    }
}
