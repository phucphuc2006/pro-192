package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * Lop tien ich kiem tra du lieu dau vao.
 * Cung cap cac phuong thuc validate cho email, ngay thang, so dien thoai, v.v.
 * 
 * @author StudentManagement Team
 * @version 1.0
 */
public class ValidationUtils {

    // Pattern cho email
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Pattern cho so dien thoai Viet Nam
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(0|\\+84)[0-9]{9,10}$");

    // Pattern cho ma sinh vien (VD: SV001, SV100)
    private static final Pattern STUDENT_ID_PATTERN = Pattern.compile("^[A-Za-z]{2,3}[0-9]{3,6}$");

    // Format ngay thang
    private static final String[] DATE_FORMATS = {
            "dd/MM/yyyy",
            "dd-MM-yyyy",
            "yyyy-MM-dd"
    };

    /**
     * Kiem tra email hop le.
     * 
     * @param email Dia chi email can kiem tra
     * @return true neu email hop le, false neu khong
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Kiem tra so dien thoai hop le (Viet Nam).
     * 
     * @param phone So dien thoai can kiem tra
     * @return true neu hop le, false neu khong
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        // Loai bo khoang trang va dau gach
        String cleanPhone = phone.replaceAll("[\\s-]", "");
        return PHONE_PATTERN.matcher(cleanPhone).matches();
    }

    /**
     * Kiem tra ngay thang hop le.
     * Ho tro cac format: dd/MM/yyyy, dd-MM-yyyy, yyyy-MM-dd
     * 
     * @param dateStr Chuoi ngay thang can kiem tra
     * @return true neu hop le, false neu khong
     */
    public static boolean isValidDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return false;
        }

        for (String format : DATE_FORMATS) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                sdf.setLenient(false); // Kiem tra nghiem ngat
                sdf.parse(dateStr.trim());
                return true;
            } catch (ParseException e) {
                // Thu format tiep theo
            }
        }
        return false;
    }

    /**
     * Kiem tra ma sinh vien hop le.
     * 
     * @param studentId Ma sinh vien can kiem tra
     * @return true neu hop le, false neu khong
     */
    public static boolean isValidStudentId(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return false;
        }
        return STUDENT_ID_PATTERN.matcher(studentId.trim()).matches();
    }

    /**
     * Kiem tra chuoi khong rong.
     * 
     * @param str Chuoi can kiem tra
     * @return true neu khong rong, false neu rong hoac null
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * Kiem tra so nguyen duong.
     * 
     * @param value Gia tri can kiem tra
     * @return true neu la so nguyen duong, false neu khong
     */
    public static boolean isPositiveInteger(int value) {
        return value > 0;
    }

    /**
     * Kiem tra diem so hop le (0-10).
     * 
     * @param score Diem can kiem tra
     * @return true neu trong khoang 0-10, false neu khong
     */
    public static boolean isValidScore(double score) {
        return score >= 0 && score <= 10;
    }

    /**
     * Kiem tra so tin chi hop le (1-10).
     * 
     * @param credits So tin chi can kiem tra
     * @return true neu trong khoang 1-10, false neu khong
     */
    public static boolean isValidCredits(int credits) {
        return credits >= 1 && credits <= 10;
    }

    /**
     * Kiem tra gioi tinh hop le.
     * 
     * @param gender Gioi tinh can kiem tra
     * @return true neu la "Nam" hoac "Nu", false neu khong
     */
    public static boolean isValidGender(String gender) {
        if (gender == null)
            return false;
        String g = gender.trim().toLowerCase();
        return g.equals("nam") || g.equals("nu") ||
                g.equals("male") || g.equals("female");
    }

    /**
     * Chuan hoa gioi tinh ve dang "Nam" hoac "Nu".
     * 
     * @param gender Gioi tinh can chuan hoa
     * @return "Nam" hoac "Nu"
     */
    public static String normalizeGender(String gender) {
        if (gender == null)
            return "Nam";
        String g = gender.trim().toLowerCase();
        if (g.equals("nu") || g.equals("female") || g.equals("nữ")) {
            return "Nu";
        }
        return "Nam";
    }

    /**
     * Lay thong bao loi cho email khong hop le.
     * 
     * @return Thong bao loi
     */
    public static String getEmailError() {
        return "Email khong hop le! (VD: example@domain.com)";
    }

    /**
     * Lay thong bao loi cho so dien thoai khong hop le.
     * 
     * @return Thong bao loi
     */
    public static String getPhoneError() {
        return "So dien thoai khong hop le! (VD: 0901234567)";
    }

    /**
     * Lay thong bao loi cho ngay thang khong hop le.
     * 
     * @return Thong bao loi
     */
    public static String getDateError() {
        return "Ngay thang khong hop le! (VD: 01/01/2000)";
    }

    /**
     * Lay thong bao loi cho diem khong hop le.
     * 
     * @return Thong bao loi
     */
    public static String getScoreError() {
        return "Diem phai trong khoang 0-10!";
    }
}
