package cdr;

import java.util.*;

public class Subscriber {
    private String phoneNumber;
    private Tariff tariff;
    private Set<Call> calls = new TreeSet<>(new Comparator<Call>() {
        @Override
        public int compare(Call o1, Call o2) {
            return o1.getEndTime().compareTo(o2.getEndTime());
        }
    });

    private double cost;

    public Subscriber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(String tariffIndex) {
        this.tariff = Tariff.getByIndex(tariffIndex);
        this.cost = tariff.getPrice();
    }

    public Set<Call> getCalls() {
        return calls;
    }

    public void addCall(Call call) {
        calls.add(call);
        cost += call.getCost();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Tariff index: %s\n", tariff.getIndex()));

        sb.append(String.format(
                        "----------------------------------------------------------------------------\n" +
                        "Report for phone number %s:\n" +
                        "----------------------------------------------------------------------------\n" +
                        "| Call Type |   Start Time        |     End Time        | Duration | Cost  |\n" +
                        "----------------------------------------------------------------------------\n"
                , phoneNumber
        ));

        for (Call call : calls) {
            sb.append(String.format("|     %s    | %s | %s | %s |  %.2f |\n",
                    call.getCallType(),
                    call.getStartTime(),
                    call.getEndTime(),
                    call.getCallDuration(),
                    call.getCost()));
        }

        sb.append(String.format(
                        "----------------------------------------------------------------------------\n" +
                        "|                                           Total Cost: |     %.2f rubles |\n" +
                        "----------------------------------------------------------------------------", cost
        ));

        return sb.toString();
    }
}
