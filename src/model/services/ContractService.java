package model.services;

import model.entities.Contract;
import model.entities.Installment;

import java.time.LocalDate;

public class ContractService {

    private OnlinePaymentService onlinePaymentService;

    public ContractService(OnlinePaymentService onlinePaymentService) {
        this.onlinePaymentService = onlinePaymentService;
    }

    public void processContract(Contract contract, Integer months) {

        for (int i = 1; i <= months; i++) {

            double basicQuota = contract.getTotalValue() / months;
            double interest = onlinePaymentService.interest(basicQuota, i);
            double subtotal = basicQuota + interest;
            double fee = onlinePaymentService.paymentFee(subtotal);
            double total = subtotal + fee;

            LocalDate dueDate = contract.getDate().plusMonths(i);
            Installment inst = new Installment(dueDate, total);
            contract.addInstallment(inst);
        }
    }
}
