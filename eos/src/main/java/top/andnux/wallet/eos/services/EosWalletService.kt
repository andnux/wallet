package top.andnux.wallet.eos.services

import androidx.annotation.Keep
import com.google.auto.service.AutoService
import io.github.novacrypto.bip39.Words
import top.andnux.wallet.base.WalletAccount
import top.andnux.wallet.base.exception.NotSupportedException
import top.andnux.wallet.base.services.WalletService
import top.andnux.wallet.base.utils.ChainUtil
import top.andnux.wallet.eos.blockchain.cypto.ec.EosPrivateKey

@Keep
@AutoService(WalletService::class)
class EosWalletService : WalletService {

    override fun chainName(): String {
        return "EOS"
    }

    @Throws(Exception::class)
    override fun createAccount(): WalletAccount {
        val mnemonic = ChainUtil.genMnemonic(Words.TWELVE)
        val privateKey = EosPrivateKey(ChainUtil.genECKey(mnemonic, "").privKeyBytes)
        return WalletAccount(privateKey = privateKey.toWif(),
                publicKey = privateKey.publicKey.toString(),
                mnemonic = mnemonic);
    }

    @Throws(Exception::class)
    override fun createAccountByMnemonic(mnemonic: String, path: String): WalletAccount {
        val privateKey = EosPrivateKey(ChainUtil.genECKey(mnemonic, path).privKeyBytes)
        return WalletAccount(privateKey = privateKey.toWif(),
                publicKey = privateKey.publicKey.toString(),
                mnemonic = mnemonic);
    }

    @Throws(Exception::class)
    override fun createAccountByPrivateKey(privateKey: String): WalletAccount {
        val eosPrivateKey = EosPrivateKey(privateKey)
        return WalletAccount(privateKey = eosPrivateKey.toWif(),
                publicKey = eosPrivateKey.publicKey.toString());
    }

    @Throws(Exception::class)
    override fun createAccountByKeyStore(keyStore: String, password: String): WalletAccount {
        throw NotSupportedException()
    }
}