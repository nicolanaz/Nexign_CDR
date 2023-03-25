package cdr;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CallDataParser {
    private List<Subscriber> subscribers = new ArrayList<>();

    public void parse(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = null;

            while ((line = reader.readLine()) != null) {
                String[] attributes = line.split(", ");

                String type = attributes[0];
                String subscriberNumber = attributes[1];
                String callStart = attributes[2];
                String callEnd = attributes[3];
                String tariffType = attributes[4];

                Call call = new Call(type, getTime(callStart), getTime(callEnd));

                Subscriber subscriber = getSubscriber(subscriberNumber, tariffType);
                Tariff tariff = subscriber.getTariff();

                double cost = tariff.getCost(call.getMinutes(), call.getCallType());
                call.setCost(cost);

                subscriber.addCall(call);
            }
        }

        saveInformation();
    }

    private void saveInformation() throws IOException {
        if (!Files.exists(Path.of("reports/"))) {
            Files.createDirectory(Path.of("reports"));
        }

        for (Subscriber subscriber : subscribers) {
            File file = new File("reports/" + subscriber.getPhoneNumber() + ".txt");

            try (PrintWriter printWriter = new PrintWriter(file)) {
                printWriter.println(subscriber);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private Subscriber getSubscriber(String number, String tariffIndex) {
        if (!isSubscriberExist(number)) {
            Subscriber subscriber = new Subscriber(number);
            subscriber.setTariff(tariffIndex);
            subscribers.add(subscriber);

            return subscriber;
        }
        return getSubByNumber(number);
    }

    private boolean isSubscriberExist(String phoneNumber) {
        for (Subscriber subscriber : subscribers) {
            if (subscriber.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    private Subscriber getSubByNumber(String phoneNumber) {
        for (Subscriber subscriber : subscribers) {
            if (subscriber.getPhoneNumber().equals(phoneNumber)) {
                return subscriber;
            }
        }
        return null;
    }

    private LocalDateTime getTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(time));

        return localDateTime;
    }
}
