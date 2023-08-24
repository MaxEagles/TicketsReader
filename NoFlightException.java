public class NoFlightException extends Exception {
    public NoFlightException(String originName, String destinationName) {
        super("Не найдены рейсы между городами " + originName + " и " + destinationName);
    }
}
