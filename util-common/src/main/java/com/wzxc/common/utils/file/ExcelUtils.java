package com.wzxc.common.utils.file;

import com.google.common.base.CaseFormat;
import com.wzxc.common.annotation.InsertBatchParam;
import com.wzxc.common.constant.ValidateConstants;
import com.wzxc.common.core.domain.MyEntry;
import com.wzxc.common.exception.InsertBatchException;
import com.wzxc.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public static Object parseCell(Cell cell){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        int cellType = cell.getCellType();
        Object cellValue;
        switch (cellType) {
            case Cell.CELL_TYPE_STRING:     // 文本
                cellValue = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:    // 数字、日期
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue();
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
            Object cellValue = parseCell(cell);
            resultList.add(cellValue.toString());
        }
        return resultList;
    }

    /**
     * 获取批导入数据
     * @param rows
     * @param fieldNameList
     * @param insertBatchParams
     * @return
     */
    public static List<List<Object>> exportContentFromData(List<List<String>> rows, List<String> fieldNameList, InsertBatchParam[] insertBatchParams){
        int columnCount = fieldNameList.size();
        List<InsertBatchParam> insertBatchParamList = new ArrayList<>();
        List<List<Object>> resultList = new ArrayList<>();
        for(int rowCount = 0; rowCount < rows.size(); rowCount++){
            List<String> row = rows.get(rowCount);
            List<Object> rowList = new ArrayList<>();
            for (int currentColumnCount = 0; currentColumnCount < columnCount; currentColumnCount++) {
                Object cellValue = row.get(currentColumnCount);
                // 判断已经保存了校验信息
                if(insertBatchParamList.size() < currentColumnCount + 1){
                    for(InsertBatchParam insertBatchParam : insertBatchParams){
                        if(insertBatchParam.fieldName().equals(fieldNameList.get(currentColumnCount))){
                            insertBatchParamList.add(insertBatchParam);
                            break;
                        }
                    }
                }
                InsertBatchParam insertBatchParam = insertBatchParamList.get(currentColumnCount);
                cellValue = handleCellValue(insertBatchParam, cellValue, rowCount, currentColumnCount);
                rowList.add(cellValue);
            }
            resultList.add(rowList);
        }
        return resultList;
    }

    /**
     * 通过excel的sheet 获取批导入数据
     *
     * @param sheet
     * @param fieldNameList
     * @param insertBatchParams
     * @return
     */
    public static List<Map<String, Object>> exportContentFromExcel(Sheet sheet, List<MyEntry<String, Integer>> fieldNameList, InsertBatchParam[] insertBatchParams){
        List<InsertBatchParam> insertBatchParamList = new ArrayList<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        // 保存校验信息
        for(int i = 0; i < fieldNameList.size(); i++){
            for(InsertBatchParam insertBatchParam : insertBatchParams){
                if(insertBatchParam.fieldName().equals(fieldNameList.get(i).getKey())){
                    insertBatchParamList.add(insertBatchParam);
                    break;
                }
            }
        }
        // 为跳过第一行目录设置count
        int rowCount = 0;
        for (Row row : sheet) {
            rowCount++;
            Map<String, Object> rowMap = new HashMap<>();
            if(rowCount == 1){ // 跳过第一行的目录
                continue;
            }
            if(row != null && !isRowEmpty(row, fieldNameList)){ // 当前行是空行，跳过
                int ccount = -1;
                for (MyEntry<String, Integer> entry : fieldNameList) {
                    ccount++;
                    Cell cell = row.getCell(entry.getValue());
                    if(cell == null || cell.toString() == null){
                        rowMap.put(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, entry.getKey()), null);
                        continue;
                    }
                    // 获取单元格内的数据
                    Object cellValue = parseCell(cell);
                    InsertBatchParam insertBatchParam = insertBatchParamList.get(ccount);
                    cellValue = handleCellValue(insertBatchParam, cellValue, rowCount, entry.getValue());
                    rowMap.put(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, entry.getKey()), cellValue);
                }
                resultList.add(rowMap);
            }
        }
        return resultList;
    }

    private static Object handleCellValue(InsertBatchParam insertBatchParam, Object cellValue, int rowCount, int columnCount){
        boolean isValid = false;
        // 进行合法性校验
        try {
            if(insertBatchParam.value().methodName.equals("isEnum")){ // 有枚举映射关系
                Object enumValue = insertBatchParam.value().fun.apply(cellValue, insertBatchParam.express());
                cellValue = enumValue;
                isValid = true;
            } else{
                isValid = (Boolean) insertBatchParam.value().fun.apply(cellValue, insertBatchParam.express());
            }
        } catch (Exception e) {
            throw new InsertBatchException("发现错误行：" + ++rowCount + "，错误列：" + ++columnCount);
        }
        if(!isValid){
            throw new InsertBatchException("发现错误行：" + ++rowCount + "，字段名称：" + insertBatchParam.fieldName() + "，" + insertBatchParam.value().msg);
        }
        // 过滤器处理
        cellValue = insertBatchParam.filter().fun.apply(cellValue, insertBatchParam.express());
        if(cellValue == null || (cellValue instanceof String && StringUtils.isEmpty(cellValue.toString())) || cellValue.equals(ValidateConstants.FILTER_FAIL)){
            cellValue = null;
        }
        return cellValue;
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
    public static List<Map<String, Object>> exportContentListFromExcel(MultipartFile excelFile, List<MyEntry<String, Integer>> fieldNameList, InsertBatchParam[] insertBatchParams) {
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
            if(e instanceof InsertBatchException){
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
    public static boolean isRowEmpty(Row row, List<MyEntry<String, Integer>> fieldNameList) {
        if(fieldNameList == null || fieldNameList.size() == 0){
            for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
                Cell cell = row.getCell(c);
                if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                    return false;
            }
        } else{
            for (MyEntry<String, Integer> entry : fieldNameList) {
                Cell cell = row.getCell(entry.getValue());
                if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                    return false;
            }
        }

        return true;
    }

    /**
     * 获取字段名称
     */
    public static List<MyEntry<String, Integer>> getFieldNameList(List<String> fieldNameZhList, InsertBatchParam[] insertBatchParams){
        List<MyEntry<String, Integer>> fieldNameList = new ArrayList<>();
        Iterator<String> fieldNameZhIterator = fieldNameZhList.iterator();
        int count = 0;
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
                    fieldNameList.add(new MyEntry<>(insertBatchParam.fieldName(), count));
                    exist = true;
                    break;
                }
            }
            count++;
//            if(!exist){
//                throw new InsertBatchException("存在不合法的字段：" + fieldNameZh);
//            }
        }
        return fieldNameList;
    }

    public static XSSFWorkbook exportEXCELFile(List<List<Object>> dataList, String filename) {
        XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet;
        try {
            sheet = workbook.createSheet(new String(filename.getBytes(), "UTF-8"));
			XSSFRow row = null;
            for(int i = 0; i < dataList.size(); i++){
                row = sheet.createRow(i);
                List<Object> data = dataList.get(i);
                for(int j = 0; j < data.size(); j++){
                    row.createCell(j).setCellValue(data.get(j) == null ? null : new String(data.get(j).toString().getBytes(), "UTF-8"));
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return workbook;
    }
}
