import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @Author minhongwei
 * @DateTime 2023/5/23 13:37 星期二
 * @Description: TODO
 */
public class JedisTest {
    @Test
    public void testRedis(){
        //1.获取连接
        Jedis jedis = new Jedis("127.0.0.1",6379);

        //2.执行具体操作
        jedis.set("username","xiaoming");

        //关闭连接
        jedis.close();
    }
}
