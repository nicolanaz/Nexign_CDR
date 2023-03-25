package cdr;

public class Tariff {
    private String index;
    private int price;
    private int freeMinutes;
    private double perIncomingMinute;
    private double perOutgoingMinute;

    Tariff(String index, int price, int freeMinutes, double perIncomingMinute, double perOutgoingMinute) {
        this.index = index;
        this.price = price;
        this.freeMinutes = freeMinutes;
        this.perIncomingMinute = perIncomingMinute;
        this.perOutgoingMinute = perOutgoingMinute;
    }

    public static Tariff getByIndex(String index) {
        switch (index) {
            case "06": return new Tariff("06", 100, 300, 1, 1);
            case "03": return new Tariff("03", 0, 0, 1.5, 1.5);
            case "11": return new Tariff("11", 0, 100, 0, 1.5);
        }
        return null;
    }

    public String getIndex() {
        return index;
    }

    public int getPrice() {
        return price;
    }

    public double getCost(int minutes, String type) {
        switch (index) {
            case "06" -> {
                if (freeMinutes >= minutes) {
                    freeMinutes -= minutes;
                    return 0;
                } else {
                    minutes -= freeMinutes;
                    return perOutgoingMinute * minutes;
                }
            }

            case "03" -> {
                return perOutgoingMinute * minutes;
            }

            case "11" -> {
                if (type.equals("02")) {
                    return 0;
                }

                if (freeMinutes >= minutes) {
                    freeMinutes -= minutes;
                    return 0.5 * minutes;
                } else {
                    minutes -= freeMinutes;
                    return perOutgoingMinute * minutes;
                }
            }
        }
        return 0;
    }
}
