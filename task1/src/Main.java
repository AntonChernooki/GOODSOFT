import Models.IoFileCopyStrategy;
import Models.Nio2FileCopyStrategy;
import Models.NioFileCopyStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    private final Scanner scanner;

    public Main() {
        this.scanner = new Scanner(System.in);
    }

    void main() {
        while (true) {
            try {
                System.out.println("выберите способ копирования, 1-io 2-nio 3-nio2 0-выход");
                int strategy;
                try {
                    strategy = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка, введите число ");
                    continue;
                }

                if (strategy == 0) {
                    System.out.println("Выход из программы ");
                    break;
                }

                if (strategy > 3 || strategy < 0) {
                    System.out.println("Ошибка, введите число от 1 до 3");
                    continue;
                }

                Path sourcePath = getSourсePath();
                if (sourcePath == null) {
                    System.out.println("Возврат в главное меню");
                    continue;
                }

                Path destinationPath = getDestinationPath();
                if (destinationPath == null) {
                    System.out.println("Операция отменена");
                    continue;
                }

                System.out.println("что копируем " + sourcePath);
                System.out.println("куда копируем " + destinationPath);

                switch (strategy) {
                    case 1:
                        System.out.println("Выбрана стратегия IO");
                        IoFileCopyStrategy ioFileCopyStrategy = new IoFileCopyStrategy();
                        ioFileCopyStrategy.copy(sourcePath, destinationPath);
                        break;
                    case 2:
                        System.out.println("Выбрана стратегия NIO");
                        NioFileCopyStrategy nioFileCopyStrategy = new NioFileCopyStrategy();
                        nioFileCopyStrategy.copy(sourcePath, destinationPath);
                        break;
                    case 3:
                        System.out.println("Выбрана стратегия NIO2");
                        Nio2FileCopyStrategy nio2FileCopyStrategy = new Nio2FileCopyStrategy();
                        nio2FileCopyStrategy.copy(sourcePath, destinationPath);
                        break;
                }

            } catch (IOException e) {
                System.out.println("Ошибка ввода-вывода: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Неожиданная ошибка: " + e.getMessage());
                e.printStackTrace();
            }
        }
        scanner.close();
    }

    private Path getDestinationPath() {
        while (true) {
            try {
                System.out.println("Введите путь в место копирования или exit для отмены:");
                String pathStr = scanner.nextLine().trim();


                if (pathStr.equalsIgnoreCase("exit")) {
                    return null;
                }

                if (pathStr.isEmpty()) {
                    System.out.println("Вы ввели пустой путь");
                    continue;
                }

                Path path = Path.of(pathStr);

                if (Files.exists(path)) {
                    while (true) {
                        System.out.println("Файл уже существует. Перезаписать? 1-да, 2-нет, 3-отмена, exit-отмена");
                        String answerStr = scanner.nextLine().trim();

                        // Проверка на exit
                        if (answerStr.equalsIgnoreCase("exit")) {
                            return null;
                        }

                        int answer;
                        try {
                            answer = Integer.parseInt(answerStr);
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка, введите число или 'exit'");
                            continue;
                        }

                        if (answer < 1 || answer > 3) {
                            System.out.println("Ошибка, введите число от 1 до 3");
                            continue;
                        }

                        if (answer == 1) {
                            return path;
                        } else if (answer == 2) {
                            break;
                        } else {
                            return null;
                        }
                    }
                } else {
                    return path;
                }
            } catch (InvalidPathException e) {
                System.out.println("Ошибка: неверный формат пути - " + e.getMessage());
            } catch (SecurityException e) {
                System.out.println("Ошибка доступа: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Неожиданная ошибка : " + e.getMessage());
            }
        }
    }

    private Path getSourсePath() {
        while (true) {
            try {
                System.out.println("Введите путь к копируемому файлу или exit для выхода:");
                String pathStr = scanner.nextLine().trim();

                if (pathStr.equalsIgnoreCase("exit")) {
                    return null;
                }

                if (pathStr.isEmpty()) {
                    System.out.println("Ошибка: путь не может быть пустым!");
                    continue;
                }

                Path path = Path.of(pathStr);

                if (!Files.exists(path)) {
                    System.out.println("Ошибка: файл не существует!");
                    continue;
                }

                if (!Files.isRegularFile(path)) {
                    System.out.println("Ошибка: указанный путь не является файлом ");
                    continue;
                }

                if (!Files.isReadable(path)) {
                    System.out.println("Ошибка: нет прав на чтение файла");
                    continue;
                }

                return path;

            } catch (InvalidPathException e) {
                System.out.println("Ошибка: неверный формат пути - " + e.getMessage());
            } catch (SecurityException e) {
                System.out.println("Ошибка доступа: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Неожиданная ошибка : " + e.getMessage());
            }
        }
    }
}