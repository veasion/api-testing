package cn.veasion.auto.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * ExcelUtils
 *
 * @author luozhuowei
 * @date 2021/9/21
 */
public class ExcelExportUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static void export(HttpServletResponse response, String fileName, String[] headers) throws IOException {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (String header : headers) {
            map.put(header, header);
        }
        export(response, fileName, map, null);
    }

    public static void export(HttpServletResponse response, String fileName, LinkedHashMap<String, String> fieldMap, List<?> list) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        createTable(workbook, fieldMap, list);
        toResponse(response, workbook, fileName);
    }

    public static void createTable(Workbook workbook, LinkedHashMap<String, String> fieldMap, List<?> list) {
        Sheet sheet = workbook.createSheet();
        createTable(sheet, getDefaultCellStyle(workbook), fieldMap, list);
    }

    public static void createTable(Sheet sheet, CellStyle cellStyle, LinkedHashMap<String, String> fieldMap, List<?> list) {
        int rowIndex = 0;
        String[] fields = fieldMap.keySet().toArray(new String[]{});
        List<String> names = new ArrayList<>(fieldMap.values());
        Cell cell;
        Row row = sheet.createRow(rowIndex++);
        for (int i = 0; i < names.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(names.get(i));
            cell.setCellStyle(cellStyle);
        }
        if (list == null || list.isEmpty()) {
            return;
        }
        JSONArray jsonArray = (JSONArray) JSON.toJSON(list);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Row cellRow = sheet.createRow(rowIndex++);
            for (int j = 0; j < fields.length; j++) {
                cell = cellRow.createCell(j);
                Object value = obj.get(fields[j]);
                cell.setCellStyle(cellStyle);
                if (value == null) {
                    cell.setCellValue("");
                    continue;
                }
                if (value instanceof Date) {
                    cell.setCellValue(new SimpleDateFormat(DATE_FORMAT).format((Date) value));
                } else {
                    cell.setCellValue(String.valueOf(value));
                }
            }
        }
    }

    public static CellStyle getDefaultCellStyle(Workbook workbook) {
        return getDefaultCellStyle(workbook, false);
    }

    public static CellStyle getDefaultCellStyle(Workbook workbook, boolean border) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        if (border) {
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
        }
        return cellStyle;
    }

    public static void toResponse(HttpServletResponse response, Workbook workbook, String fileName) throws IOException {
        setResponseHeader(response, fileName);
        workbook.write(response.getOutputStream());
    }

    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
    }

}
