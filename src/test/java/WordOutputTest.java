import com.minhw.po.SSVO;
import com.minhw.utils.ExcelUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author minhongwei
 * @DateTime 2023/5/13 16:07 星期六
 * @Description: TODO
 */
public class WordOutputTest {


    private static final String FILE_PATH = "/Users/minhongwei/Desktop/";

    // @Test
    // public void test04() {
    //     List<SSVO> list = ExcelUtils.readExcel1(FILE_PATH + "老板要求人才类型试水/输入模板.xls", 0);
    //     // System.out.println(list);
    //     list.forEach(System.out::println);
    //     list.sort((o1, o2) -> o2.getAmount().compareTo(o1.getAmount()));
    //     System.out.println("排序后");
    //     list.forEach(System.out::println);
    //     boolean b = false;
    //     double bs = 0.0;
    //     if (list.size() >= 2) {
    //         // 第一名的交易额是第二名的1.5倍及以下  这里简单操作，就不使用大数操作
    //         bs = list.get(0).getAmount() / list.get(1).getAmount();
    //         b = bs <= 1.5;
    //     }
    //     System.err.printf("表格中第一名的交易额是第二名的1.5倍及以下？ %s %f %n", b, bs);
    //     // TODO  将数据输出到word 模版
    //     outWordFile(list);
    // }


    @Test
    public void test() {
        // 创建新的Word文档
        XWPFDocument document = new XWPFDocument();

        // 创建段落
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        // 设置文本内容
        run.setText("Hello, World!");

        // 保存文档到指定的文件路径
        String filePath = FILE_PATH + "output.docx";
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            document.write(outputStream);
            System.out.println("Word文档已成功输出！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // @Test
    public void outWordFile(List<SSVO> list) {
        // 创建新的Word文档
        XWPFDocument document = new XWPFDocument();

        // 创建表格
        XWPFTable table = document.createTable();

        // 添加表头行
        XWPFTableRow headerRow = table.getRow(0);
        headerRow.getCell(0).setText("序号");
        headerRow.addNewTableCell().setText("公司识别码");
        headerRow.addNewTableCell().setText("商品类型");
        headerRow.addNewTableCell().setText("交易额");
        headerRow.addNewTableCell().setText("税额");
        AtomicInteger i = new AtomicInteger(1);
        list.forEach(ssvo -> {
            // 添加数据行
            XWPFTableRow dataRow = table.createRow();
            dataRow.getCell(0).setText(i.getAndIncrement() + "");
            dataRow.getCell(1).setText(ssvo.getHeadingCode());
            dataRow.getCell(2).setText(ssvo.getGoodsType());
            dataRow.getCell(3).setText(ssvo.getAmount().toString());
            dataRow.getCell(4).setText(ssvo.getTax().toString());
        });
        // // 添加数据行
        // XWPFTableRow dataRow = table.createRow();
        // dataRow.getCell(0).setText("1");
        // dataRow.getCell(1).setText("111");
        // dataRow.getCell(2).setText("11");
        // dataRow.getCell(2).setText("11");

        // 保存文档到指定的文件路径
        String filePath = FILE_PATH + "output.docx";
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            document.write(outputStream);
            System.out.println("Word文档已成功输出！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test03() {
        try {
            // 加载模板文档
            FileInputStream templateFile = new FileInputStream(FILE_PATH + "template.docx");
            XWPFDocument doc = new XWPFDocument(templateFile);

            // 获取文档中的所有段落
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                // 替换段落中的文本
                replaceText(paragraph, "placeholder", "replacement");
            }

            // 获取文档中的所有表格
            for (XWPFTable table : doc.getTables()) {
                // 遍历表格中的所有行
                for (XWPFTableRow row : table.getRows()) {
                    // 遍历行中的所有单元格
                    for (XWPFTableCell cell : row.getTableCells()) {
                        // 替换单元格中的文本
                        replaceText(cell, "placeholder", "replacement");
                    }
                }
            }

            // 保存生成的文档
            FileOutputStream outputFile = new FileOutputStream(FILE_PATH + "output.docx");
            doc.write(outputFile);

            // 关闭流
            templateFile.close();
            outputFile.close();

            System.out.println("Word文档生成成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 替换文本的辅助方法
    private static void replaceText(XWPFParagraph paragraph, String target, String replacement) {
        List<XWPFRun> runs = paragraph.getRuns();
        for (XWPFRun run : runs) {
            String text = run.getText(0);
            if (text != null && text.contains(target)) {
                text = text.replace(target, replacement);
                run.setText(text, 0);
            }
        }
    }

    // 替换单元格文本的辅助方法
    private static void replaceText(XWPFTableCell cell, String target, String replacement) {
        for (XWPFParagraph paragraph : cell.getParagraphs()) {
            replaceText(paragraph, target, replacement);
        }
    }
}
