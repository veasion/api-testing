package cn.veasion.auto.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ExcelImportUtils
 *
 * @author luozhuowei
 * @date 2021/9/21
 */
public class ExcelImportUtils {

    public static <T> List<T> parse(File file, Map<String, String> fieldMap, Class<T> clazz) throws IOException {
        return parse(file, fieldMap).toJavaList(clazz);
    }

    public static <T> List<T> parse(InputStream is, Map<String, String> fieldMap, Class<T> clazz) throws IOException {
        return parse(is, fieldMap).toJavaList(clazz);
    }

    public static JSONArray parse(File file, Map<String, String> fieldMap) throws IOException {
        return parse(new FileInputStream(file), fieldMap);
    }

    public static JSONArray parse(InputStream is, Map<String, String> fieldMap) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        XSSFSheet sheet = workbook.getSheetAt(0);
        return parse(sheet, fieldMap);
    }

    public static JSONArray parse(XSSFSheet sheet, Map<String, String> fieldMap) {
        JSONArray jsonArray = new JSONArray();
        XSSFRow headerRow = sheet.getRow(0);
        int lastRowNum = sheet.getLastRowNum();
        int lastCellNum = headerRow.getLastCellNum();
        if (lastRowNum <= 0 || lastCellNum < 0) {
            return jsonArray;
        }
        Map<Integer, String> cellIndexFieldMap = new HashMap<>();
        for (int i = 0; i < lastCellNum; i++) {
            XSSFCell cell = headerRow.getCell(i);
            String value = cell.getStringCellValue();
            if (value == null) {
                continue;
            }
            String field = fieldMap.get(value.trim());
            if (field != null) {
                cellIndexFieldMap.put(i, field);
            }
        }
        JSONObject jsonObject;
        for (int i = 1; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            jsonObject = new JSONObject();
            for (int j = 0; j < lastCellNum; j++) {
                XSSFCell cell = row.getCell(j);
                String field = cellIndexFieldMap.get(j);
                if (cell == null || field == null) {
                    continue;
                }
                Object value;
                if (CellType.NUMERIC.equals(cell.getCellType())) {
                    value = cell.getNumericCellValue();
                } else if (CellType.BOOLEAN.equals(cell.getCellType())) {
                    value = cell.getBooleanCellValue();
                } else {
                    value = cell.getStringCellValue();
                }
                jsonObject.put(field, value);
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

}
