package top.andnux.wallet.eos.services

import androidx.annotation.Keep
import com.google.auto.service.AutoService
import io.github.novacrypto.bip39.Words
import top.andnux.wallet.base.WalletAccount
import top.andnux.wallet.base.exception.NotSupportedException
import top.andnux.wallet.base.services.WalletService
import top.andnux.wallet.base.utils.ChainUtil
import top.andnux.wallet.bos.blockchain.cypto.ec.BosPrivateKey

@Keep
@AutoService(WalletService::class)
class BosWalletService : WalletService {
    override fun chainName(): String {
        return "BOS"
    }

    @Throws(Exception::class)
    override fun createAccount(): WalletAccount {
        val mnemonic = ChainUtil.genMnemonic(Words.TWELVE)
        val privateKey = BosPrivateKey(ChainUtil.genECKey(mnemonic, "").privKeyBytes)
        return WalletAccount(privateKey = privateKey.toWif(),
                publicKey = privateKey.publicKey.toString(),
                mnemonic = mnemonic);
    }

    @Throws(Exception::class)
    override fun createAccountByMnemonic(mnemonic: String, path: String): WalletAccount {
        val privateKey = BosPrivateKey(ChainUtil.genECKey(mnemonic, path).privKeyBytes)
        return WalletAccount(privateKey = privateKey.toWif(),
                publicKey = privateKey.publicKey.toString(),
                mnemonic = mnemonic);
    }

    @Throws(Exception::class)
    override fun createAccountByPrivateKey(privateKey: String): WalletAccount {
        val BosPrivateKey = BosPrivateKey(privateKey)
        return WalletAccount(privateKey = BosPrivateKey.toWif(),
                publicKey = BosPrivateKey.publicKey.toString());
    }

    @Throws(Exception::class)
    override fun createAccountByKeyStore(keyStore: String, password: String): WalletAccount {
        throw NotSupportedException()
    }
}