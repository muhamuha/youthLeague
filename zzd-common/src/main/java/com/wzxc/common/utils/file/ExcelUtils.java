package com.wzxc.common.utils.file;

import com.wzxc.common.annotation.InsertBatchParam;
import com.wzxc.common.annotation.InsertBatchParams;
import com.wzxc.common.exception.InsertBatchException;
import com.wzxc.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.ibatis.annotations.Insert;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class ExcelUtils {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    // 判断Excel的版本,获取Workbook
    public static Workbook getWorkbok(MultipartFile file) throws IOException {
        Workbook wb = null;
        if(file.getOriginalFilename().endsWith(EXCEL_XLS)){ //Excel 2003
            try{
                wb = new HSSFWorkbook(file.getInputStream());
            } catch (Exception e){
                wb = new XSSFWorkbook(file.getInputStream());
            }

        } else if(file.getOriginalFilename().endsWith(EXCEL_XLSX)){  // Excel 2007/2010
            wb = new XSSFWorkbook(file.getInputStream());
        }
        return wb;
    }

    //判断文件是否是excel
    public static void checkExcelVaild(MultipartFile file) throws Exception{
        if(file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)){
            throw new Exception("文件不是Excel");
        }
    }

    // 解析cellType
    public static String parseCell(Cell cell){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        int cellType = cell.getCellType();
        String cellValue = "";
        switch (cellType) {
            case Cell.CELL_TYPE_STRING:     // 文本
                cellValue = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:    // 数字、日期
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = fmt.format(cell.getDateCellValue());
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:    // 布尔型
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK: // 空白
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_ERROR: // 错误
                cellValue = "错误";
                break;
            case Cell.CELL_TYPE_FORMULA:    // 公式
                // 得到对应单元格的公式
                //cellValue = cell.getCellFormula() + "#";
                // 得到对应单元格的字符串
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }

    // 获取字段列表（默认第一行为字段名称）
    public static List<String> exportTitleFromExcel(Sheet sheet){
        List<String> resultList = new ArrayList<>();
        Row row = sheet.getRow(0);
        // 如果当前行没有数据，跳出循环
        if(row.getCell(0).toString().equals("")){
            return resultList;
        }
        String rowValue = "";
        for (Cell cell : row) {
            if(cell.toString() == null){
                continue;
            }
            String cellValue = parseCell(cell);
            resultList.add(cellValue);
        }
        return resultList;
    }

    // 获取内容列表
    public static List<List<String>> exportContentFromExcel(Sheet sheet, List<String> fieldNameList, InsertBatchParam[] insertBatchParams){
        int columnCount = fieldNameList.size();
        List<InsertBatchParam> insertBatchParamList = new ArrayList<>();
        List<List<String>> resultList = new ArrayList<>();
        // 为跳过第一行目录设置count
        int rowCount = 0;
        for (Row row : sheet) {
            List<String> rowList = new ArrayList<>();
            if(rowCount == 0){ // 跳过第一行的目录
                rowCount++;
                continue;
            }
            if(row != null && !isRowEmpty(row, columnCount)){ // 当前行是空行，跳过
                for (int currentColumnCount = 0; currentColumnCount < columnCount; currentColumnCount++) {
                    Cell cell = row.getCell(currentColumnCount);
                    if(cell == null || cell.toString() == null){
                        rowList.add(null);
                        continue;
                    }
                    // 判断已经保存了校验信息
                    if(insertBatchParamList.size() < currentColumnCount + 1){
                        for(InsertBatchParam insertBatchParam : insertBatchParams){
                            if(insertBatchParam.fieldName().equals(fieldNameList.get(currentColumnCount))){
                                insertBatchParamList.add(insertBatchParam);
                                break;
                            }
                        }
                    }
                    // 获取单元格内的数据
                    String cellValue = parseCell(cell);
                    // 校验数据
                    boolean isValid = false;
                    InsertBatchParam insertBatchParam = insertBatchParamList.get(currentColumnCount);
                    if(insertBatchParam.value().methodName.equals("isEnum")){ // 有枚举映射关系
                        try {
                            String enumValue = (String) insertBatchParam.value().fun.apply(cellValue, insertBatchParam.express());
                            if(!StringUtils.isEmpty(enumValue)){
                                cellValue = enumValue;
                                isValid = true;
                            }
                            if(!isValid){
                                throw new InsertBatchException("发现错误行：" + ++rowCount + "，错误列：" + ++currentColumnCount);
                            }
                        } catch (Exception e) {
                            throw new InsertBatchException("发现错误行：" + ++rowCount + "，错误列：" + ++currentColumnCount);
                        }
                    } else{
                        isValid = (Boolean) insertBatchParam.value().fun.apply(cellValue, insertBatchParam.express());
                    }
                    if(!isValid){
                        throw new InsertBatchException("发现错误行：" + ++rowCount + "，错误列：" + (++currentColumnCount));
                    }
                    rowList.add(cellValue);
                }
                resultList.add(rowList);
            }
        }
        return resultList;
    }

    // 导出标题列表
    public static List<String> exportTitleListFromExcel(MultipartFile excelFile) throws InsertBatchException {
        try {
            // 同时支持Excel 2003、2007
            checkExcelVaild(excelFile);
            Workbook workbook = getWorkbok(excelFile);
            //Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel2003/2007/2010都是可以处理的
            int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
            //设置当前excel中sheet的下标：0开始
            Sheet sheet = workbook.getSheetAt(0);   // 遍历第一个Sheet
            return exportTitleFromExcel(sheet);
        } catch (Exception e) {
            if(e instanceof InsertBatchException){
                throw (InsertBatchException) e;
            }
            log.error("发生异常", e);
            throw new InsertBatchException("excel文件解析失败");
        }
    }

    // 导出内容列表
    public static List<List<String>> exportContentListFromExcel(MultipartFile excelFile, List<String> fieldNameList, InsertBatchParam[] insertBatchParams) {
        try {
            // 同时支持Excel 2003、2007
            checkExcelVaild(excelFile);
            Workbook workbook = getWorkbok(excelFile);
            //Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel2003/2007/2010都是可以处理的
            int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
            //设置当前excel中sheet的下标：0开始
            Sheet sheet = workbook.getSheetAt(0);   // 遍历第一个Sheet
            return exportContentFromExcel(sheet, fieldNameList, insertBatchParams);
        } catch (Exception e) {
            if(e instanceof  InsertBatchException){
                throw (InsertBatchException) e;
            }
            log.error("发生异常：", e);
            throw new InsertBatchException("excel文件解析失败");
        }
    }

    /**
     * 判断是否是空行
     * @param row
     * @return
     */
    public static boolean isRowEmpty(Row row, Integer columnCount) {
        if(columnCount == null){
            for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
                Cell cell = row.getCell(c);
                if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                    return false;
            }
        } else{
            for (int c = 0; c < columnCount; c++) {
                Cell cell = row.getCell(c);
                if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                    return false;
            }
        }

        return true;
    }

    /**
     * 获取字段名称
     */
    public static List<String> getFieldNameList(List<String> fieldNameZhList, InsertBatchParam[] insertBatchParams){
        List<String> fieldNameList = new ArrayList<>();
        Iterator<String> fieldNameZhIterator = fieldNameZhList.iterator();
        while(fieldNameZhIterator.hasNext()){
            String fieldNameZh = fieldNameZhIterator.next();
            boolean exist = false;
            for(InsertBatchParam insertBatchParam : insertBatchParams){
                if(fieldNameZh == null || StringUtils.isEmpty(fieldNameZh)){
                    if(!fieldNameZhIterator.hasNext()){ // 最后一个元素是空值，跳过
                        exist = true;
                        break;
                    } else{
                        throw new InsertBatchException("字段名不能为空，必须连续，中间不能有空值");
                    }
                } else if(insertBatchParam.fieldNameZh().equals(fieldNameZh)){
                    fieldNameList.add(insertBatchParam.fieldName());
                    exist = true;
                    break;
                }
            }
            if(!exist){
                throw new InsertBatchException("存在不合法的字段：" + fieldNameZh);
            }
        }
        return fieldNameList;
    }
}
