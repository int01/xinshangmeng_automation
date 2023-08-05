import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.After;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author minhongwei
 * @DateTime 2023/7/18 19:49 星期二
 * @Description: TODO
 */
public class CapOutPutEMS {

    String filePath = "/Users/minhongwei/Desktop/模版样式.docx";

    String targetPath = "/Users/minhongwei/Desktop/";

    InputStream is;
    XWPFDocument doc;
    Map<String, Object> params = new HashMap<String, Object>();

    {
        params.put("${name}", "xxx");
        params.put("${sex}", "男");
        params.put("${ems}", "共青团员");
        params.put("${place}", "sssss");
        params.put("${classes}", "3102");
        params.put("${sfz}", "213123123");
        /**
         * params 读取Excel表填入参数并放入list中，然后遍历list
         */
        try {
            is = new FileInputStream(filePath);
            doc = new XWPFDocument(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用一个docx文档作为模板，然后替换其中的内容，再写入目标文档中。
     *
     * @throws Exception
     */
    @Test
    public void testTemplateWrite() throws Exception {
        System.out.println(doc.getParagraphsIterator());
        // 替换段落里面的变量
        this.replaceInPara(doc, params);
        // // 替换表格里面的变量
        this.replaceInTable(doc, params);
        OutputStream os = new FileOutputStream(targetPath + "test.doc");
        doc.write(os);

        close(os);
    }


    /**
     * 替换段落里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    public void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        // 获取文档中的段落
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            this.replaceInPara(para, params);
        }
    }

    /**
     * 替换段落里面的变量
     *
     * @param para   要替换的段落
     * @param params 参数
     */
    private void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (this.matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            boolean errFLag = false;
            StringBuilder sb = new StringBuilder();
            for (XWPFRun run : runs) {
                // System.out.println(run.toString());
                String runText = run.toString();
                if (matcher(runText).find()) {
                    run.setText((String) params.get(runText), 0);
                } else if ('$' == runText.charAt(0) && '{' == runText.charAt(1)) {
                    errFLag = true;
                    sb.append(runText);
                    run.setText("", 0);
                } else {
                    if (errFLag) {
                        if ('}' == runText.charAt(0)) {
                            sb.append(runText);
                            System.out.println(sb.toString());
                            run.setText((String) params.get(sb.toString()), 0);
                            errFLag = false;
                            sb = new StringBuilder();
                            continue;
                        }
                        run.setText("", 0);
                        sb.append(runText);
                    }
                    // System.out.printf("errorCode %d.\n",  errFLag);
                }
            }
        }
    }

    /**
     * 替换表格里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    public void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        this.replaceInPara(para, params);
                    }
                }
            }
        }
    }

    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(str);
    }

    /**
     * 关闭输入流
     *
     * @param is
     */
    public void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     *
     * @param os
     */
    public void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @After
    public void after() {
        this.close(is);
    }

}
