package com.minhw;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * @Author minhongwei
 * @DateTime 2023/7/18 19:15 星期二
 * @Description: TODO
 */

public class DocGenerator {
    public static void main(String[] args) {
        try {
            // 1. 读取doc模板文件
            FileInputStream templateFile = new FileInputStream("/Users/minhongwei/Desktop/高等学校毕业生档案转递单.doc");
            POIFSFileSystem fileSystem = new POIFSFileSystem(templateFile);
            HWPFDocument doc = new HWPFDocument(fileSystem);

            // 2. 获取doc模板中的所有段落
            Range range = doc.getRange();
            for (int i = 0; i < range.numParagraphs(); i++) {
                Paragraph paragraph = range.getParagraph(i);
                // 3. 处理段落中的占位符并替换为实际内容
                System.out.println(paragraph.text());
                // String content = processPlaceholder(paragraph.text());
                // System.out.println();
                // replaceParagraphContent(paragraph, content);
            }

            // 4. 处理doc模板中的表格
            // for (int i = 0; i < range.numParagraphs(); i++) {
            //     Table table = range.getTable(i);
            //     for (int rowIdx = 0; rowIdx < table.numRows(); rowIdx++) {
            //         TableRow row = table.getRow(rowIdx);
            //         for (int cellIdx = 0; cellIdx < row.numCells(); cellIdx++) {
            //             TableCell cell = row.getCell(cellIdx);
            //             // 5. 处理表格中的占位符并替换为实际内容
            //             String content = processPlaceholder(cell.getParagraph(0).text());
            //             replaceTableCellContent(cell, content);
            //         }
            //     }
            // }

            // 6. 保存生成的文档
            FileOutputStream outputStream = new FileOutputStream("/Users/minhongwei/Desktop/generated_doc.doc");
            doc.write(outputStream);
            outputStream.close();

            // 7. 关闭资源
            doc.close();

            System.out.println("文档生成完成。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 模拟处理占位符的方法，根据实际需求自行修改
    private static String processPlaceholder(String placeholder) {
        // 在实际应用中，您可以根据具体需求对占位符进行解析和替换
        // 这里只是简单示例，返回固定内容
        if (placeholder.equals("${NAME}")) {
            return "John Doe";
        } else if (placeholder.equals("${AGE}")) {
            return "30";
        } else {
            return placeholder; // 如果找不到匹配的占位符，保持原样返回
        }
    }

    // 替换段落内容的方法
    private static void replaceParagraphContent(Paragraph paragraph, String content) {
        int numRuns = paragraph.numCharacterRuns();
        for (int i = numRuns - 1; i >= 0; i--) {
            // paragraph.delete(i);
        }
        paragraph.insertAfter(content);
    }

    // 替换表格单元格内容的方法
    private static void replaceTableCellContent(TableCell cell, String content) {
        int numParagraphs = cell.numParagraphs();
        for (int i = numParagraphs - 1; i >= 0; i--) {
            // cell.removeParagraph(i);
        }
        cell.insertAfter(content);
    }
}

