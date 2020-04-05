package top.andnux.wallet;

import com.google.gson.Gson;

import org.junit.Test;

import java.util.ServiceLoader;

import top.andnux.wallet.base.WalletAccount;
import top.andnux.wallet.base.services.WalletService;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception{
        ServiceLoader<WalletService> services = ServiceLoader.load(WalletService.class);
        for (WalletService service : services) {
            System.out.println(service.chainName());
            WalletAccount account = service.createAccount();
            String json = new Gson().toJson(account);
            System.out.println(json);
        }
    }
}