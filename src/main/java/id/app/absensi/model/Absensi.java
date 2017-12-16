package id.app.absensi.model;

import com.google.gson.Gson;
import com.poiji.annotation.ExcelCell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Absensi {

    @ExcelCell(0)
    private String uid;

    @ExcelCell(1)
    private String name;

    @ExcelCell(2)
    private String time;

    @ExcelCell(3)
    private String status;

    private String hour;

    private String date;

    public Absensi() {
    }

    public Absensi(String uid, String name, String time, String status) {
        this.uid = uid;
        this.name = name;
        this.time = time;
        this.status = status;
    }

    public String getUid() {
        return uid.replaceFirst(getPrefix(), getReplacement(getPrefix()));
    }

    private String getPrefix() {
        return uid.substring(0, 2);
    }

    private String getReplacement(String prefix) {
        switch (prefix) {
            case "50":
                return "K";
            case "51":
                return "L";
            case "59":
                return "M";
            default: // Optional
                return prefix;
        }
    }

    public String getName() {
        return name.replace("S", "Z");
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    private Date getDateTime() {
        if (isNumeric(getTime())) {
            return DateUtil.getJavaDate(Double.parseDouble(getTime()));
        } else {
            Date date = null;
            try {
                date = new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse(getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return new SimpleDateFormat("MM/dd/yyyy").format(getDateTime());
    }

    public String getDate() {
        return new SimpleDateFormat("hh:mm:ss").format(getDateTime());
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        this.setHour(getHour());
        this.setDate(getDate());
        return gson.toJson(this);
    }

    private boolean isNumeric(String value) {
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}