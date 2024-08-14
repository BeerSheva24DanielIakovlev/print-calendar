package telran.time;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

record MonthYear(int month, int year) {}

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 1 || args.length > 3) {
                throw new IllegalArgumentException("Please enter the correct date format");
            }

            MonthYear monthYear = getMonthYear(args);
            printCalendar(monthYear);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printCalendar(MonthYear monthYear) {
        printTitle(monthYear);
        printWeekDays();
        printDates(monthYear);
    }

    private static void printTitle(MonthYear monthYear) {
        String monthName = Month.of(monthYear.month()).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String title = String.format("%d, %s", monthYear.year(), monthName);
        int width = 28;
        int padding = (width - title.length()) / 2;

        System.out.println(" ".repeat(padding) + title);
        System.out.println("----------------------------");
    }

    private static void printWeekDays() {
        System.out.println("Sun Mon Tue Wed Thu Fri Sat");
    }

    private static void printDates(MonthYear monthYear) {
        int firstDayOfWeek = getFirstDayOfWeek(monthYear);
        int lastDayOfMonth = getLastDayOfMonth(monthYear);

        System.out.print(" ".repeat(4 * firstDayOfWeek));

        int day = 1;
        while (day <= lastDayOfMonth) {
            System.out.printf("%3d ", day);
            if ((day + firstDayOfWeek) % 7 == 0) {
                System.out.println();
            }
            day++;
        }

        System.out.println();
    }

    private static MonthYear getMonthYear(String[] args) {
        LocalDate date = LocalDate.now();
        if (args.length == 2) {
            int month = Integer.parseInt(args[0]);
            int year = Integer.parseInt(args[1]);
            return new MonthYear(month, year);
        } else if (args.length == 3) {
            int month = Integer.parseInt(args[1]);
            int year = Integer.parseInt(args[2]);
            return new MonthYear(month, year);
        }
        return new MonthYear(date.getMonthValue(), date.getYear());
    }

    private static int getFirstDayOfWeek(MonthYear monthYear) {
        LocalDate firstDay = LocalDate.of(monthYear.year(), monthYear.month(), 1);
        return (firstDay.getDayOfWeek().getValue()) % 7;
    }

    // private int getOffset(int firstWeekDay) {
    //     //TOD
    //     //shift on this offset for starting printin
    //     return -1;
    // }

    private static int getLastDayOfMonth(MonthYear monthYear) {
        return Month.of(monthYear.month()).length(isLeapYear(monthYear.year()));
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
