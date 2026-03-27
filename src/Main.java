import model.entities.Contract;
import model.entities.Installment;
import model.services.ContractService;
import model.services.PaypalService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Entre os dados do contrato: ");
        System.out.print("Numero: ");
        int number = sc.nextInt();
        System.out.print("Data (dd/MM/yyyy): ");
        sc.nextLine();
        LocalDate date = LocalDate.parse(sc.nextLine(), fmt);
        System.out.print("Valor do contrato: ");
        double value = sc.nextDouble();
        System.out.print("Entre o número de parcelas: ");
        int installment = sc.nextInt();

        Contract contract = new Contract(number, date, value);
        ContractService cs = new ContractService(new PaypalService());
        cs.processContract(contract, installment);

        System.out.println();
        System.out.println("Parcelas: ");
        for (Installment inst : contract.getInstallments()) {
            System.out.println(inst.getDueDate().format(fmt) + " - " + String.format("%.2f", inst.getAmount()));
        }
        sc.close();
    }
}