package cn.acyou.iblog.utility;


import cn.acyou.iblog.constant.AppConstant;
import cn.acyou.iblog.exception.BussinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * 导出Excel文档工具类
 * @author 那位先生
 * @date 2014-8-6
 * */
public class ExcelUtil {
    protected static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 创建excel文档，
     * @param list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * */
    public static Workbook createWorkBook(List<Map<String, Object>> list, String[] keys, String columnNames[]) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < keys.length; i++) {
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

//        Font f3=wb.createFont();
//        f3.setFontHeightInPoints((short) 10);
//        f3.setColor(IndexedColors.RED.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        if (list.size() > 65535) {
            throw new BussinessException("导出数据超过最大限制:65535,请减少数据后再尝试!");
        }
        //设置每行每列的值
        for (int i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow(i);
            // 在row行上创建一个方格
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                Object cellValue = list.get(i).get(keys[j]);
                if (cellValue != null && StringUtils.isNotBlank(cellValue.toString()) && NumberUtils.isNumber(cellValue.toString())) {
                    try {
                        //对于货位零件号这种含有前导零整数做字符串处理保留前导零
                        if (!MathUtil.isLeadingZeroInteger(cellValue.toString())) {
                            cell.setCellValue(Double.parseDouble(cellValue.toString()));
                        } else {
                            cell.setCellValue(cellValue.toString());
                        }
                    } catch (Exception e) {
                        cell.setCellValue(cellValue.toString());
                    }
                } else {
                    cell.setCellValue(cellValue == null ? " " : cellValue.toString());
                }
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

    /**
     * @param list        数据列表
     * @param keys        ist中map的key数组集合
     * @param columnNames excel的列名
     * @param fileName    导出Excel名
     * @param response    http请求的response,用于写数据到response
     */
    public static void writeExcelBytesToResponse(List<Map<String, Object>> list, String[] keys, String columnNames[], String fileName, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
        writeIO(response, os, fileName);
    }


    /**
     * @param list           数据列表
     * @param keysList       ist中map的key数组集合
     * @param columnNameList excel的列名
     * @param fileName       导出Excel名
     * @param response       http请求的response,用于写数据到response
     * @param number         合并单元格的列数
     */
    public static void writeExcelBytesToResponseNew(List<Map<String, Object>> list, List<String> keysList, List<String> columnNameList, String isShowUsing,
                                                    String fileName, int number, HttpServletResponse response, int type) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ExcelUtil.createWorkBookNew(list, keysList, columnNameList, isShowUsing, number, type).write(os);
        writeIO(response, os, fileName);
    }


    private static Workbook createWorkBookNew(List<Map<String, Object>> list, List<String> keysList,
                                              List<String> columnNameList, String isShowUsing, int number, int type) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < columnNameList.size(); i++) {
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }
        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();//数字

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

//        Font f3=wb.createFont();
//        f3.setFontHeightInPoints((short) 10);
//        f3.setColor(IndexedColors.RED.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);
        cs.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        // 设置第二种单元格的样式（用于数字值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        DataFormat df = wb.createDataFormat();
        cs2.setDataFormat(df.getFormat("#,#0"));


        // 创建第一行,第二行
        Row row = sheet.createRow((short) 0);
        Row row2 = sheet.createRow((short) 1);

        for (int r = 0; r < columnNameList.size(); r++) {
            if ("1".equals(isShowUsing)) {
                if (type == 0) {
                    if (r < columnNameList.size() - 2 * number) {
                        CellRangeAddress cra = new CellRangeAddress(0, 1, r, r);
                        //在sheet里增加合并单元格
                        sheet.addMergedRegion(cra);
                    } else if (r == columnNameList.size() - 2 * number) {
                        CellRangeAddress cra2 = new CellRangeAddress(0, 0, columnNameList.size() - 2 * number, columnNameList.size() - number - 1);
                        //在sheet里增加合并单元格
                        sheet.addMergedRegion(cra2);
                        Cell cellR2 = row.createCell(columnNameList.size() - 2 * number);
                        cellR2.setCellValue(list.get(0).get("totalNumber").toString() == null ? " " : list.get(0).get("totalNumber").toString());
                        cellR2.setCellStyle(cs);

                        CellRangeAddress cra = new CellRangeAddress(0, 0, columnNameList.size() - number, columnNameList.size() - 1);
                        //在sheet里增加合并单元格
                        sheet.addMergedRegion(cra);
                        Cell cellR = row.createCell(columnNameList.size() - number);
                        cellR.setCellValue(list.get(0).get("usingNumber").toString() == null ? " " : list.get(0).get("usingNumber").toString());
                        cellR.setCellStyle(cs);
                    }
                } else {
                    if (r < columnNameList.size() - number - 1) {
                        CellRangeAddress cra = new CellRangeAddress(0, 1, r, r);
                        //在sheet里增加合并单元格
                        sheet.addMergedRegion(cra);
                    } else if (r == columnNameList.size() - number - 1) {
                        CellRangeAddress cra2 = new CellRangeAddress(0, 0, columnNameList.size() - number - 1, columnNameList.size() - 2);
                        //在sheet里增加合并单元格
                        sheet.addMergedRegion(cra2);
                        Cell cellR2 = row.createCell(columnNameList.size() - number - 1);
                        cellR2.setCellValue(list.get(0).get("totalNumber").toString() == null ? " " : list.get(0).get("totalNumber").toString());
                        cellR2.setCellStyle(cs);

                        Cell cellR = row.createCell(columnNameList.size() - 1);
                        cellR.setCellValue(list.get(0).get("usingNumber").toString() == null ? " " : list.get(0).get("usingNumber").toString());
                        cellR.setCellStyle(cs);
                    }
                }
            } else {
                if (r < columnNameList.size() - number) {
                    CellRangeAddress cra = new CellRangeAddress(0, 1, r, r);
                    //在sheet里增加合并单元格
                    sheet.addMergedRegion(cra);
                } else if (r == columnNameList.size() - number) {
                    CellRangeAddress cra = new CellRangeAddress(0, 0, columnNameList.size() - number, columnNameList.size() - 1);
                    //在sheet里增加合并单元格
                    sheet.addMergedRegion(cra);
                    Cell cellR = row.createCell(columnNameList.size() - number);
                    cellR.setCellValue(list.get(0).get("totalNumber") == null ? " " : list.get(0).get("totalNumber").toString());
                    cellR.setCellStyle(cs);
                }
            }
        }

        //设置列名
        for (int i = 0; i < columnNameList.size(); i++) {
            if ("1".equals(isShowUsing)) {
                if (type == 0) {
                    if (i < columnNameList.size() - 2 * number) {
                        Cell cell = row.createCell(i);
                        cell.setCellValue(columnNameList.get(i));
                        cell.setCellStyle(cs);
                    } else {
                        Cell cellR = row2.createCell(i);
                        cellR.setCellValue(columnNameList.get(i) == null ? " " : columnNameList.get(i));
                        cellR.setCellStyle(cs);
                    }
                } else {
                    if (i < columnNameList.size() - number - 1) {
                        Cell cell = row.createCell(i);
                        cell.setCellValue(columnNameList.get(i));
                        cell.setCellStyle(cs);
                    } else {
                        Cell cellR = row2.createCell(i);
                        cellR.setCellValue(columnNameList.get(i) == null ? " " : columnNameList.get(i));
                        cellR.setCellStyle(cs);
                    }
                }
            } else {
                if (i < columnNameList.size() - number) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(columnNameList.get(i));
                    cell.setCellStyle(cs);
                } else {
                    Cell cellR = row2.createCell(i);
                    cellR.setCellValue(columnNameList.get(i) == null ? " " : columnNameList.get(i));
                    cellR.setCellStyle(cs);
                }
            }
        }
        //设置每行每列的值
        for (short i = 2; i < list.size() + 1; i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i);
            // 在row行上创建一个方格
            for (short j = 0; j < keysList.size(); j++) {
                Cell cell = row1.createCell(j);
                String value = list.get(i - 1).get(keysList.get(j)) == null ? " " : list.get(i - 1).get(keysList.get(j)).toString();
                cell.setCellValue(value);
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

    public static void writeIO(HttpServletResponse response, ByteArrayOutputStream os, String fileName) throws IOException {
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[10240];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
    }
    public static HSSFCellStyle getContentStyle (HSSFWorkbook workbook) {
        HSSFCellStyle contentStyle = workbook.createCellStyle(); //设置内容行格式
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        HSSFDataFormat df = workbook.createDataFormat();  //此处设置数据格式
        contentStyle.setDataFormat(df.getFormat("#,#0")); //数据格式只显示整数，如果是小数点后保留两位，可以写contentStyle.setDataFormat(df.getFormat("#,#0.00"));
        return contentStyle;
    }
}