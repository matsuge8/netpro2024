import java.util.Scanner;

public class MyExceptionHoliday {

    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("5月の日付を入力してください: ");
            int date = scanner.nextInt();
            try {
                checkHoliday(date);
                System.out.println("例外は発生しませんでした。これは休日や週末ではありません。");
            } catch (NoHolidayException e) {
                System.out.println("NoHolidayExceptionが発生しました： " + e.getMessage());
            }
        }
    }

    static void checkHoliday(int date) throws NoHolidayException {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException("無効な日付が入力されました。");
        }
        if (date == 3 || date == 6) {
            throw new NoHolidayException("この日付（" + date + "）は休日です。");
        }
        if (isWeekend(date)) {
            throw new NoHolidayException("この日付（" + date + "）は週末です。");
        }
    }

    static boolean isWeekend(int date) {
        
        return date % 7 == 4 || date % 7 == 5;
    }

}
