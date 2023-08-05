import com.minhw.utils.ExcelUtils;
import org.junit.Test;

import java.util.Map;

/**
 * @Author mi
 * @DateTime 2022/12/30 11:54 星期五
 * @Description: TODO
 */
public class ExcelTest {

    /**
     * excel文件的路径
     */
    String path = "/Users/minhongwei/Desktop/2023第二期公示.xlsx";

    @Test
    public void getExcel() {
        Map<String, String> excel = ExcelUtils.readExcel(path, 1);
        for (Map.Entry<String, String> entry : excel.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
