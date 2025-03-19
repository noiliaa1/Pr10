import java.util.Scanner;

public class Main {
    private static final int USERS_LIMIT = 15;
    private static final String[] usernames = new String[USERS_LIMIT];
    private static final String[] passwords = new String[USERS_LIMIT];
    private static int usersCount = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Додати користувача");
            System.out.println("2. Видалити користувача");
            System.out.println("3. Аутентифікація");
            System.out.println("4. Вихід");
            System.out.print("Оберіть дію: ");

            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> addUser(sc);
                    case 2 -> removeUser(sc);
                    case 3 -> authenticateUser(sc);
                    case 4 -> {
                        System.out.println("Вихід з програми.");
                        return;
                    }
                    default -> System.out.println("Невірний вибір.");
                }
            } else {
                System.out.println("Будь ласка, введіть число.");
                sc.nextLine();
            }
        }
    }

    private static void addUser(Scanner sc) {
        if (usersCount >= USERS_LIMIT) {
            System.out.println("Не можна додати більше користувачів.");
            return;
        }

        System.out.print("Введіть ім’я користувача: ");
        String username = sc.nextLine().trim();
        if (username.length() < 5 || username.contains(" ")) {
            System.out.println("Ім’я користувача має містити не менше 5 символів і не повинно містити пробілів.");
            return;
        }

        System.out.print("Введіть пароль: ");
        String password = sc.nextLine();
        if (!validatePassword(password)) return;

        usernames[usersCount] = username;
        passwords[usersCount] = password;
        usersCount++;
        System.out.println("Користувача додано.");
    }

    private static void removeUser(Scanner sc) {
        System.out.print("Введіть ім’я користувача для видалення: ");
        String username = sc.nextLine();

        for (int i = 0; i < usersCount; i++) {
            if (usernames[i].equals(username)) {
                usernames[i] = usernames[usersCount - 1];
                passwords[i] = passwords[usersCount - 1];
                usernames[usersCount - 1] = null;
                passwords[usersCount - 1] = null;
                usersCount--;
                System.out.println("Користувача видалено.");
                return;
            }
        }
        System.out.println("Користувача з таким ім’ям не знайдено.");
    }

    private static void authenticateUser(Scanner sc) {
        System.out.print("Введіть ім’я користувача для аутентифікації: ");
        String username = sc.nextLine();
        System.out.print("Введіть пароль: ");
        String password = sc.nextLine();

        for (int i = 0; i < usersCount; i++) {
            if (usernames[i].equals(username) && passwords[i].equals(password)) {
                System.out.println("Аутентифікація успішна.");
                return;
            }
        }
        System.out.println("Невірне ім’я користувача або пароль.");
    }

    private static boolean validatePassword(String password) {
        if (password.length() < 10 || password.contains(" ")) {
            System.out.println("Пароль має бути не менше 10 символів і не містити пробілів.");
            return false;
        }

        String[] forbiddenPasswords = {"admin", "pass", "password", "qwerty", "ytrewq"};
        for (String forbidden : forbiddenPasswords) {
            if (password.contains(forbidden)) {
                System.out.println("Пароль не може містити заборонені слова.");
                return false;
            }
        }

        boolean hasSpecialChar = false, hasDigit = false;
        int digitCount = 0;

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
                digitCount++;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        if (!hasDigit || !hasSpecialChar || digitCount < 3) {
            System.out.println("Пароль має містити хоча б 1 спеціальний символ та хоча б 3 цифри.");
            return false;
        }
        return true;
    }
}
