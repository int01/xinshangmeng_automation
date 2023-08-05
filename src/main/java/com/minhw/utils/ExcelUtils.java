package com.minhw.utils;


import com.minhw.po.SSVO;
import io.netty.util.internal.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minhongwei
 * @date 2022/12/3 22:13
 * @Description: TODO
 */
public class ExcelUtils {

    public static Map<String, String> readExcel(String path, int sheetIndex) {

        Map<String, String> map = new HashMap<>();
        try {
            // 获取文件输入流
            InputStream inputStream = new FileInputStream(path);
            // 定义一个org.apache.poi.ss.usermodel.Workbook的变量
            Workbook workbook = null;
            // 截取路径名 . 后面的后缀名，判断是xls还是xlsx。 魔法值！！！
            if (path.substring(path.lastIndexOf(".") + 1).equals("xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else if (path.substring(path.lastIndexOf(".") + 1).equals("xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            }

            Sheet sheet = workbook.getSheetAt(sheetIndex);
            // sheet.getPhysicalNumberOfRows()获取总的行数
            // 循环读取每一行
            for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                // 循环读取每一个格
                Row row = sheet.getRow(i);
                // 第二个框为中文名称
                Cell name = row.getCell(1);
                name.setCellType(CellType.STRING);
                // 最后一个值为订单数据,如果为空则不处理。请将你的档位订购量放在最后一行
                /** 如：
                 *  0198	云烟(软大重九) 1000	3	2	2
                 *  1101	中华(硬)	    450	    1	1	1	1	1
                 *  保留 "品牌编码 品牌名称 档位订货量"， 档位订货量放最后一个位置，档位订货量为空则不处理
                 *  如品牌编码0198是当前档位无需订购的量，程序不会处理
                 *  ！！无需保留标题拦，只需要保留数据即可
                 */
                Cell value = row.getCell(row.getPhysicalNumberOfCells() - 1);
                value.setCellType(CellType.STRING);

                if (!StringUtil.isNullOrEmpty(value.getStringCellValue())) {
                    map.put(name.getStringCellValue().trim()
                                    .replace('（', '(')
                                    .replace('）', ')'),
                            value.getStringCellValue());
                }

                // row.getPhysicalNumberOfCells()获取总的列数
//                for (int index = 0; index < row.getPhysicalNumberOfCells(); index++) {
//                    // 获取数据，但是我们获取的cell类型
//                    Cell cell = row.getCell(index);
//
//                    // 转换为字符串类型
//                    cell.setCellType(CellType.STRING);
//                    // 获取得到字符串
//                    String id = cell.getStringCellValue();
//                    System.out.println(id);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static List<SSVO> readExcel1(String path, int sheetIndex) {
        List<SSVO> result = new ArrayList<>();
        try {
            // 获取文件输入流
            InputStream inputStream = new FileInputStream(path);
            // 定义一个org.apache.poi.ss.usermodel.Workbook的变量
            Workbook workbook = null;
            // 截取路径名 . 后面的后缀名，判断是xls还是xlsx。 魔法值！！！
            if (path.substring(path.lastIndexOf(".") + 1).equals("xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else if (path.substring(path.lastIndexOf(".") + 1).equals("xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            }
            String neetNames = "销方识别号,商品类型,金额,税额";
            Map<String, Integer> neetIndex = new HashMap();
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            // sheet.getPhysicalNumberOfRows()获取总的行数
            // 循环读取每一行
            for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                // 循环读取每一个格
                Row row = sheet.getRow(i);
                // System.out.println(row.getLastCellNum());
                // row.getPhysicalNumberOfCells(); // 获取有值的列数
                if (i == 0) {
                    for (int index = 0; index < row.getLastCellNum(); index++) {
                        // 获取数据，但是我们获取的cell类型
                        Cell cell = row.getCell(index);
                        if (cell == null) {
                            continue;
                        }
                        // 转换为字符串类型
                        cell.setCellType(CellType.STRING);
                        // 获取得到字符串
                        String id = cell.getStringCellValue();
                        if (neetNames.contains(id)) {
                            neetIndex.put(id, index);
                        }
                    }
                    continue;
                }
                SSVO ssvo = new SSVO();
                neetIndex.forEach((k, v) -> {
                    Cell cell = row.getCell(v);
                    if (cell == null) {
                        return;
                    }
                    // System.out.println(cell.getCellType());
                    switch (k) {
                        case "销方识别号":
                            cell.setCellType(CellType.STRING);
                            ssvo.setHeadingCode(cell.getStringCellValue());
                            break;
                        case "商品类型":
                            cell.setCellType(CellType.STRING);
                            ssvo.setGoodsType(cell.getStringCellValue());
                            break;
                        case "金额":
                            cell.setCellType(CellType.NUMERIC);
                            ssvo.setAmount(cell.getNumericCellValue());
                            break;
                        case "税额":
                            cell.setCellType(CellType.NUMERIC);
                            ssvo.setTax(cell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                    // System.out.printf("%s:%s%n", k, id);
                });
                result.add(ssvo);
                // System.out.println("=====================================");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
