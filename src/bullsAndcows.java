import java.util.*;

public class bullsAndcows {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Игра 'Быки и коровы'");

        while (true) {
            System.out.println("\nВыберите режим:");
            System.out.println("1 - Вы отгадываете число, загаданное компьютером");
            System.out.println("2 - Два игрока (Игрок 1 загадывает, Игрок 2 отгадывает)");
            System.out.println("3 - Выход");
            System.out.print("Ваш выбор: ");

            String choice = scanner.nextLine().trim();
            if (choice.equals("0")) break;

            switch (choice) {
                case "1" -> playerGuesses();
                case "2" -> twoPlayers();
                default -> System.out.println("Неверный выбор. Введите 1, 2 или 3.");
            }
        }

        System.out.println("Спасибо за игру!)");
    }

    // игрок отгадывает число компьютера
    private static void playerGuesses() {
        String secret = generateSecret();
        System.out.println("Компьютер загадал 4-значное число с неповторяющимися цифрами");
        int attempts = 0;

        while (true) {
            System.out.print("Введите ваше число (или 'exit' для возврата в меню): ");
            String guess = scanner.nextLine().trim();
            if (guess.equalsIgnoreCase("exit")) return;

            if (isValidGuess(guess)) {
                System.out.println("Введите 4 разные цифры, например: 0123");
                continue;
            }

            attempts++;
            int bulls = countBulls(secret, guess);
            int cows = countCows(secret, guess) - bulls;
            System.out.printf("Результат: %d быков, %d коров%n", bulls, cows);
            if (bulls == 4) {
                System.out.printf("Поздравляем! Вы угадали число %s за %d попыток.%n", secret, attempts);
                return;
            }
        }
    }

    // Режим: два игрока
    private static void twoPlayers() {
        System.out.print("Игрок 1: введите секретное 4-значное число: ");
        String secret = scanner.nextLine().trim();
        while (isValidGuess(secret)) {
            System.out.print("Введите 4 разные цифры, например 0123: ");
            secret = scanner.nextLine().trim();
        }
        System.out.println("\nИгрок 2: отгадывайте. (Введите 'exit' чтобы вернуться в меню.)");
        int attempts = 0;
        while (true) {
            System.out.print("Игрок 2, попытка: ");
            String guess = scanner.nextLine().trim();
            if (guess.equalsIgnoreCase("exit")) return;
            if (isValidGuess(guess)) {
                System.out.println("Неверный ввод. Введите 4 уникальные цифры.");
                continue;
            }
            attempts++;
            int bulls = countBulls(secret, guess);
            int cows = countCows(secret, guess) - bulls;
            System.out.printf("%d быков, %d коров%n", bulls, cows);

            if (bulls == 4) {
                System.out.printf("Игрок 2 выиграл! Угадано число %s за %d попыток.%n", secret, attempts);
                return;
            }
        }
    }

    // Генерация секретного 4-значного числа с неповторяющимися цифрами.
    private static String generateSecret() {
        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i <= 9; i++) digits.add(i);
        Collections.shuffle(digits);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) sb.append(digits.get(i));
        return sb.toString();
    }

    // Проверка, что в строке 4 знака, все цифры и все уникальны
    private static boolean isValidGuess(String s) {
        if (s == null || s.length() != 4) return true;
        Set<Character> seen = new HashSet<>();
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) return true;
            seen.add(c);
        }
        return seen.size() != 4;
    }

    // Подсчёт быков (правильная цифра и позиция)
    private static int countBulls(String secret, String guess) {
        int bulls = 0;
        for (int i = 0; i < 4; i++) {
            if (secret.charAt(i) == guess.charAt(i)) bulls++;
        }
        return bulls;
    }

    // Подсчёт числа совпадающих цифр (включая быков)
    private static int countCows(String secret, @org.jetbrains.annotations.NotNull String guess) {
        int count = 0;
        for (char c : guess.toCharArray()) {
            if (secret.indexOf(c) >= 0) count++;
        }
        return count;
    }
}
